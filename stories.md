# Gárdonyi Márk
## Masterwork
---
# Movie recommendations

## Introduction
The aim of the app is to provide a simple web service for movie viewers where they can write short recommendations and possibly rate movies. Movies might be searched by title, name of actor/actress or director. 

## Planned database structure:
[Database diagramm](https://dbdiagram.io/d/6283b52e7f945876b63647f3)

---
## User stories:

### 1. Initialization of the project
Setup a new MySQL database. Create a Gitignore file. 
Setup a SpringBoot project with MySQL as production database and H2 as test database. Set all environment variables.

### 2. Viewer registration
As a frontend developer, I want to have a POST /register endpoint, so a user can register to the application.

*Scenario 1:*

Given the POST /register endpoint.
When I send request to it with a username.
Then I get an HTTP 400 error with message: "Password is required."

*Scenario 2:*

Given the POST /register endpoint.
When I send request to it with a password.
Then I get an HTTP 400 error with message: "Username is required."

*Scenario 3:*

Given the POST /register endpoint.
When I send an empty request to it.
Then I get an HTTP 400 error with message: "Username and password are required."

*Scenario 4:*

Given the POST /register endpoint.
When I send a taken username to it.
Then I get an HTTP 409 error with message: "Username is already taken."

*Scenario 5:*

Given the POST /register endpoint.
When I send a password which is under 8 character.
Then I get an HTTP 406 error with message: "Password must be at least 8 characters."

*Scenario 6:*

Given the POST /register endpoint.
When I send a request to it with a good username and password.
Then I get back an HTTP 201 response with the userId and username.


### 3. Viewer login
As a frontend developer, I want to have a POST /login endpoint, so when I send a user name a password, I get back a JWT token.

*Scenario 1:*

Given the POST /login endpoint.
When I send request to it with a username.
Then I get an HTTP 400 error with message: "Password is required."

*Scenario 2:*

Given the POST /login endpoint.
When I send request to it with a password.
Then I get an HTTP 400 error with message: "Username is required."

*Scenario 3:*

Given the POST /login endpoint.
When I send an empty request to it.
Then I get an HTTP 400 error with message: "All fields are required."

*Scenario 4:*

Given the POST /login endpoint.
When I send a request to it with wrong username or password.
Then I get an HTTP 401 error with message: "Username or password is incorrect."

*Scenario 5:*

Given the POST /login endpoint.
When I send a request to it with a good username and password.
Then I get back and HTTP 200 response with a JWT token.


### 4. Security
As a frontend developer, I want to have certain endpoints protected and only available for logged in users. 

*Public endpoints (available without logging in):*
- POST /register
- POST /login
- GET /movies

*Protected endpoints (available only with a valid JWT token):*
- All other endpoints

*Scenario 1:*

When there’s no token provided in a request to a protected endpoint,
Then the endpoint should return an HTTP 401 status with the message: “No authentication token is provided!“.

*Scenario 2:*

When in a request to a protected endpoint the provided authentication token is invalid,
Then the endpoint should return an HTTP 401 status with the message: “Authentication token is invalid!“.

*Scenario 3:*

When in a request to a protected endpoint the provided authentication token is valid,
Then we should decode the information (kingdomId, kingdomName, username) from the token,
And put it to the Security Context, so these data should be available in the endpoint.

### 5. Get top movies
As a frontend developer, I want to be able to retrieve the list of top rated movies from the database. 

*Scenario:*

Response with HTTP status 200 and body:
```
{
    "movies": [
        {
            "id": 123,
            "title": "asdasdsa",
            "rating": 9.9
        },
        ...
    ]
}
```
### 6. Get movies by director
As a frontend developer, I want to be able to retrieve the list of all movies made by a given director from the database. 

*Scenario 1:*

When the director's name is in the database then we receive a response with HTTP status 200 and body:
```
{
    "movies": [
        {
            "id": 123,
            "title": "asdasdsa",
            "rating": 7.4
        },
        ...
    ]
}
```

*Scenario 2:*

When the director's name is **not** in the database then we receive a response with HTTP status 404 and body:
```
{
    "status": "error",
    "message": "Director is not in the database"
}
```
### 7. Get movies by actor
As a frontend developer, I want to be able to retrieve the list of all movies that have a given actor/actress on the cast from the database. 

*Scenario 1:*

When the actor's name is in the database then we receive a response with HTTP status 200 and body:
```
{
    "movies": [
        {
            "id": 123,
            "title": "asdasdsa",
            "rating": 5.3
        },
        ...
    ]
}
```
*Scenario 2:*

When the actor's name is **not** in the database then we receive a response with HTTP status 404 and body:
```
{
    "status": "error",
    "message": "Actor/actress is not in the database"
}
```
### 8. Get a movie by id
As a frontend developer, I want to be able to retrieve a movie from the database by its id. 

*Scenario 1:*

When the movie id is in the database then we receive a response with HTTP status 200 and body:
```
{
    "id": 123,
    "title": "asdsdsdasds",
    "director": "wewrerer",
    "cast": [
        {
            "id": 789,
            "name": "xcycxv"
        },
        ...
    ],
    "release": 2022,
    "length": 91,
    "rating": 6.2,
    "recommendations": [
        {
            "id": 456,
            "text": "oioiipip",
            "rating": 7
        },
        ...
    ]
}
```
*Scenario 2:*

When the movie id is **not** in the database then we receive a response with HTTP status 404 and body:
```
{
    "status": "error",
    "message": "Movie is not in the database"
}
```

### 9. Get a movie by title
As a frontend developer, I want to be able to retrieve a movie from the database by its title. 

*Scenario 1:*

When the movie title is in the database then we receive a response with HTTP status 200 and body:
```
{
    "id": 123,
    "title": "asdsdsdasds",
    "director": "wewrerer",
    "cast": [
        {
            "id": 789,
            "name": "xcycxv"
        },
        ...
    ],
    "release": 2022,
    "length": 91,
    "rating": 6.2,
    "recommendations": [
        {
            "id": 456,
            "text": "oioiipip",
            "rating": 7
        },
        ...
    ]
}
```
*Scenario 2:*

When the movie title is **not** in the database then we receive a response with HTTP status 404 and body:
```
{
    "status": "error",
    "message": "Movie is not in the database"
}
```
### 10. Post a recommendation
As a frontend developer, I want to be able to save new recommendation of the logged in user to a given movie.

*Scenario 1:*

When the movie id is in the database and the user have not recommended it yet then we receive a response with HTTP status 201 and body:
```
{
    "id": 123,
    "movieId": 456,
    "rating": 5,
    "text": ""
}
```
*Scenario 2:*

When either the movie id or the rating is missing from the request body we receive a response with HTTP status 400 and body:
```
{
    "status": "error",
    "message": "<Parameter> is missing"
}
```

*Scenario 3:*

When the movie id is **not** in the database then we receive a response with HTTP status 404 and body:
```
{
    "status": "error",
    "message": "Movie is not in the database"
}
```
*Scenario 4:*

When the user has already recommended the given movie then we receive a response with HTTP status 409 and body:
```
{
    "status": "error",
    "message": "Recommendation already exists"
}
```
### 11. Modify a recommendation
As a frontend developer, I want to be able to modify a recommendation of the logged in user.

*Scenario 1:*

When the recommendation id is in the database and there is a valid rating in the request body then we receive a response with HTTP status 200 and body:
```
{
    {
    "id": 123,
    "movieId": 456,
    "rating": 5,
    "text": ""
}
```
*Scenario 2:*

When either the movie id or the rating is missing from the request body we receive a response with HTTP status 400 and body:
```
{
    "status": "error",
    "message": "<Parameter> is missing"
}
```

*Scenario 3:*

When the recommendation id belong to a different user we receive a response with HTTP status 403 and body:
```
{
    "status": "error",
    "message": "Forbidden action"
}
```
*Scenario 4:*

When the recommendation id or the movie id is **not** in the database then we receive a response with HTTP status 404 and body:
```
{
    "status": "error",
    "message": "<Parameter> is not in the database"
}
```
### 12. Delete a recommendation
As a frontend developer, I want to be able to remove a recommendation of the logged in user.

*Scenario 1:*

When the recommendation id is in the database then we receive a response with HTTP status 200 and body:
```
{
    "status": "ok",
    "message": "Recommendation is deleted"
}
```
*Scenario 2:*

When the recommendation id belong to a different user we receive a response with HTTP status 403 and body:
```
{
    "status": "error",
    "message": "Forbidden action"
}
```
*Scenario 4:*

When the recommendation id is **not** in the database then we receive a response with HTTP status 404 and body:
```
{
    "status": "error",
    "message": "Recommendation is not in the database"
}
```
### 13. Get the list of all directors

As a frontend developer, I want to be able to retrieve the list of all directors in the database.

*Scenario:*

Response with HTTP status 200 and body:
```
{
    "directors": [
        {
            "id": 123,
            "name": "sasfdfd"
        },
        ...
    ]
}
```
### 14. Add director

As a frontend developer, I want to be able to add new director to the database.

*Scenario 1:*

When the id and name are valid then we receive a response with HTTP status 201 and body:
```
{
    "id": 123,
    "name": "ycvcvbyvbv"
}
```
*Scenario 2:*

When either id or name is missing from the request body we receive a response with HTTP status 400 and body:
```
{
    "status": "error",
    "message": "<Parameter> is missing"
}
```
*Scenario 3:*

When either id or name is already present in the database we receive a response with HTTP status 409 and body:
```
{
    "status": "error",
    "message": "<Parameter> is already in the database"
}
```
### 15. Get the list of all actors/actresses

As a frontend developer, I want to be able to retrieve the list of all actors/actresses in the database.

*Scenario:*

Response with HTTP status 200 and body:
```
{
    "actors": [
        {
            "id": 123,
            "name": "sasfdfd"
        },
        ...
    ]
}
```
### 16. Add actor/actress

As a frontend developer, I want to be able to add new actor/actress to the database.

*Scenario 1:*

When the id and name are valid then we receive a response with HTTP status 201 and body:
```
{
    "id": 123,
    "name": "ycvcvbyvbv"
}
```
*Scenario 2:*

When either id or name is missing from the request body we receive a response with HTTP status 400 and body:
```
{
    "status": "error",
    "message": "<Parameter> is missing"
}
```
*Scenario 3:*

When either id or name is already present in the database we receive a response with HTTP status 409 and body:
```
{
    "status": "error",
    "message": "<Parameter> is already in the database"
}
```
### 17. Add new movie

As a frontend developer, I want to be able to add new movie to the database when an administrator is logged in.

*Scenario 1:*
When the title, director id and cast ids are valid we receive a response with HTTP status 201 and body:
```
{
    "id": 123,
    "title": "asdsdsdasds",
    "director": "wewrerer",
    "cast": [
        {
            "id": 789,
            "name": "xcycxv"
        },
        ...
    ],
    "release": 2022,
    "length": 91,
    "rating": 6.2,
    "recommendations": [
        {
            "id": 456,
            "text": "oioiipip",
            "rating": 7
        },
        ...
    ]
}
```
*Scenario 2:*

When either the title, the director id or the actor/actress ids are missing we receive a response with HTTP status 400 and body:
```
{
    "status": "error",
    "message": "<Parameter> is missing"
}
```
*Scenario 3:*

When the title is already present in the database we receive a response with HTTP status 409 and body:
```
{
    "status": "error",
    "message": "Movie is already in the database"
}
```
### 18. Modify a movie
As a frontend developer, I want to be able to modify the details of a movie in the database when an administrator is logged in.

*Scenario 1:*
When the title, director id and cast ids are valid we receive a response with HTTP status 201 and body:
```
{
    "id": 123,
    "title": "asdsdsdasds",
    "director": "wewrerer",
    "cast": [
        {
            "id": 789,
            "name": "xcycxv"
        },
        ...
    ],
    "release": 2022,
    "length": 91,
    "rating": 6.2,
    "recommendations": [
        {
            "id": 456,
            "text": "oioiipip",
            "rating": 7
        },
        ...
    ]
}
```
*Scenario 2:*
When either the title, the director id or the actor/actress ids are missing we receive a response with HTTP status 400 and body:
```
{
    "status": "error",
    "message": "<Parameter> is missing"
}
```
*Scenario 3:*

When the title is already present in the database we receive a response with HTTP status 409 and body:
```
{
    "status": "error",
    "message": "Movie is already in the database"
}
```
### 19. Delete movie
As a frontend developer, I want to be able to delete a movie from the database.

*Scenario 1:*

When the movie id is valid we receive a response with HTTP status 200 and body: 
```
{
    "status": "ok",
    "message": "Movie is deleted"
}
```