# żeby interaktywnie potestować:
# docker exec -it <matcher container-id> python
# exec(open("interactive.py").read())
# albo ./interact.sh

from kafka import KafkaConsumer, KafkaProducer
import json

json_deserializer = lambda m : json.loads(m) if m is not None else None
json_serializer = lambda m : json.dumps(m).encode('utf-8')

result_consumer = KafkaConsumer(
    'jnp-game-result',
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

def receive_result():
    ret = next(result_consumer)
    result_consumer.commit()
    return ret

def send_handsign(email, handsign):
    data = {"player": email, "handSign": handsign}
    producer.send('jnp-game', data)
    producer.flush()

