FROM maven:3.9.9-eclipse-temurin-21-alpine as build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline
RUN mvn package

FROM eclipse-temurin:21-jre-alpine-3.21 as run

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 6868

CMD ["java", "-jar", "app.jar"]