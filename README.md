# mancala_game

Mancala board game created using angular.js, spring boot and mysql for data persistence

### Tools & Technology Used
**Front End:**
- AngularJS

**Back End:**
- Java
- Spring Boot
- mysql

### Pre-requisites
MYSQL Database should be installed and configured as below to respond to the REST Service calls from front end

Setup db:

	create database mancala_game;
	create user 'mancala'@'localhost' identified by 'mancala';
	grant all privileges on mancala_game.* to 'mancala'@'localhost';

### HOW TO RUN
Spring boot startup will add 2 players (vivin & sundar used for demo) into database.

To run application:
	
	mvn spring-boot:run(or you can run via Spring Tool Suite or Intellij IDE) or 
	java -jar target/mancala-game-bol-0.0.1-SNAPSHOT.jar
To run the test cases:

	mvn test
Login to web application with the first player
	
	localhost:8080
	login: vivin
	password: vivin	
Once logged in start with the game and login with second player 

	localhost:8080
	login: sundar
	password: sundar
Now we can go ahead to play the game and once the game is over, the winner will be displayed!