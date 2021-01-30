[![forthebadge](https://forthebadge.com/images/badges/built-by-developers.svg)](https://forthebadge.com)[![forthebadge](https://forthebadge.com/images/badges/uses-brains.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/powered-by-black-magic.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/makes-people-smile.svg)](https://forthebadge.com)


# **Tic Tac Toe**

Tic-tac-toe has been a part of almost everyone’s childhood.
While the game of tic-tac-toe is really old, dating back to ancient Egyptians at around 1300 B.C., the underlying principles of the game have endured the test of time. The game seems simple enough. Two opponents, one using X and one using O, use a 3 x 3 grid to mark their symbols. The first one to get all three of his/her symbols in a row, whether it’s diagonal, horizontal, or vertical, wins the game.

---
## Overview

![single player demo](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/demos/singleGame.gif)

This project is a simple Tic-Tac-Toe online game developed as a final project for the java course.
The game is developed using java and javafx utilizing socket programming, multi-threading
and JSON data transmission to allow for a multiplayer online game with other features such as ingame Chat and much more.

---
## Table of Contents

<!-- TOC -->
- [Main Features](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#%EF%B8%8F-main-features)
- [Game Features](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-game-features)
- [Demo](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-demo)
- [How To Use](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-how-to-use)
    - [Database Setup](#database-setup)
    - [Run The Server](#run-the-server)
    - [Run The Client](#run-the-client)
- [Dependencies](#dependencies)
- [Limitations](#limitations)
- [Possible Improvements](#possible-improvements)
- [About Us](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-about-us)

<!-- /TOC -->



---
## 🕹️ Main Features

### Server-Side

- Live list of players status and score.
- Load and logs monitoring through a simple GUI.
- Start and stop the server with a simple click of a button.
- Passwords are encrypted before being saved in the database.

### Client-Side

- Play in single mode vs. computer in two levels of difficulty, implemented using minimax algorithm.
- A live list of the status and score of other players in the game.
- Chat with the opponent inside the game.
- Save the game to continue playing it later.
- User-friendly GUI.

---
## 💡 Game Features

- A score metrics for each player that is calculated upon winning or losing.
- Play with your friends in multiplayer mode (online).
- Playing again with the same player.
- Quit the game in the middle, but it will result in loosing the game.

---
## 🎮 Demo

### Server-side Interface

![server gui gif](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/demos/server.gif)

You can start and stop the server from the GUI, as well as view the server logs, the database and a live players list.

### Multiplayer Mode

![multiplayer gif](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/demos/multiGame.gif)

Two players can challenge each other and play together online, and they can also chat with each other during the game.

### Saving and Loading

![save gif](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/demos/save_load.gif)

The players can save the game to continue playing at another time.

For the full demo video refer to the following link: [Demo Video](https://drive.google.com/file/d/1xS0jdz3L2U-QqoeBnXbGfMWgWpbPNS03/view?usp=sharing)

---
## 💻 How To Use

Clone the project in your working directory.

```bash
git clone https://github.com/ahmedmumdouh/TicTacToe-Java-Project.git
```

or download the zipped file and unzip it in your working directory.


### Database Setup

- Import SQL schema file in any MySQL Server ( <u>Recommended: phpMyAdmin</u> ) or implement SQl statements manually in mySQL Shell as described in tictactoedb.sql in [DBSchema directory.](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ServerSide/DBSchema)
- Edit DBconfig.java file in  [Database Package ](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ServerSide/src/database ) to fill your database username ,password ,port number ,and database server url .

### Run The Server

Using File Explorer : Navigate to the ServerSide folder then inside dist folder double click ServerSide.jar

Using the Terminal : Navigate to the [ServerSide/dist](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ServerSide/dist) directory and run the following command:

```bash
java -jar ServerSide.jar
```

Using The executable file: Double-click the ServerSide.jar file found in the [bin directory](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ServerSide/bin)

⚠️**Note that the server and the client run on port 7777.**

### Run The Client

Using File Explorer : Navigate to the ClientSide folder then inside dist folder double click ClientSide.jar

Using the Terminal : Navigate to the [ClientSide/dist](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ClientSide/dist) directory and run the following command:

```bash
java -jar ClientSide.jar
```

Using The executable file: Double-click the ClientSide.jar file found in the [bin directory](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ClientSide/bin)

---
## 📋 Dependencies

* [MySQL](https://www.mysql.com/)
* [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html/)
* [Json-simple library](https://github.com/fangyidong/json-simple)
* [mysql-connector](https://dev.mysql.com/downloads/connector/j/)


---
## 🚫 Limitations

- A potential shortcoming could appear when the database goes down while the server is running.
- The reliability of the Client-Server communication is about 90%; sometimes the requests from the client are not caught by the server.

---
## 📈 Possible Improvements

- Implement a notification system to notify players about others signning in/out.
- To allow the player to share his game result on his social media accounts.
---
## 👨‍💻 About Us

We are a team of software enginnering students at ITI intake 41, Smart Village branch, Open-source application track.

### 📞 Contacts

You can find us on:

#### Linkedin

- [Ahmed Mamdouh Abdelwahab](https://www.linkedin.com/in/ahmed-mamdouh-935120100/)
- [Ahmed Mamdouh Mostafa](https://www.linkedin.com/in/ahmed-mamdouh96/)
- [Aya Hamed](https://www.linkedin.com/in/aya-hamed/)
- [Ghada Ragab](https://www.linkedin.com/in/ghadaragab/)
- [Hossam Khalil](https://www.linkedin.com/in/hossam-khalil01/)
- [Sarah Magdy](https://www.linkedin.com/in/sarah-mostafa-0647b61b8/)

#### GitHub

- [Ahmed Mamdouh Abdelwahab](https://github.com/ahmedmumdouh)
- [Ahmed Mamdouh Mostafa](https://github.com/AhmedMamdouh996)
- [Aya Hamed](https://github.com/AyaHamedd)
- [Ghada Ragab](https://github.com/GhadaRagab123)
- [Hossam Khalil](https://github.com/hossamkhalil01)
- [Sarah Magdy](https://github.com/SarahOuf)
