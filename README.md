[![forthebadge](https://forthebadge.com/images/badges/built-by-developers.svg)](https://forthebadge.com)[![forthebadge](https://forthebadge.com/images/badges/uses-brains.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/powered-by-black-magic.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/makes-people-smile.svg)](https://forthebadge.com)


# **Tic Tac Toe**

This is a simple Tic-Tac-Toe online game developed by a team of students at ITI intake 41,
Smart Village branch, as a final project for the Java programming course.

## Table of Contents

<!-- TOC -->

- [Main Features](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#%EF%B8%8F-main-features)
- [Game Features](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-game-features)
- [Demo](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-demo)
- [How To Use](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-how-to-use)
    - [Database Setup](#database-setup)
    - [How to Run The Server](#how-to-run-the-server)
    - [How to Run The Client](#how-to-run-the-client)
- [Dependencies](#dependencies)
- [Limitations](#limitations)
- [Possible Improvements](#possible-improvements)
- [Developers](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-developers)

<!-- /TOC -->
---

## 🕹️ Main Features

### Server-Side

- Live list of players status and score.
- Load and logs monitoring through a simple GUI.
- Start and stop the server with a simple click of a button.
- Passwords are encrypted before being saved in the database.

### Client-Side

- Play in single mode vs. computer in two levels of difficulty.
- A live list of the status and score of other players in the game.
- Chat with the opponent inside the game.
- Save the game to continue playing it later.
- User-friendly GUI.


## 💡 Game Features

- A score metrics for each player that is calculated upon winning or losing.
- Play with your friends in multiplayer mode (online).
- Playing again with the same player.
- Quit the game in the middle, but it will result in loosing the game.

---
## 🎮 Demo

Two gifs



For the full demo video refer to the following link: www.rgijgrmor.com

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

### How to Run The Server

Using File Explorer : Navigate to the ServerSide folder then inside dist folder double click ServerSide.jar

Using the Terminal : Navigate to the [ServerSide/dist](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ServerSide/dist) directory and run the following command:

```bash
java -jar ServerSide.jar
```

⚠️**Note that the server and the client run on port 7777.**

### How to Run The Client

Using File Explorer : Navigate to the ClientSide folder then inside dist folder double click ClientSide.jar

Using the Terminal : Navigate to the [ClientSide/dist](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ClientSide/dist) directory and run the following command:

```bash
java -jar ClientSide.jar
```

---
## Dependencies

* [MySQL](https://www.mysql.com/)
* [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html/)

---
## Limitations

- A potential shortcoming could be reveled when the database goes down while the server is running.
- Client-Server communication is reliability is about 90% sometimes the requests from the client are not caught by the server.

---
## Possible Improvements

- A possible improvment would be to implement a notification system to notify players about others signning in / out.
- Another potential improvment could be to allow the player to share the vectory to his social media accounts.
---
## 👨‍💻 Developers

- Ahmed Mamdouh Abdelwahab: https://www.linkedin.com/in/ahmed-mamdouh-935120100/
- Ahmed Mamdouh Mostafa: https://www.linkedin.com/in/ahmed-mamdouh-816273134/
- Aya Hamed: https://www.linkedin.com/in/aya-hamed/
- Ghada Ragab:	https://www.linkedin.com/in/ghadaragab/
- Hossam Khalil:	https://www.linkedin.com/in/hossam-khalil01/
- Sarah Magdy:	https://www.linkedin.com/in/sarah-mostafa-0647b61b8/
