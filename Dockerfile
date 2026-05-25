# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/hms-backend-1.0.0.jar hms-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hms-backend.jar"]
