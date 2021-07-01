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

### Supported Features
Feature | Software Module Used
------------ | -------------
ReSTful API | [Springboot](https://spring.io/projects/spring-boot)
API Authentication | [Spring Security](https://spring.io/projects/spring-security) with JWT Token
Object Relationship Mapping | [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
Exception Handling | [Controller Advice and ExceptionHandler](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc)
Logging | [SLF4J](http://www.slf4j.org/manual.html) Logger
Unit Tests | Junit 5 with [AssertJ](https://assertj.github.io/doc/)

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

### Web Service ReST API End Points
Recipe Webservice comes with ReST API Ends points for authentication, creating a new recipe, retrieveing an existing recipe, retrieving all existing recieps as list, updating an an existing recipe and deleting an existing recipe. Below table lists and describes on the implemented ReST APIs:
API End Point | Method | Purpose | Request | Response
------------ | ------------- | ------------- | ------------ | ------------- 
/api/authenticate | POST | Authenticate and get JWT Token | User Model with user name and password | JWT Token on Success, 403 Forbidden on failure
/api/recipe | POST | Create a new recipe | Recipe Model and valid JWT Token as bearer token as auth header| Recipe Model with 201 Created on Success, 400 Bad request on failure
/api/recipe/{id} | GET | Get an existing recipe | Recipe id as path parameter and valid JWT Token as bearer token as auth header | Recipe Model with 200 OK on Success, 401 Not Found on failure
/api/recipes | GET | Gel all existing recipes as list | Valid JWT Token as bearer token as auth header | Recipes as list with 200 OK on success, 401 Not Found on failure
/api/recipe | PUT | Update an existing recipe | Updated Recipe Model and valid JWT Token as bearer token as auth header | Recipe Model with 200 OK on Success, 401 Not Found on failure
/api/recipe/{id} | DELETE | Delete an existing recipe | Recipe id as path parameter and valid JWT Token as bearer token as auth header | Deletion message with 200 OK on success, 401 Not Found on failure

### Web Service ReST End Points Usage and Sample Response
In order to consume Recipe Webservice ReST API End points, one has to first authenticate and get JWT Token in order to place subsequent client requests. There are 2 object models needed to be aware of - one for Authentication and the other one for Recipe contents. Below given are details and examples on needed models:
- **User Model**
  - JSON Schema
    ```
    {
	    "userName": "Username as configured for recipe web service",
	    "password": "JWT Secret Key as configured for recipe web service"
    }
    ```
  - JSON Example
    ```
    {
	     "userName": "abnamro",
	     "password": "recipeKey"
    }
    ```
- **Recipe Model**
  - JSON Schema
    ```
    {
	      "id": "recipeId as integer value",
	      "name": "recipeName as string",
	      "type": "recipeType - ng/vg/eg as string",
	      "servingCapacity": "number of people the dish to be served as integer value",
	      "creationDateTime": "creation date time as Date or null",
	      "ingredients": "list of ingredients objects with name and quantity as fields or null",
	      "instructtions": "step by step procedure to prepare recipe as text or null"
    }
    ```
  - JSON Example 
    ```
    {
	      "id": 101,
	      "name": "Sweet Bun",
	      "type": "vg",
	      "servingCapacity": 5,
	      "creationDateTime": null,
	      "ingredients": [{
		                      "name": "all purpose floor",
		                      "quantity": "500 gms"
	                      }, {
		                      "name": "yeast",
		                      "quantity": "5 gms"
	                      }, {
		                      "name": "sugar",
		                      "quantity": "10 gms"
	                      }, {
		                      "name": "milk",
		                      "quantity": "100 ml"
	                      }],
	       "instructtions": "1.Activate the yeast by with milk and sugar \n 2.Take all purpose floor in mixing bowl and mix with sugar and milk \n 3.Mix yeast with floor in bowl\n 4.Wait for few minutes to raise and then put inti oven for about 20-25 minutes at 180 degree temperatur"
      }
    ```
- ReST API Calls and responses
  - POST request to `/api/authenticate` end point with above given user model :
  - POST request to `/api/recipe` end point with above given recipe model and JWT Token as auth header returned in call to `/api/authenticate` :
  - GET request to `/api/recipe/{id}` end point with path parameter as recipe id: `/api/recipe/101` end point :
  - GET request to `/api/recipes` end point :
  - PUT request to `/api/recipe` end point with updated model with name renamed to `Milk Bun` and servingCapacity to 6 :
  - GET request to `/api/recipe/{id}` end point with path parameter as recipe id: `/api/recipe/101`, one should notice updated fields in previous request reflected :
  - DELETE request to `/api/recipe/{id}` end point with path parameter as recipe id: `/api/recipe/101`, one should notice response message and status code as 200 OK :
  - GET request to `/api/recipe/{id}` end point with path parameter as recipe id: `/api/recipe/101`, one should notice response message and status code as 401 Not Found :
  
### Future Enhancements
- Integrate Web Service with Authorisation servers
- Design and build simple,beautiful front end view
- Integration between Backend API with Front End view
- Data Normalization in Persistence layer if recipes data grows exponentially
### Contribution
Development of this web service is with MIT License. Any developer who is interested in contributing for adding additional features are welcome to contact through Email Id: lokesh.h.k@gmail.com
