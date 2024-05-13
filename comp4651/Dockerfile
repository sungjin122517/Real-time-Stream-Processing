# Use a base image with JDK and Gradle installed
FROM gradle:jdk21 AS build

# Set working directory inside the container
WORKDIR /home/gradle/project

# Copy Gradle build files
COPY build.gradle settings.gradle /home/gradle/project/
COPY gradle /home/gradle/project/gradle

# Copy project source code
COPY src /home/gradle/project/src

# Build the project
RUN gradle build -x test

# Create a new stage for the application runtime
FROM openjdk:21-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /home/gradle/project/build/libs/*.jar /app/app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]