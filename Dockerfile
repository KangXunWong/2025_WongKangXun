# Use Amazon Corretto 21 as a parent image
FROM amazoncorretto:21

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY coinchangebackend/build/libs/coinchange-1.0.0-all.jar /app/coinchange.jar

# Copy the configuration file to the container
COPY coinchangebackend/resources/config.yml /app/config.yml

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/coinchangebackend/coinchange.jar", "server", "/app/config.yml"]