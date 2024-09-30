## Getting Started
Event Alert - Backend is a REST API server that provides the required endpoints for the client applications.  
The technology stack consists of:
* [Java](https://www.oracle.com/java/) - The programming language used to develop the application.
* [Spring Boot](https://spring.io/projects/spring-boot#overview) - The base framework used for the REST controllers, services, dependency injection, Tomcat server and everything else needed by a stand-alone application.
* [Spring Security](https://spring.io/projects/spring-security) - The framework used to secure the application and provide both authentication and authorization.
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - The framework used to provide Data Access Object interfaces by extending its repositories.
* [Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging) - The cross-platform messaging solution to send notifications.
* [Hibernate](https://hibernate.org/) - The Object-Relational Mapping framework used to map the object-oriented domain models to the relational database.
* [MySQL Server](https://dev.mysql.com/doc/refman/5.7/en/) - The database server.
* [Liquibase](https://www.liquibase.com/) - The library for tracking, managing and applying database schema changes.
* [OAuth 2.0](https://oauth.net/2/) - The protocol used for authorization.
* [JWT](https://jwt.io/introduction/) - The standard used to securely transmit the information.
* [Project Lombok](https://projectlombok.org/) - The library used to minimize boilerplate code and save development time.

## Project scope
The purpose of this project is to provide all the required features for users that may want to be aware of the incidents reported around them.  
A user must be registered and authenticated in order to access these features.  
Then he can update his profile and report an event if needed.  
Also, he can search for the events reported by other users around his location.  
A user who is an admin can also modify or delete other users, the events reported by them, the comments posted by them, etc.  

## Run prerequisites
In order to run the application locally, the following steps must be set:
* Minimum software versions:
  * JDK 17
  * MySQL Server 5.7
  * Maven 3.9.2

* The MySQL database must be created with the following commands:
```
create database event_alert;
create user 'event_alert_user' identified by '1234qwer';
grant all on event_alert.* to 'event_alert_user';
```
&emsp;&emsp;The connection details are specified in [application.yml](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/application.yml#L13,L15).<br/>
&emsp;&emsp;At application startup, Liquibase will run the [scripts](https://github.com/adrianscarlatescu/event-alert-backend/tree/master/src/main/resources/db/changelog/scripts) declared. 
It will create the required tables and insert some basic data.
* Push notifications feature (to skip this feature, nothing has to be done):
  * Create a [Firebase](https://console.firebase.google.com/) project and generate the service account private key.
  This key must be put in `/resources/firebase/firebase-service-account.json`.
  * Set [app.notification.enabled](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/application.yml#L44) to `true`.

## Database schema
<img alt="Database schema" src="https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/readme/database_schema.png" width="800">  

## Authorization diagram
<img alt="Authorization diagram" src="https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/readme/authorization_diagram.png" width="800">

## Request - response example
The target is to get all the events filtered by the following *body*:
```
{
    "startDate": "2020-04-01",
    "endDate": "2020-04-21",
    "latitude": 44.8481,
    "longitude": 24.8839,
    "radius": 100,
    "severitiesIds": [1, 2, 3, 4],
    "tagsIds": [1, 2, 3, 4, 5, 6, 7, 20, 21, 22]
}
```
Field description:
* *startDate* - the event's report date must be after this value.
* *endDate* - the event's report date must be before this value.
* *latitude* - it is the latitude of the coordiante from which the request is made.
* *longitude* - it is the longitude of the coordiante from which the request is made.
* *radius* - it is the radius in kilometers of the circle having its center as the coordinate made by the *latitude* and the *longitude* mentioned above (the events coordinates must be within this circle's area).
* *severitiesIds* - the event's severity identifier must be among these values.
* *tagsIds* - the event's tag identifier must be among these values.

Required *headers*:
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJBY2Nlc3NUb2tlbklkIiwic3ViIjoidGVzdDFAdGVzdC5jb20iLCJpYXQiOjE1ODkwMzY4NTYsImV4cCI6MTU4OTAzODA1Nn0.9Xg-KVRtDcu2Pr2IhZI7ZwAryfMh1zY5cq22j84h3okIm3xC4QxxWH-fXhdvwgcLp-XD8Mj9Pny1lcIAQrg5pg
Content-Type: application/json
```
Specific *query parameters* for paged response:
```
pageSize: 20
pageNumber: 0
```
* Note: the distance between the request location and the event is calculated with the following MySQL function: [ST_Distance_Sphere(g1, g2 [, radius])](https://dev.mysql.com/doc/refman/5.7/en/spatial-convenience-functions.html#function_st-distance-sphere).  

Response:
```
{
    "totalPages": 1,
    "totalElements": 1,
    "content": [
        {
            "id": 20,
            "dateTime": "2020-04-18T14:15:31",
            "latitude": 44.8671652,
            "longitude": 24.8496802,
            "imagePath": "img/event_20.jpg",
            "description": null,
            "severity": {
                "id": 2,
                "name": "Major",
                "color": 16745779
            },
            "tag": {
                "id": 2,
                "name": "Fire",
                "imagePath": "img/tag_fire.png"
            },
            "user": {
                "id": 1,
                "firstName": "Alan",
                "lastName": "Walter",
                "imagePath": "img/user_1.jpg"
            },
            "distance": 4.263562194233072
        }
    ]
}
```
Postman [collection](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/postman/Event%20Alert%20-%20Collection.postman_collection.json) /
[local environment](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/postman/Event%20Alert%20-%20Local.postman_environment.json).  

## Client applications
* [event-alert-android](https://github.com/adrianscarlatescu/event-alert-android)
* [event-alert-frontend](https://github.com/adrianscarlatescu/event-alert-frontend)
