import json
import random
import time

from confluent_kafka import SerializingProducer
from confluent_kafka import Producer
from datetime import datetime
from json import dumps

# Kafka broker configuration
kafka_nodes = "kafka:9092"

# Topic for NBA news
nba_news_topic = 'nba-news'

teams = ["Boston celtics", "New York knicks"]

boston_team_info = {
    "team_name": "Boston Celtics",
    "rank": 1,
    "team_players": ["Jayson Tatum", "Jaylen Brown", "Derrick White", "Jrue Holiday", "Kristops Porzingis"]
}
boston_player_info = {
    "team_name": "Boston Celtics",
    "player_importance": {
        "Jayson Tatum": 0.9,
        "Jaylen Brown": 0.85,
        "Derrick White": 0.7,
        "Jrue Holiday": 0.95,
        "Kristaps Portingis": 0.8
    }
}

NY_team_info = {
    "team_name": "New York knicks",
    "rank": 4,
    "team_players": ["Julius Randle", "Josh Hart", "Jalen Brunson", "Donte Divincenzo", "OG Anunoby"]
}
NY_player_info = {
    "team_name": "New York knicks",
    "player_importance": {
        "Julius Randle": 0.9,
        "Josh Hart": 0.8,
        "Jalen Brunson": 0.85,
        "Donte Divincenzo": 0.75,
        "OG Anunoby": 0.85
    }
}

boston_players = ["Jayson Tatum", "Jaylen Brown", "Derrick White", "Jrue Holiday", "Kristops Porzingis"]

knicks_players = ["Julius Randle", "Josh Hart", "Jalen Brunson", "Donte Divincenzo", "OG Anunoby"]

actions = ["committed a foul against", "blocked the shot of", "turned the ball over to", "Scored 2 points against", "Scored 2 points against","Scored 2 points against", "Scored 3 points against", "Scored 3 points against"]

# Function to send team and player information
def send_initial_information(producer, topic, team_info, player_info):
    # Send team information
    producer.produce(topic, key=team_info['team_name'], value=json.dumps(team_info))
    print(f"Sent team information for {team_info['team_name']}")

    # Send player information
    for player, importance in player_info['player_importance'].items():
        player_data = {
            "team_name": player_info['team_name'],
            "player_name": player,
            "importance": importance
        }
        producer.produce(topic, key=player, value=json.dumps(player_data))
        print(f"Sent player information for {player}")

# Generate random news
def generate_nba_news():
    # Randomly choose between the two possibilities
    option = random.randint(1, 2)

    if option == 1:
        player1 = random.choice(boston_players)
        player2 = random.choice(knicks_players)
    else:
        player1 = random.choice(knicks_players)
        player2 = random.choice(boston_players)

    action = random.choice(actions)

    #return f"{player1} {action} {player2}"
    return {
        'player1': player1,
        'player2': player2,
        'action': action,
        'updateTime': datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%S.%f%z'),
    }


# Create Kafka producer
# Function to publish NBA news to Kafka topic
def publish_nba_news():
    return {
        'player': random.choice(teams),
        'text': generate_nba_news(),
        'updateTime': datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%S.%f%z'),
        # 'team' : x
        # 'scenario' : y
    }

def delivery_report(err, msg):
    if err is not None:
        print(f'Message delivery failed: {err}')
    else:
        print(f"Message delivered to {msg.topic} [{msg.partition()}]")


def main():
    topic = 'game_updates'
    producer = SerializingProducer({
        'bootstrap.servers': 'broker:29092'
    })

    curr_time = datetime.now()

    # Initial setup
    send_initial_information(producer, topic, boston_team_info, boston_player_info)
    send_initial_information(producer, topic, NY_team_info, NY_player_info)

    # Only active for 120 seconds
    while (datetime.now() - curr_time).seconds < 120:
        try:
            updates = generate_nba_news()
            print(updates)

            producer.produce(topic,
                            key=updates['player1'],
                            value=json.dumps(updates),
                            on_delivery=delivery_report
                            )
            # To ensure data gets delivered before another one gets sent
            producer.poll(0)
            time.sleep(3)

        except BufferError:
            print("Buffer full! Waiting...")
            time.sleep(1)
        except Exception as e:
            print(e)


if __name__ == "__main__":
    main()
    # publish_nba_news()
    # schedule.every(10).seconds.do(publish_nba_news)

    # while True:
    #     schedule.run_pending()
    #     time.sleep(0.5)