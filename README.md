# mancala_game

Mancala board game created using angular.js, spring boot and mysql for data persistence

### Tools & Technology Used
**Front End:**
- Angularjs
- SockJS
- StompClient

**Back End:**
- Java
- Spring Boot
- mysql

### Pre-requisites
MYSQL Database should be installed and configured as below to respond to the REST Service calls

Setup db:

	create database mancala_game;
	create user 'mancala'@'localhost' identified by 'mancala';
	grant all privileges on mancala_game.* to 'mancala'@'localhost';

### HOW TO RUN
Spring boot startup will add 2 players into database.

To run the test cases:

	mvn test
To run application:
	
	mvn spring-boot:run(or you can run via Spring Tool Suite or Intellij IDE)
Chrome:
	
	localhost:8080
	login: vivin
	password: vivin
New Chrome Tab or other browser(Firefox):

	localhost:8080
	login: sundar
	password: sundar