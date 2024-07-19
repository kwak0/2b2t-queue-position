from flask import render_template, request
import json

players = []

def queue_post():
    global players
    data = request.get_json()
    username = data["username"]
    position = data["position"]
    connected = data["connected"]
    if connected == "false":
        connected = False
    response = data

    data.pop("connected")
    player_in_list = False
    for player in players:
        if player["username"] == username:
            player_in_list = True
            break
            
    if player_in_list:
        if connected:
            player["position"] = position
        else:
            players.remove(player)
    else:
        if connected:
            players.append(data)

    return json.dumps(player)

def queue_get():
    global players
    clients = len(players)
    header = f"Clients: {clients}"
    message_list = []

    for player in players:
        username = player["username"]
        position = player["position"]
        message_list.append(f"{username} is currently in position {position}.")   
    if clients == 1:  
        title = f"{username}: {position}"
    else:
        title = header

    size = 2.5
    message = ""
    for line in message_list:
        message += f"<p>{line}</p>"

    return render_template("queue_position.html", title=title, header=header, message=message, size=size)

