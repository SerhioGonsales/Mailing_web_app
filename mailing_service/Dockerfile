# Stage 1: Сборка приложения
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /mailing-service
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Запуск приложения
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /mailing-service/target/mailing-service-*.jar app.jar
CMD ["java", "-jar", "app.jar"]