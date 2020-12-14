# roulette-api

This is a simple application to simulate roulette functionalities through API rest, supporting the following operations 
   * Create roulette
   * List roulette with current status
   * Open roulette
   * Bet in roulette
   * Close roulette     

## Project Structure
This project follow clean code directives and is aligned by clean Architecture definitions.
```
.
|-- gradle
|-- src
|   |-- main
|       |--java
|           |--base package
|               |--adapter (inbound and outbound adapters (controllers, repositories))
|               |--assembler (configurations, bean definitions)
|               |--kernel (Object for business logic, (Domian modles, Business excetions)) 
|               |--usercase (Business logic, (Services for business logic))
|               |--util (Utilities)
|       |--resources (application config file)
|   |-- test (All Unit Test)
|       |--java
|-- Dockerfile
|-- docker-compose.yml
|-- README.md
|-- (gradle files)

```

## Libraries and technologies integrated

#### For programming
   * Java JDK 8
   * Spring Starter Web (2.4.0)
   * Spring Starter Validations (2.4.0)
   * Spring Data Redis (2.4.0)
   * Jadis
   * lombok (1.18.8)
   * Validation (2.0.1)
#### For Test
   * JUnit
  
## Requirements

For building and running the application you need:

* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
* [Gradle 4+](http://www.gradle.org/downloads)


## Running with Docker

### Prerequisites

#### Docker

Docker and Docker Compose are used to build and deploy this application.

Installation instructions for Docker can be found on its [Get Started](https://docs.docker.com/get-started/) page.

If your installation of Docker did not come with Docker Compose, you can follow its
[install instructions](https://docs.docker.com/compose/install/).

### Running

To start this application run the following command over root folder:

```
$ docker-compose up -d
```
User the command **-d** to run the instruction in background


## Testing

The user could use the following instruction to test the Roulette API

* Create roulette
```
[POST] http://localhost:9090/roulette/

[RESPONSE DOBY]
{
  "rouletteId": "ae43de9c-50be-417c-b67d-c83080d52ef0"
}
```

* List roulette with current status
```
[GET] http://localhost:9090/roulette/

[RESPONSE DOBY]
[
  {
    "id": "d5aa7601-62b5-40ad-8a95-870106671315",
    "status": "OPEN"
  },
  {
    "id": "ae43de9c-50be-417c-b67d-c83080d52ef0",
    "status": "CREATED"
  }
]
```
* Open roulette
```
[POST] http://localhost:9090/roulette/{rouletteId}/open

[RESPONSE DOBY - HTTP Status 204]

```

* Bet in roulette
```
[POST] http://localhost:9090/roulette/{rouletteId}/bet
[REQUEST HEADER] 
userId:{AnyUserID}
[REQUEST BODY]
{
    "type":"COLOR",
    "value": 5000,
    "color": "BLACK",
    "number": null
}
[RESPONSE DOBY - HTTP Status 204]

```

* Close roulette  
```
[POST] http://localhost:9090/roulette/{rouletteId}/close
[RESPONSE DOBY]
{
  "winningNumber": 35,
  "betResults": [
    {
      "userId": "1234rtgfdrtyt43w",
      "type": "COLOR",
      "value": 5000,
      "color": "RED",
      "number": null,
      "result": 0.0
    },
    {
      "userId": "1234rtgfdrtyt43w",
      "type": "COLOR",
      "value": 5000,
      "color": "BLACK",
      "number": null,
      "result": 9000.0
    },
    {
      "userId": "1234rtgfdrtyt43w",
      "type": "NUMBER",
      "value": 1000,
      "color": null,
      "number": 20,
      "result": 0.0
    },
    {
      "userId": "1234rtgfdrtyt43w",
      "type": "NUMBER",
      "value": 1000,
      "color": null,
      "number": 10,
      "result": 0.0
    },
    {
      "userId": "1234rtgfdrtyt43w",
      "type": "NUMBER",
      "value": 1000,
      "color": null,
      "number": 36,
      "result": 0.0
    }
  ]
}

```

In the following Insomnia [File](https://github.com/williamlema/roulette-api/blob/main/request/Insomnia_2020-12-14.json) will find the different endpoints that support all functionalities in this API
