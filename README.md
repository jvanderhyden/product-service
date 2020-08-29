# myRetail - Product Service

A Spring Boot based RESTful service for obtaining product and price details.

## Getting Started

The recommended way to launch the application is via [Docker](https://docs.docker.com/desktop/). The project includes Dockerfile and docker-compose.yml to make it easy to spin up a local development environment.

#### Docker Compose

To build and launch the application alongside a MongoDB container, utilize the compose script by executing the following from the project root:

```
$ docker-compose up --build
```

> Configuration changes can be made in the **docker-compose.yml** file.

#### Docker

To build and launch the application on its own, execute the following commands from the project root:

```
$ docker build -t product:latest .
```

Configuration changes can be made by creating an environment file and passing it to the run command.

```
$ docker run -p 8080:8080 --env-file .env product:latest
```

## Configuration

The following environment variables can be used to configure the application:

Environment Variable | Description | Default
------------ | ------------- | -------------
MONGO_AUTH_DB | The name of the authentication database for MongoDB | admin
MONGO_USER | The username for MongoDB | root
MONGO_PASSWORD | The password for MongoDB | password
MONGO_PRICE_DB | The name of the price database for MongoDB | price
MONGO_HOST | The hose for MongoDB | localhost
MONGO_PORT | The port for MongoDB | 27017
REDSKY_BASE_URL | The base URL for the RedSky API | https://redsky.target.com
REDSKY_CONNECT_TIMEOUT | The connect timeout for the RedSky API client | 5000
REDSKY_READ_TIMEOUT | The read timeout for the RedSky API client  | 5000
REDSKY_EXCLUDES | Comma-delimited string of data elements to exclude from the RedSky response | bulk_ship,available_to_promise_network,question_answer_statistics,rating_and_review_reviews,rating_and_review_statistics,rating_and_review_reviews
REDSKY_KEY | The key used to authenticate with the RedSky API | candidate

## Testing

#### Unit Tests

Unit tests can be run via the **test** Gradle task by executing the following from the project root:

```
$ ./gradlew test
```

#### Component Tests

Component tests are executed against a transient environment that consists of the application, a MongoDB instance and a mock server to stand in for the RedSky API. Component tests can be run via the **componentTest** Gradle task by executing the following from the project root:

```
$ ./gradlew componentTest
```

## APIs

#### PUT /products/{id}

Used to set price information for an **id** specified in the path.

Example request:
```json
{
    "value": 12.34,
    "currency_code": "USD"
}
```

#### GET /products/{id}

Used to obtain product detail and price information for an **id** specified in the path.

Example response:
```json
{
    "id": 123,
    "name": "A Fine Product",
    "current_price": {
        "value": 12.34,
        "currency_code": "USD"
    }
}
```


