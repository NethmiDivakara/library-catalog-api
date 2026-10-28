Library Catalog Api

A RESTful API developed using SpringBoot to manage a library's book catalogue.
This supports CRUD.

Java version-21
SpringBoot version-4.1.2
Maven -Maven Wrapper
Database -PostgresSQL

Dependencies used
• Spring Web (or Spring Web MVC, depending on Initializr label) - controllers and HTTP requests.
• Spring Data JPA - entity persistence and repository abstraction.
• PostgreSQL Driver - JDBC connection to PostgreSQL.
• Validation - request validation with Jakarta Bean Validation.
• Spring Boot Starter Test - included with generated tests; keep it in the project

Project Setup
•Clone the project
•cd library-catalog-api

Database creation
Create a PostgreSQL database named library_catalog
Configure the database connection using environment variables.

The following configuration is used in application.properties:

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.datasource.driver-class-name=org.postgresql.Driver

Required Env 

DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD


Run the application using the Maven Wrapper.

Windows
.\mvnw.cmd spring-boot:run
Linux / macOS
./mvnw spring-boot:run

The application runs at:

http://localhost:8080

Endpoint table 

POST /api/v1/books Create a book from a validated JSON request; generate its id -> 201 Created + created book
GET /api/v1/books  Return all books; an empty catalogue returns []   ->  200 OK + array
GET /api/v1/books/{id} Return one book by id  -> 200 OK + book
PUT /api/v1/books/{id} Fully update the allowed book fields for an existing id  -> 200 OK + updated book
DELETE /api/v1/books/{id}  Delete the existing book by id  -> 204 No Content

