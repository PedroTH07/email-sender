FROM maven:3-eclipse-temurin-21-alpine AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn clean install

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/target/emailsender-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]