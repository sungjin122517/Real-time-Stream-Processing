# Use a base image with Java and Maven installed
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY pom.xml .
COPY src ./src

# Build the Maven project
RUN mvn clean package

# Use a lightweight Java runtime image
FROM openjdk:21-slim AS runtime

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime container
COPY --from=build /app/target/*.jar app.jar

# Specify the command to run the application
CMD ["java", "-jar", "app.jar"]