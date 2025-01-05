# Coin Change Calculator Backend

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
java -jar coinchange-backend/build/libs/coinchange-1.0.1-all.jar server coinchange-backend/resources/config.yml
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

# Coin Change Calculator Frontend

This project is a React-based frontend application that interacts with a Dropwizard backend to calculate the minimum number of coins needed to make a given amount. The application dynamically filters available coin denominations based on the target amount entered by the user.

## Prerequisites

- Node.js (version 14 or later)
- npm (version 6 or later)

## Available Scripts

In the project directory, you can run:

### `npm run start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However, we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

## Functionality

### Coin Change Calculator

The Coin Change Calculator allows users to input a target amount and dynamically filters the available coin denominations based on the input. The filtered denominations are then sent to the backend to calculate the minimum number of coins needed.

#### Features

- **Target Amount Input**: Users can input the target amount they want to calculate the coin change for.
- **Dynamic Coin Denominations**: The available coin denominations are dynamically filtered based on the target amount.
- **Calculate Coin Change**: The filtered denominations are sent to the backend to calculate the minimum number of coins needed.
- **Error Handling**: Displays error messages if the calculation fails.
- **Clear Coin Change**: Clears the calculated coin change when the target amount is cleared.

## Example Usage

1. **Enter Target Amount**: Input the target amount in the "Target Amount" field.
2. **View Available Coin Denominations**: The available coin denominations will be displayed and dynamically filtered based on the target amount.
3. **Calculate Coin Change**: Click the "Calculate" button to calculate the minimum number of coins needed.
4. **View Coin Change**: The calculated coin change will be displayed below the button.
5. **Clear Coin Change**: Clear the target amount to reset the coin change calculation.
