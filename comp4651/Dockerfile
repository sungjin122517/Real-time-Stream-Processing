FROM openjdk:21-slim
ARG JAR_FILE=build/libs/*.jar
COPY build/libs/*.jar app.jar
#ENV DATABASE_HOST=localhost
ENTRYPOINT ["java", "-jar", "/app.jar"]