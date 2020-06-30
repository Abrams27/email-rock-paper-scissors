from __future__ import print_function
from dataclasses import dataclass
from kafka import KafkaConsumer, KafkaProducer
import sys
import json

def eprint(*args, **kwargs):
    print(*args, file=sys.stderr, **kwargs)
    sys.stderr.flush()

json_deserializer = lambda m : json.loads(m) if m is not None else None
json_serializer = lambda m : json.dumps(m).encode('utf-8')

topics = ['matchmaking_pairs', 'handsigns']
consumer = KafkaConsumer(
    *topics,
    bootstrap_servers = 'kafka',
    value_deserializer = json_deserializer,
    key_deserializer = json_deserializer,
    group_id = "gameserver",
)

producer = KafkaProducer(
    bootstrap_servers = 'kafka',
    value_serializer = json_serializer,
    key_serializer = json_serializer,
)

@dataclass
class Player:
    email: str
    opponent = None
    handsign = None

class Game:
    players: list = []

    def find_player(self, email):
        matches = [p for p in self.players if p.email == email]
        if len(matches) > 0:
            return matches[0]
        else:
            return None

    def add_players(self, *emails):
        for email in emails:
            p = self.find_player(email)
            if p is None:
                p = Player(email)
                self.players.append(p)

    def set_opponents(self, email1, email2):
        p1 = self.find_player(email1)
        p2 = self.find_player(email2)

        p1.opponent = p2
        p2.opponent = p1

    def handle_match_start(self, email1, email2):
        self.add_players(email1, email2)
        self.set_opponents(email1, email2)

    def handle_handsign(handsign):
        pass

game = Game()

for msg in consumer:
    if msg.topic == 'matchmaking_pairs':
        eprint("handling match start for", *msg.value)
        game.handle_match_start(*msg.value)
    elif msg.topic == 'handsigns':
        eprint("handling handsign", msg.value)
        game.handle_handsign(msg.value)
    consumer.commit()

