package org.example.comp4651;

import org.example.comp4651.entity.GameStats;
import org.example.comp4651.entity.Player;
import org.example.comp4651.entity.Team;
import org.example.comp4651.repository.GameStatsRepository;
import org.example.comp4651.repository.PlayerRepository;
import org.example.comp4651.repository.TeamRepository;
import org.example.comp4651.service.GameStatsService;
import org.example.comp4651.service.PlayerService;
import org.example.comp4651.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Comp4651ApplicationTests {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private GameStatsRepository gameStatsRepository;

	@Autowired
	private GameStatsService gameStatsService;

	@Test
	void testJpa() {
		/* Adjust for rank */
//		gameStatsService.adjustForRank(1);

		/* Adjust for player importance */
//		gameStatsService.adjustForPlayerImportance(1);

		/* Adjust for rank */

//		Team temp = this.teamRepository.findById(2);
//
//		Player p1 = new Player();
//		p1.setName("Kim");
//		p1.setImportanceValue(0.8);
//		p1.setFoul(0);
//		p1.setGoal(0);
//		p1.setPlaying(true);
//		p1.setTeam(temp);
//		this.playerRepository.save(p1);
//
//		Player p2 = new Player();
//		p2.setName("Sung");
//		p2.setImportanceValue(0.9);
//		p2.setFoul(0);
//		p2.setGoal(0);
//		p2.setPlaying(true);
//		p2.setTeam(temp);
//		this.playerRepository.save(p2);
//
//		Player p3 = new Player();
//		p3.setName("Han");
//		p3.setImportanceValue(0.7);
//		p3.setFoul(0);
//		p3.setGoal(0);
//		p3.setPlaying(true);
//		p3.setTeam(temp);
//		this.playerRepository.save(p3);
//
//		Player p4 = new Player();
//		p4.setName("Cho");
//		p4.setImportanceValue(0.6);
//		p4.setFoul(0);
//		p4.setGoal(0);
//		p4.setPlaying(true);
//		p4.setTeam(temp);
//		this.playerRepository.save(p4);
//
//		Player p5 = new Player();
//		p5.setName("Lee");
//		p5.setImportanceValue(0.5);
//		p5.setFoul(0);
//		p5.setGoal(0);
//		p5.setPlaying(true);
//		p5.setTeam(temp);
//		this.playerRepository.save(p5);


//		Team t1 = new Team();
//		t1.setName("Team1");
//		t1.setRank(1);
//		this.teamRepository.save(t1);
//
//		Team t2 = new Team();
//		t2.setName("Team2");
//		t2.setRank(10);
//		this.teamRepository.save(t2);
//
//		GameStats gs1 = new GameStats();
//		gs1.setHomeTeam(t1);
//		gs1.setHomeWinProb(60);
//		gs1.setHomeScore(47);
//		gs1.setHomeFouls(10);
//		gs1.setHomeImportanceValue(4);
//		gs1.setAwayTeam(t2);
//		gs1.setAwayWinProb(40);
//		gs1.setAwayScore(58);
//		gs1.setAwayFouls(12);
//		gs1.setAwayImportanceValue(3.8);
//		this.gameStatsRepository.save(gs1);

	}

}
