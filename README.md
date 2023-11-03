# eTicket-System backend project
The main objective of this app is to provide some api's where user can check their online fines (fines are usually referred to during developing as ticket-details)
and create payments based on the fines (ticket-details) they have.

## Technologies used for this application are:

- Java 17
- Spring Boot 2.7
- Spring Data Jpa
- Spring Security
- JWT Authorization
- PostgreSQL DB
- Flyway
- Docker 
- Swagger 
- Java Mail
- Email Confirmation Payment


### Things to do before running the App

- execute the file inside docker/docker-compose.yml in order to set up the DB
- use Swagger for interacting with the api-s, Swagger url: (http://localhost:${server_port}/swagger-ui/index.html)
- Flyway is used for better migration of the db in the future

### Feature
A confirmation email will be send if the user provides an email address

Application also needs a mail server app to be running in order to complete the task

I have used MailDev which is for development purposes and can be easley set up with following command: sudo docker run -p 1080:1080 -p 1025:1025 maildev/maildev



### Configuration settings

| Config file parameter         | Env variable  | To be configured | Default Value | Description                                                                                                           |
|-------------------------------|---------------|:----------------:|---------------|-----------------------------------------------------------------------------------------------------------------------|
| server.port                   | server_port   |                  | 8080          | Application port (HTTP protocol )                                                                                     |
| spring.datasource.username    | db_username   |        Y         | ewow-user     | Postgres user                                                                                                         |
| spring.datasource.password    | db_password   |        Y         | ewow_password | Postgres password                                                                                                     |
| spring.datasource.url         | db_url        |        Y         | 172.28.0.2    | Postgres host, (is the host that will be configured on docker file, docker container that will be created for the db) |
| spring.datasource.url.port    | db_port       |        Y         | 5432          | Postgres port                                                                                                         |
| spring.datasource.url.db_name | db_name       |        Y         | eticket-db    | Postgres DB name                                                                                                      |
| spring.mail.host              | mail_host     |        Y         | localhost     | host for mail server                                                                                                  |
| spring.mail.username          | mail_username |        Y         |               | Default value can be assigned, (exmp: test)                                                                           |
| spring.mail.password          | mail_password |        Y         |               | Default value can be assigned, (exmp: test)                                                                           |
| spring.mail.port              | mail_port     |        Y         | 1025          | Port for mail server                                                                                                  |