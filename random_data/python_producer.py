import random
import schedule
import time

from time import sleep
from kafka import KafkaProducer, KafkaConsumer
from generate_news import generate_nba_news
from json import dumps

# Kafka broker configuration
kafka_nodes = "kafka:9092"

# Topic for NBA news
nba_news_topic = 'nba-news'

# Create Kafka producer
#producer = KafkaProducer(bootstrap_servers=kafka_nodes)
prod = KafkaProducer(bootstrap_servers=kafka_nodes, value_serializer=lambda x:dumps(x).encode('utf-8'))

# Function to publish NBA news to Kafka topic
def publish_nba_news():
    while True:
        news = generate_nba_news()
        #prod.send(nba_news_topic, news.encode('utf-8'))
        prod.send(topic=nba_news_topic, value=news)
        print(f"Published: {news}")
        sleep(5)  # Adjust the interval as needed

# # Create Kafka consumer
# consumer = KafkaConsumer(nba_news_topic, bootstrap_servers=bootstrap_servers)

# # Function to consume NBA news from Kafka topic
# def consume_nba_news():
#     for message in consumer:
#         print(f"Received: {message.value.decode('utf-8')}")

if __name__ == "__main__":
    publish_nba_news()
    schedule.every(10).seconds.do(publish_nba_news)

    while True:
        schedule.run_pending()
        time.sleep(0.5)
