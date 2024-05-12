/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package FlinkNBA;

import Deserializer.JSONValueDeserializationSchema;
import dto.Gamestat;
import dto.Update;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.connector.jdbc.JdbcSink;

public class DataStreamJob {
	private static final String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
	private static final String username = "postgres";
	private static final String password = "postgres";

	public static void main(String[] args) throws Exception {
		// Sets up the execution environment, which is the main entry point
		// to building Flink applications.
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		String topic = "game_updates";

		KafkaSource<Update> source = KafkaSource.<Update>builder()
				.setBootstrapServers("localhost:9092")
				.setTopics(topic)
				.setGroupId("FlinkNBA")
				.setStartingOffsets(OffsetsInitializer.earliest())
				.setValueOnlyDeserializer(new JSONValueDeserializationSchema())
				.build();

		DataStream<Update> updateStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka source");

		updateStream.print();

		JdbcExecutionOptions execOptions = new JdbcExecutionOptions.Builder()
				.withBatchSize(1000)
				.withBatchIntervalMs(200)
				.withMaxRetries(5)
				.build();

		JdbcConnectionOptions connOptions = new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
				.withUrl(jdbcUrl)
				.withDriverName("org.postgresql.Driver")
				.withUsername(username)
				.withPassword(password)
				.build();

//		 Create table if not exists any
//
//		{"player": "Boston celtics", "text": "Marcus Smart committed a foul against Jalen Brunson", "updateTime": "2024-05-11T07:07:06.048549"}
		updateStream.addSink(JdbcSink.sink(
			"CREATE TABLE IF NOT EXISTS updates (" +
					"gameId VARCHAR(255), " +
					"player VARCHAR(255), " +
					"team VARCHAR(255), " +
					"situation INTEGER, " +
					"text VARCHAR(255), " +
					"updateTime TIMESTAMP PRIMARY KEY" +
					")",
			(JdbcStatementBuilder<Update>) (preparedStatement, update) -> {
				},
				execOptions,
				connOptions
        )).name("Create Updates Table Sink");

//		 Insert into the table
		updateStream.addSink(JdbcSink.sink(
			"INSERT INTO updates(gameId, player, team, situation, text, updateTime) " +
					"VALUES (?, ?, ?, ?, ?, ?) " +
					"ON CONFLICT (updateTime) DO UPDATE SET " +
					"gameId = EXCLUDED.gameId, " +
					"player = EXCLUDED.player, " +
					"team = EXCLUDED.team, " +
					"situation = EXCLUDED.situation, " +
					"text = EXCLUDED.text " +
					"WHERE updates.updateTime = EXCLUDED.updateTime",
			(JdbcStatementBuilder<Update>) (preparedStatement, update) -> {
				preparedStatement.setString(1, update.getGameId());
				preparedStatement.setString(2, update.getPlayer());
				preparedStatement.setString(3, update.getTeam());
				preparedStatement.setInt(4, update.getSituation());
				preparedStatement.setString(5, update.getText());
				preparedStatement.setTimestamp(6, update.getUpdateTime());
				},
				execOptions,
				connOptions
        )).name("Insert into Updates Table Sink");

		updateStream.addSink(JdbcSink.sink(

				"CREATE TABLE IF NOT EXISTS gamestats (" +
						"gameId VARCHAR(255), " +
						"situation INTEGER, " +
						"home_score INTEGER, " +
						"away_score INTEGER, " +
						"home_foul INTEGER, " +
						"away_foul INTEGER, " +
//						"team_home VARCHAR(255), " +
//						"team_away VARCHAR(255), " +
						"PRIMARY KEY (gameId)" +
						")",
				(JdbcStatementBuilder<Update>) (preparedStatement, update) -> {
				},
				execOptions,
				connOptions
		)).name("Create Updates Table Sink");

		updateStream.map(
				update -> {
					String gameId = update.getGameId();
					Integer home_score = 0;
					Integer away_score = 0;
					Integer home_foul = 0;
					Integer away_foul = 0;
					Integer situation = update.getSituation();
//					String team_home = update.getTeam_home();
//					String team_away = update.getTeam_away();
					return new Gamestat(gameId, situation, home_score, away_score, home_foul, away_foul);
//							, team_home, team_away);
				}
		).keyBy(Gamestat::getGameId)
				.reduce((gamestat, t1) -> {
					if (t1.getSituation() == 1) {
						gamestat.setHome_foul(gamestat.getHome_foul() + 1);
					} else if (t1.getSituation() == 3 || t1.getSituation() == 4 || t1.getSituation() == 5) {
						gamestat.setHome_score(gamestat.getHome_score() + 2);
					} else if (t1.getSituation() == 6 || t1.getSituation() == 7) {
						gamestat.setHome_score(gamestat.getHome_score() + 3);
					} else if (t1.getSituation() == 9) {
						gamestat.setAway_foul(gamestat.getAway_foul() + 1);
					} else if (t1.getSituation() == 11 || t1.getSituation() == 12 || t1.getSituation() == 13) {
						gamestat.setAway_score(gamestat.getAway_score() + 2);
					} else if (t1.getSituation() == 14 || t1.getSituation() == 15) {
						gamestat.setAway_score(gamestat.getAway_score() + 3);
					}
					gamestat.setSituation(t1.getSituation());

					return gamestat;
				}).addSink(JdbcSink.sink(
					"INSERT INTO gamestats (gameId, situation, home_score, away_score, home_foul, away_foul) " +
							"VALUES (?, ?, ?, ?, ?, ?) " +
							"ON CONFLICT (gameId) DO UPDATE SET " +
							"situation = EXCLUDED.situation, " +
							"home_score = EXCLUDED.home_score, " +
							"away_score = EXCLUDED.away_score, " +
							"home_foul = EXCLUDED.home_foul, " +
							"away_foul = EXCLUDED.away_foul " +
//							"team_home = EXCLUDED.team_home, " +
//							"team_away = EXCLUDED.team_away " +
							"WHERE gamestats.gameId = EXCLUDED.gameId",

						(JdbcStatementBuilder<Gamestat>) (preparedStatement, gamestat) -> {
							preparedStatement.setString(1, gamestat.getGameId());
							preparedStatement.setInt(2, gamestat.getSituation());
							preparedStatement.setInt(3, gamestat.getHome_score());
							preparedStatement.setInt(4, gamestat.getAway_score());
							preparedStatement.setInt(5, gamestat.getHome_foul());
							preparedStatement.setInt(6, gamestat.getAway_foul());
//							preparedStatement.setString(7, gamestat.getTeam_home());
//							preparedStatement.setString(8, gamestat.getTeam_away());
						},
						execOptions,
						connOptions
				)).name("Insert into Gamestat table");


		/*
		 * Here, you can start creating your execution plan for Flink.
		 *
		 * Start with getting some data from the environment, like
		 * 	env.fromSequence(1, 10);
		 *
		 * then, transform the resulting DataStream<Long> using operations
		 * like
		 * 	.filter()
		 * 	.flatMap()
		 * 	.window()
		 * 	.process()
		 *
		 * and many more.
		 * Have a look at the programming guide:
		 *
		 * https://nightlies.apache.org/flink/flink-docs-stable/
		 *
		 */


		// Execute program, beginning computation.
		env.execute("Flink NBA update lets go");
	}
}
