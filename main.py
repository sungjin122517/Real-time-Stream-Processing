import json
import random
import time

from confluent_kafka import SerializingProducer
from datetime import datetime
from json import dumps

# Kafka broker configuration
kafka_nodes = "kafka:9092"

# Topic for NBA news
nba_news_topic = 'nba-news'

teams = ["Boston celtics", "New York knicks"]

boston_players = ["Jayson Tatum", "Jaylen Brown", "Marcus Smart", "Al Horford"]

knicks_players = ["Julius Randle", "RJ Barrett", "Jalen Brunson", "donte divincenzo"]

actions = ["committed a foul against", "blocked the shot of", "turned the ball over to"]


# Generate random news
def generate_nba_news():
    player1 = random.choice(boston_players)
    player2 = random.choice(knicks_players)
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
def main():

    topic = 'game_updates'
    producer = SerializingProducer({
        'bootstrap.servers': 'localhost:9092'
    })

    curr_time = datetime.now()

    # Only active for 120 seconds
    while (datetime.now() - curr_time).seconds < 1200:
        try:
            updates = publish_nba_news()
            print(updates)

            producer.produce(topic,
                             key=updates['player'],
                             value=json.dumps(updates),
                             on_delivery=delivery_report
                             )
            # To ensure data gets delivered before another one gets sent
            producer.poll(0)

            # wait for 5 seconds before sending the next update
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
