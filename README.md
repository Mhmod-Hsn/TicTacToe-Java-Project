[![forthebadge](https://forthebadge.com/images/badges/built-by-developers.svg)](https://forthebadge.com)[![forthebadge](https://forthebadge.com/images/badges/uses-brains.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/powered-by-black-magic.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/makes-people-smile.svg)](https://forthebadge.com)



# **Tic Tac Toe**

This is a simple Tic-Tac-Toe online game developed by a team of students at ITI intake 41, 
Smart Village branch, as a final project for the Java programming course.

## Table of Contents

- [Main Features](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#%EF%B8%8F-main-features)

- [Additional Features](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-additional-features) 

- [Setup](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-setup) 

  - [Setup the database](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#setup-the-database)
  - [Run The Server](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#run-the-server)
  - [Run The Client](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#run-the-client)

- [Demo](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-demo) 
- [Developers](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/blob/master/README.md#-developers)

## 🕹️ Main Features

- Play in single mode vs. computer in two levels of difficulty.
- Play with your friends in multiplayer mode (online).
- Chat with the opponent inside the game.
- Save the game to continue playing it later.
- User-friendly GUI.
  

## 💡 Additional Features

- A score metrics for each player that is calculated upon winning or losing.
- A live list of the status and score of other players in the game.
- Inviting any online player in multiplayer mode.
- Playing again with the same player.
- Quit the game in the middle, but it will result in loosing the game.
  

## 💻 Setup 

Clone the project in your working directory.

```bash
git clone https://github.com/ahmedmumdouh/TicTacToe-Java-Project.git
```

or download the zipped file and unzip it in your working directory.

### Setup the database

- Import SQL schema file in any MySQL Server ( <u>Recommended: phpMyAdmin</u> ) or implement SQl statements manually in mySQL Shell as described in tictactoedb.sql in [DBSchema directory.](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ServerSide/DBSchema)
- Edit DBconfig.java file in  [Database Package ](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ServerSide/src/database ) to fill your database username ,password ,port number ,and database server url .

### Run The Server

Using File Explorer : Navigate to the ServerSide folder then inside dist folder double click ServerSide.jar

Using the Terminal : Navigate to the [ServerSide/dist](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ServerSide/dist) directory and run the following command:

```bash
java -jar ServerSide.jar
```

⚠️**Note that the server and the client run on port 7777.**

### Run The Client

Using File Explorer : Navigate to the ClientSide folder then inside dist folder double click ClientSide.jar

Using the Terminal : Navigate to the [ClientSide/dist](https://github.com/ahmedmumdouh/TicTacToe-Java-Project/tree/master/ClientSide/dist) directory and run the following command:

```bash
java -jar ClientSide.jar
```



## 🎮 Demo 

Two gifs 



For the full demo video refer to the following link: www.rgijgrmor.com



## 👨‍💻 Developers

- Ahmed Mamdouh Abdelwahab: https://www.linkedin.com/in/ahmed-mamdouh-935120100/
- Ahmed Mamdouh Mostafa: https://www.linkedin.com/in/ahmed-mamdouh-816273134/
- Aya Hamed: https://www.linkedin.com/in/aya-hamed/
- Ghada Ragab:	https://www.linkedin.com/in/ghadaragab/
- Hossam Khalil:	https://www.linkedin.com/in/hossam-khalil01/
- Sarah Magdy:	https://www.linkedin.com/in/sarah-mostafa-0647b61b8/
