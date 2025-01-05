# Coin Change Calculator

This project is a Dropwizard-based application that calculates the minimum number of coins needed to make a given amount. The application exposes a REST API endpoint to perform the calculation.

## Example

Coin denomination must be one of the following values [0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000]

Output:
A list of minimum number of coins in ascending order
You can assume that you have infinite number of coins for each denomination

### Example 1:

Input:
Target amount: 7.03
Coin denominator: [0.01, 0.5, 1, 5, 10]
Output:
[0.01, 0.01, 0.01, 1, 1, 5]

### Example 2:

Input:
Target amount: 103
Coin denominator: [1, 2, 50]
Output:
[1, 2, 50, 50]

## Prerequisites

- Java 17 or later
- Gradle 8.8 or later

## Building the Project

To build the project, run the following command:

```sh
./gradlew build
```

This will compile the source code, run the tests, and create a JAR file in the libs directory.

## Running the Application

### Without Docker

To run the application without Docker, use the following command:

```sh
java -jar app/build/libs/coinchange-1.0.0-all.jar server app/resources/config.yml
```

### With Docker

To run the application with Docker, follow these steps:

1. Build the Docker image:

```sh
docker build -t coinchange-app .
```

2. Run the Docker container:

```sh
docker run -p 8080:8080 -p 8081:8081 coinchange-app
```

## API Endpoint

The application exposes the following endpoint:

- POST /coin-change: Calculates the minimum number of coins needed to make the given amount.

  ### Request

```json
{
  "targetAmount": 123.45,
  "coinDenominations": [0.01, 0.05, 0.1, 0.25, 1.0]
}
```

### Response

```json
{
  "coinChange": [1.0, 0.25, 0.1, 0.1, 0.01]
}
```

## Configuration

The application configuration is specified in the config.yml file. The configuration includes settings for the server and logging.

### Example Configuration

```yml
server:
applicationConnectors:
    - type: http
    port: 8080
adminConnectors:
    - type: http
    port: 8081
loggingConfig: ./resources/logback.xml
```

## Logging

The logging configuration is specified in the logback.xml file. The configuration includes settings for console and file appenders.

### Testing

To run the tests, use the following command:

```sh
./gradlew test
```

The test results will be available in the test directory.

## License

This project is licensed under the Apache License, Version 2.0. See the LICENSE file for details.

This `README.md` file provides an overview of the project, instructions for building and running the application with and without Docker, details about the API endpoint, configuration, logging, testing, and licensing.
