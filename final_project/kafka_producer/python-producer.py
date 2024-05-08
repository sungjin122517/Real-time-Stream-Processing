import datetime
import time
import random
import schedule
from json import dumps

from faker import Faker
from kafka import KafkaProducer

kafka_nodes = "kafka:9092"
myTopic = "basketball"

teams = ["Boston celtics", "New York Knicks", "Brooklyn Nets", "Philadelphia 76ers", "Toronto Raptors"]
boston_players = ["Jayson Tatum", "Jaylen Brown", "Marcus Smart", "Al Horford"]
knicks_players = ["Julius Randle", "RJ Barrett", "Jalen Brunson", "donte divincenzo"]
actions = ["committed a foul against", "blocked the shot of", "turned the ball over to"]

# Generate random news
def generate_nba_news():
    player1 = random.choice(boston_players)
    player2 = random.choice(knicks_players)
    action = random.choice(actions)
    
    return f"{player1} {action} {player2}"

def gen_data():
    prod = KafkaProducer(bootstrap_servers=kafka_nodes, value_serializer=lambda x:dumps(x).encode('utf-8'))
    my_data = {
        'team': random.choice(teams),
        'news': generate_nba_news()
    }
    print(my_data)
    prod.send(topic=myTopic, value=my_data)
    prod.flush()

# kafka_nodes = "kafka:9092"
# myTopic = "weather"

# def gen_data():
#   faker = Faker()

#   prod = KafkaProducer(bootstrap_servers=kafka_nodes, value_serializer=lambda x:dumps(x).encode('utf-8'))
#   my_data = {'city': faker.city(), 'temperature': random.uniform(10.0, 110.0)}
#   print(my_data)
#   prod.send(topic=myTopic, value=my_data)

#   prod.flush()

if __name__ == "__main__":
  gen_data()
  schedule.every(10).seconds.do(gen_data)

  while True:
    schedule.run_pending()
    time.sleep(0.5)