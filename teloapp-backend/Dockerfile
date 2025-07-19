# Etapa 1: Construir la aplicación
FROM maven:3.8-openjdk-17 AS build
# Copia todo el repositorio al contenedor
WORKDIR /app
COPY . .
# Muévete a la carpeta del backend y ejecuta Maven desde ahí
WORKDIR /app/teloapp-backend
RUN mvn clean install -DskipTests

# Etapa 2: Crear la imagen final
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Ajusta la ruta para copiar el .jar desde la ubicación correcta
COPY --from=build /app/teloapp-backend/target/teloapp-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]