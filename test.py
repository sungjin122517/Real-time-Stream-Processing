from confluent_kafka import SerializingProducer
from confluent_kafka.serialization import StringSerializer
import json

kafka_bootstrap_servers = 'broker:29092'

# Test connection to Kafka using SerializingProducer
def test_kafka_connection():
    try:
        producer = SerializingProducer({
            'bootstrap.servers': kafka_bootstrap_servers,
            'key.serializer': StringSerializer('utf_8'),
            'value.serializer': StringSerializer('utf_8')
        })
        producer.list_topics(timeout=10)
        print("Connection to Kafka successful")
    except Exception as e:
        print("Error connecting to Kafka:", str(e))

if __name__ == "__main__":
    test_kafka_connection()