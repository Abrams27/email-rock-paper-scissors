# żeby interaktywnie potestować:
# docker exec -it <matcher container-id> python
# exec(open("interactive.py").read())

from kafka import KafkaConsumer, KafkaProducer
import json

json_deserializer = lambda m : json.loads(m) if m is not None else None
json_serializer = lambda m : json.dumps(m).encode('utf-8')

pairs_consumer = KafkaConsumer(
    'matchmaking_pairs',
    bootstrap_servers = 'kafka',
    value_deserializer = json_deserializer,
    key_deserializer = json_deserializer,
    group_id = "interactive",
    auto_offset_reset = "earliest"
)

producer = KafkaProducer(
    bootstrap_servers = 'kafka',
    value_serializer = json_serializer,
    key_serializer = json_serializer,
)

def receive_matchmaking_pair():
    ret = next(pairs_consumer)
    pairs_consumer.commit()
    return ret

def send_matchmaking_request(email):
    producer.send('matchmaking_requests', email)

