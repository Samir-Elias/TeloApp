# Etapa 1: Construir la aplicación con Maven
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

# Etapa 2: Crear la imagen final y ligera para correr la aplicación
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/target/teloapp-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]