import random

teams = ["Boston celtics"]

boston_players = ["Jayson Tatum", "Jaylen Brown", "Marcus Smart", "Al Horford"]

knicks_players = ["Julius Randle", "RJ Barrett", "Jalen Brunson", "donte divincenzo"]

actions = ["committed a foul against", "blocked the shot of", "turned the ball over to"]

# Generate random news
def generate_nba_news():
    player1 = random.choice(boston_players)
    player2 = random.choice(knicks_players)
    action = random.choice(actions)
    
    return f"{player1} {action} {player2}"

# Generate 5 random news items
for _ in range(5):
    print(generate_nba_news())