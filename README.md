# adidas-challenge

OVERVIEW
--------
This repository has two implemented microservices:
* **Flight DataService** provides information about available flights between different cities.
* **Itineraries Service** calculates the best itineraries between two cities accessing the **Flight DataService**.

REQUIREMENTS
------------
* Java 8
* Docker 18

BUILD
-----
To build all projects: 
* On **adidas-challenge** directory execute **./mvnw install**

To build each project individually and create docker images:
* On **flight-data-service** directory execute **./mvnw install dockerfile:build**
* On **itineraries-service** directory execute **./mvnw install dockerfile:build**

RUN
---
On **adidas-challenge** directory execute **docker-compose up** to run all containers at the same time.

STOP RUN
--------
On **adidas-challenge** directory execute **docker-compose down** to stop all container instances.

TEST
----
From your browser:
* Go to http://localhost:8080/swagger-ui.html to test **Itineraries Service**.
* Go to http://localhost:8081/swagger-ui.html to test **Flight DataService**.

with all containers running.
