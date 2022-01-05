FROM openjdk:11-jdk-slim
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-cp", "-Dspring.profiles.active=prod", "app.jar", "com.muravyev.cinema.CinemaApplication"]