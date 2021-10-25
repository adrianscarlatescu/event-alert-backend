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
* [JUnit](https://junit.org/junit4/) - The framework used to write repeatable tests.
* [Mockito](https://site.mockito.org/) - The mocking framework used to add dummy functionality that is used in unit testing.
* [JWT](https://jwt.io/introduction/) - The standard used to securely transmit the information.
* [Project Lombok](https://projectlombok.org/) - The library used to minimize boilerplate code and save development time.

## Project scope
The purpose of this project is to provide all the required features for users that may want to be aware of the incidents reported around them.  
A user must be registered and authenticated in order to access these features.  
Afterwards he can update his profile and report an event if needed.  
Also, he can search for the events reported by other users around his location.  
A user who is an admin can also modify or delete other users, the events reported by them, the comments posted by them, etc.  

## Run prerequisites
In order to run the application locally, the following steps must be set:
* Run the commands highlighted in this [script](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/static/database_query.sql#L1,L6) in order to have the database ready.
The connection details are specified in [application.properties](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/application.properties#L4,L6).
* Push notifications feature:
    * To skip this feature, set [app.notification.enabled](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/application.properties#L23) to `false`.
    * In order to send push notifications, create a Firebase project and generate the service account private key.
This key must be put in [firebase-service-account.json](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/static/firebase-service-account.json).


## Database schema
<img src="https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/static/database_schema.png" width="600">  

* Note: dummy data was added with the following [script](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/static/database_query.sql).

## Authorization diagram
<img src="https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/static/authorization_diagram.png" width="800">

## Request - response example
The target is to get all the events filtered by the following *body*:
```
{
    "startDate": "2020-04-01T00:00:00",
    "endDate": "2020-04-21T23:59:59",
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
    "totalElements": 2,
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
                "joinDateTime": "2020-05-07T19:35:40",
                "email": "test1@test.com",
                "firstName": "Alan",
                "lastName": "Walter",
                "dateOfBirth": "1984-05-23",
                "phoneNumber": "+03442777999",
                "imagePath": "img/user_1.jpg",
                "gender": "MALE",
                "userRoles": [
                    {
                        "id": 2,
                        "name": "ROLE_ADMIN"
                    },
                    {
                        "id": 1,
                        "name": "ROLE_USER"
                    }
                ],
                "reportsNumber": 23
            },
            "distance": 4.263562194233072
        },
        {
            "id": 16,
            "dateTime": "2020-04-15T22:21:43",
            "latitude": 44.8466356,
            "longitude": 24.8753764,
            "imagePath": "img/event_16.jpg",
            "description": null,
            "severity": {
                "id": 4,
                "name": "Trivial",
                "color": 11192420
            },
            "tag": {
                "id": 20,
                "name": "Traffic accident",
                "imagePath": "img/tag_traffic_accident.png"
            },
            "user": {
                "id": 1,
                "joinDateTime": "2020-05-07T19:35:40",
                "email": "test1@test.com",
                "firstName": "Alan",
                "lastName": "Walter",
                "dateOfBirth": "1984-05-23",
                "phoneNumber": "+03442777999",
                "imagePath": "img/user_1.jpg",
                "gender": "MALE",
                "userRoles": [
                    {
                        "id": 2,
                        "name": "ROLE_ADMIN"
                    },
                    {
                        "id": 1,
                        "name": "ROLE_USER"
                    }
                ],
                "reportsNumber": 23
            },
            "distance": 0.9592219050088762
        }
    ]
}
```
Full Postman requests collection is available [here](https://github.com/adrianscarlatescu/event-alert-backend/blob/master/src/main/resources/static/EventAlert.postman_collection.json).  
The variables *{{url}}*, *{{accessToken}}* and *{{refreshToken}}* have to be declared.

## Client applications
* [event-alert-android](https://github.com/adrianscarlatescu/event-alert-android)
* [event-alert-frontend](https://github.com/adrianscarlatescu/event-alert-frontend)
