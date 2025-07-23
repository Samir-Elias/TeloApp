# --------------- STAGE 1: Build la aplicación Spring Boot ---------------
FROM openjdk:17-jdk-slim as builder

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos del proyecto Maven (pom.xml y src)
# Esto permite que Maven descargue dependencias y luego compile el código
COPY pom.xml .
COPY src ./src

# Ejecuta el build de Maven. -DskipTests para omitir las pruebas.
RUN mvn clean install -DskipTests

# --------------- STAGE 2: Crea la imagen final ligera ---------------
FROM openjdk:17-jdk-slim

# Crea un volumen para los logs (opcional, pero buena práctica)
VOLUME /tmp

# Copia el JAR compilado desde la etapa 'builder'
# Asegúrate de que el nombre del JAR coincida con el que genera tu build (artifactId en pom.xml)
ARG JAR_FILE=target/teloappbackend-0.0.1-SNAPSHOT.jar # Debería ser teloappbackend ahora
COPY --from=builder /app/${JAR_FILE} app.jar

# Define el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-cp", "app.jar", "com.teloapp.teloappbackend.TeloappBackendApplication", "--spring.profiles.active=production"]