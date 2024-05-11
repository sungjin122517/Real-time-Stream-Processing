import json
import random
import time
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
    "team_players": ["Jayson Tatum", "Jaylen Brown", "Derrick White", "Jrue Holiday", "Kristaps Portingis"]
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
    "rank": 1,
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

actions = ["committed a foul against", "blocked the shot of", "turned the ball over to", "Scored 2 points against","Scored 2 points against","Scored 2 points against","Scored 3 points against", "Scored 3 points against" ]

# Generate random news
def generate_nba_news():
    player1 = random.choice(boston_team_info["team_players"])
    player2 = random.choice(NY_player_info["team_players"])
    action = random.choice(actions)

    return f"{player1} {action} {player2}"


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
        