from __future__ import print_function
from kafka import KafkaConsumer, KafkaProducer
import sys
import json

def eprint(*args, **kwargs):
    print(*args, file=sys.stderr, **kwargs)
    sys.stderr.flush()

def pairwise(iterable):
    "s -> (s0, s1), (s2, s3), (s4, s5), ..."
    a = iter(iterable)
    return zip(a, a)

json_deserializer = lambda m : json.loads(m) if m is not None else None
json_serializer = lambda m : json.dumps(m).encode('utf-8')

consumer = KafkaConsumer(
    'matchmaking_requests',
    bootstrap_servers = 'kafka',
    value_deserializer = json_deserializer,
    key_deserializer = json_deserializer,
    group_id = "matchmaker",
)

producer = KafkaProducer(
    bootstrap_servers = 'kafka',
    value_serializer = json_serializer,
    key_serializer = json_serializer,
)

for msg1, msg2 in pairwise(consumer):
    paired = None

    try:
        paired = {
            "player1": msg1.value["player"],
            "player2": msg2.value["player"]
        }
    except (AttributeError, TypeError):
        eprint("invalid messages", msg1.value, msg2.value)
        continue

    eprint("sending pair", paired)
    producer.send('matchmaking_pairs', paired)
    producer.flush()
    consumer.commit()

