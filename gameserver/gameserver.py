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

    def rps(self, x, y):
        return [0, -1, 1]['RPS'.index(x) - 'RPS'.index(y)]

    def choose_winner(self, player):
        result = self.rps(player.handsign, player.opponent.handsign)
        if result == -1:
            return player
        elif result == 0:
            return None
        else:
            return player.opponent

    def resolve_match(self, players, winner):
        for player in players:
            msg = {
                "player": player.email,
                "opponent": player.opponent.email,
                "opponentHandSign": player.opponent.handsign,
            }

            if winner is None:
                msg["result"] = "Tie"
            elif winner == player:
                msg["result"] = "You won"
            else:
                msg["result"] = "You lost"

            eprint("sending result", msg)
            producer.send('results', msg)
        producer.flush()

    def handle_player_handsign(self, email, handsign):
        p = self.find_player(email)
        if p is None or p.opponent is None:
            eprint("could not find player or his opponent", email)
            return

        p.handsign = handsign

        if p.opponent.handsign is not None:
            winner = self.choose_winner(p)
            self.resolve_match([p, p.opponent], winner)
            self.players.remove(p)
            self.players.remove(p.opponent)

game = Game()

for msg in consumer:
    if msg.topic == 'matchmaking_pairs':
        eprint("handling match start for", msg.value)

        email1, email2 = None, None
        try:
            email1, email2 = msg.value["player1"], msg.value["player2"]
        except (AttributeError, TypeError):
            eprint("invalid message", msg.value)
            continue

        game.handle_match_start(email1, email2)

    elif msg.topic == 'handsigns':
        eprint("handling handsign", msg.value)

        email, handsign = None, None
        try:
            email = msg.value["player"]
            handsign = msg.value["handSign"]
        except (AttributeError, TypeError):
            eprint("invalid message", msg.value)
            continue

        game.handle_player_handsign(email, handsign)

    consumer.commit()

