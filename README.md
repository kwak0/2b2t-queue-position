# 2b2t Queue Position Mod

A Minecraft mod to keep track of 2b2t queue position.

The mod sends the current queue position to a Flask server so you can keep track of multiple accounts waiting without having to be near your computer. You (currently) have to host the server yourself.

Includes:
-A Minecraft Fabric Mod
-An easily deployable Flask server

## Installation

### Mod

#### 1) Install Fabric Loader
Download and install the Fabric Loader [here](https://fabricmc.net/use/installer/).  
([Installation guide](https://docs.fabricmc.net/players/installing-fabric))

#### 2) Install Mod
Download 2b2t-queue-position-0.1.0.jar and move the file to the mods folder.  
([Guide](https://docs.fabricmc.net/players/installing-mods)).

#### 3) Set server adress
Launch Minecraft once with the mod to generate the config file. Go to your Minecraft folder (the one where your mods folder is) and open the config folder. Now open 2b2t_position_mod_config.json and replace the Flask server address with your own address. The default address gets used when you run the server locally.

### Server

##### 1) Download and extract the source code.
##### 2) Move the 2 files in the flask-server folder to your server. (You can get one for free using [pythonanywhere.com](https://www.pythonanywhere.com/))
##### 3) Set up the server. ([Guide for pythonanywhere.com](https://help.pythonanywhere.com/pages/Flask/))

### Example
If everything works, your site should look something like this while in the queue.

![image](https://github.com/user-attachments/assets/4ec027fd-3bd8-4db5-937b-f91e33f2c6a4)
