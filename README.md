# telly-boot-microservices
Telly-Boot Microservices

This project is a continuation of https://github.com/jonathanborenstein/telly-boot except this project runs as three distinct services on different ports.

In order to use the program you need to download the rabbitmq message broker. https://www.rabbitmq.com/download.html

You can run then program by importing a maven project into your IDE or by going to each directory (bus, reservations, and telly) and use <code>mvn clean spring-boot:run</code> .

First you will need to download or clone the program. <code>git clone https://github.com/jonathanborenstein/telly-boot-microservices.git</code>

You can run rabbitmq with the following command: <code>invoke-rc.d rabbitmq-server start</code> in your command line.

One rabbitmq is running, start the telly microservice first, followed by the bus microservice and then the reservations microservice.

localhost:8080 will be your starting point. You should create a username, log in, and create trips. Create a few simple trips with the formatting such as 2017/06/03 for the date field, JFK for the leave from field, and LAX for the going to field. You can do whatever you want as long as the date format is correct.

Then search for those trips and you will be able to book them, delete the reservation, etc.

Each microservice can be connected to a MySql database and there is a connecter jar already on the classpath. Right now the program runs with an H2 embedded database for each microservice. If you want to save the data locally, go to the application.properties file of each microservice, undo the hashmarks, and add in your database information.
