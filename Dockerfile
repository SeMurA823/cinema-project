FROM maven:3.8.6-openjdk-11-slim as build
COPY . ./
ENV SPRING_PROFILES_ACTIVE=test
RUN mvn clean package

FROM openjdk:11-jdk-slim
ARG JAR_FILE=./target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]