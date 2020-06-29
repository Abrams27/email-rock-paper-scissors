import json
from kafka import KafkaConsumer, KafkaProducer

def pairwise(iterable):
    "s -> (s0, s1), (s2, s3), (s4, s5), ..."
    a = iter(iterable)
    return zip(a, a)

json_deserializer = lambda m : json.loads(m) if m is not None else None
json_serializer = lambda m : json.dumps(m).encode('utf-8')

consumer = KafkaConsumer(
    'matchmaking_requests',
    bootstrap_servers = 'localhost:9092',
    value_deserializer = json_deserializer,
    key_deserializer = json_deserializer
)

producer = KafkaProducer(
    bootstrap_servers = 'localhost:9092',
    value_serializer = json_serializer,
    key_serializer = json_serializer
)

for msg1, msg2 in pairwise(consumer):
    paired = [msg1.value, msg2.value]
    producer.send('matchmaking_pairs', paired)

