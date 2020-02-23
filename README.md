# Omnius Event Store

System store task gererated from scheduler at regular event. THe scheduler generate task and put in the queue. 
Listner takes the tasks from the queue and store in DB. The stored task can be viewd on UI with the web application. 
Also task list is getting update in web app when new task is stored. Here are are some screen shot of how UI look like.
https://github.com/kumar-devender/omnius-event-store/wiki/OmniUs-Home-screen-shot

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

To run this application on your machine you need to have install docker in your machine. Please follow below link if docker is not installed on your machine.

```
https://docs.docker.com/docker-for-mac/install
```

### Running application on new machine


Clone the project on your system

```
git clone https://github.com/kumar-devender/omnius-event-store.git
```

Step into the codebase root directory and run following command

```
mvn clean package
```

Below is the final command to have the application running on your machine
```
docker-compose up --build
```

Now the application is running on your machine. To see the task open web application using ```http://localhost:8080```
Also you can play with API ```http://localhost:8080/swagger-ui.html``` 

Taks are created at 30 second interval. If you want to change this interval please change value of ```fixedDelay.in.milliseconds```
in application.properties file.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [ActiveMQ](https://activemq.apache.org) - Message broker for distributed systems
* [Postgres] (https://www.postgresql.org)- Database
* [Flyway] (http://flywaydb.org) Schema creation and migration
* [Spring Boot, lombok, springfox]
* [Lots of love]

## Further enhancement
* Pagination of task can be implemented on UI.
* Integration test and unit test.

## Author

* **Devender Kumar** - [Omnius Event Store](https://github.com/kumar-devender/omnius-event-store)
