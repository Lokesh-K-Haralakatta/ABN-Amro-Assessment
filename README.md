# ABN-Amro-Assessment: Recipe Web Service
Recipe Web Service is specifically designed and implemented as part of ABN Amro Technical Interview. Recipe Web Service contains ReST APIs in order to Create, Get, Update and Delete recipe from the database and render the requested details as JSON response to end user. The response from ReST APIs can be further integrated with front end view for better presentation.

### System Design
Recipe Web Service is microservice based layered architectured RESTful Web Service. This service can be deployed independently on premise / cloud and can also be containerized to execute as docker containers. There are 4 layers from top to bottom:
- API Layer
  - Top layer, which is main interface available for intgeration and interaction with front-end or end user to consume APIs
  - Contains secured API end points implementation
  - [Springboot-starter-security](https://spring.io/guides/gs/securing-web/) Module along with JWT is used to implement authentication for APIs 
  - [Springboot-starter-web](https://spring.io/guides/gs/rest-service/) module used as a framework to implement ReSTful api end points  
- Service Layer
  - This layer sits in between API layer and Data access layer with some utility functionality
  - Mainly responsible for interacting with Data Access Layer and transferring the recipes data as required by top and below layers
  - It's just another module added to decouple business logic of recipes data transfer and mapping from/to API layer
  - Further, service layer can be enhanced to support advanved features like Caching, Interacting with external Authorization Service etc
- Data Access Layer
  - Responsible to provide Object Relationship Mapping (ORM) between higher level recipe Java objects and persistence layer tables
  - [Springboot-starter-data-JPA](https://spring.io/guides/gs/accessing-data-jpa/) module is used to implement mappings between objects and tables
  - This layer contains recipe entity classes and JPA repositories which implement lower level functionality of storing/retrieving recipes data  
- Persistence Layer
  - Bottom most layer, responsible for physically storing the recipes data onto database table
  - Just one physical table - `recipes` is used to store the recipes data for the service
  - [MySQL]((https://www.mysql.com/) is configured to be used as database service
  - For development and testing purposes, the Embedded H2 Database provided by Spring Boot framework is also utilized 

### Webservice API Flow
![Recipe Webservice API Flow](https://github.com/Lokesh-K-Haralakatta/ABN-Amro-Assessment/blob/develop/recipw-web-service-flow.png)
### Prerequisites
* [JDK 1.8](https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html)
* [Apache Maven](https://maven.apache.org/)
* [MySQL](https://www.mysql.com/)
* [Git](https://git-scm.com/)

### Steps to build Web Service
* Download code zip / `git clone https://github.com/Lokesh-K-Haralakatta/ABN-Amro-Assessment`
* Move to `ABN-Amro-Assessment` and run maven build command `mvn clean package`
* On successfull build completion, one should have web service jar in `target` directory

### Steps to execute Web Service
* Execution on Development profile with Embedded H2 Database
  - In Development Mode, by default web service uses [Embedded H2 database](https://spring.io/guides/gs/accessing-data-jpa/) for persisting and retrieving recipes details.
  - Command to execute: `java --spring.profiles.active=dev -jar target/ABN-Amro-Recipes-Service-0.0.1-SNAPSHOT.jar`
  - On successfull start, one should have web service listening for web requests at port 9000
* Execution on Development profile with MySQL Database
  - In Development mode, one can also execute web service against local [MySQL Service](https://www.mysql.com/) for persisting and retrieving recipes details.
  - Specify required [MySQL Service](https://spring.io/guides/gs/accessing-data-mysql/) configuraiton parameters in `application.properties` file as given below:
    -  server.port=9000
    -  spring.profiles.active=dev
    -  spring.jpa.hibernate.ddl-auto=update
    -  spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_name
    -  spring.datasource.username=mysql-username
    -  spring.datasource.password=mysql-user-password
    -  spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  - Web service needs database table with name `RECIPES` to be present in configured MySQL Database. Use below given table schema to create one before execution
    - CREATE TABLE recipes(id INT PRIMARY KEY, name VARCHAR(50), type VARCHAR(4),cdatetime TIMESTAMP, capacity INT, ingredients TEXT, instructions TEXT);  
  - Command to execute with custom application.properties file: `java --spring.config.location=application.properties -jar target/ABN-Amro-Recipes-Service-0.0.1-SNAPSHOT.jar`
  - On successfull start, one should have web service listening for web requests at port 9000
* Execution on Production Profile with MySQL Database
  - In order to execute on Production, set the required configuration parameters in application.properties file
    -  server.port=required-port-number
    -  spring.profiles.active=prod
    -  spring.jpa.hibernate.ddl-auto=update
    -  spring.datasource.url=jdbc:mysql://${MYSQL_HOST:hostName}:port-Number/db_name
    -  spring.datasource.username=mysql-username
    -  spring.datasource.password=mysql-user-password
    -  spring.datasource.driver-class-name=com.mysql.jdbc.Driver
  - Web service needs database table with name `RECIPES` to be present in configured MySQL Database. Use below given table schema to create one before execution
    - CREATE TABLE recipes(id INT PRIMARY KEY, name VARCHAR(50), type VARCHAR(4),cdatetime TIMESTAMP, capacity INT, ingredients TEXT, instructions TEXT);    
  - Command to execute with custom application.properties file: `java --spring.config.location=application.properties -jar target/ABN-Amro-Recipes-Service-0.0.1-SNAPSHOT.jar`
  - On successfull start, one should have web service listening for web requests at specified port in `application.properties` file interacting with configured production grade MySQL Service for persistence and retrieval of recipes details.      

### Web Service ReST End Points

### Web Service ReST End Points Usage and Sample Response

### Future Enhancements
- Make Web Service ReST End Points Secure
- Add Containerisation Feature
- Design and build simple,beautiful front end view
- Integration between Backend API with Front End view
- Data Normalization in Persistence layer if recipes data grows exponentially
### Contribution
Development of this web service is with MIT License. Any developer who is interested in contributing for adding additional features are welcome to contact through Email Id: lokesh.h.k@gmail.com
