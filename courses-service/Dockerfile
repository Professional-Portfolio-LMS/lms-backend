# Build
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn -q -DskipTests clean package

# Run
FROM eclipse-temurin:21
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

HEALTHCHECK --interval=10s --timeout=5s --start-period=20s CMD curl -f http://localhost:8082/actuator/health || exit 1
ENTRYPOINT ["java","-jar","app.jar"]
