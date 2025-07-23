# ... (todas las líneas de la etapa builder) ...

# --------------- STAGE 2: Crea la imagen final ligera ---------------
FROM openjdk:17-jdk-slim

# Crea un volumen para los logs (opcional, pero buena práctica)
# VOLUME /tmp # Asegúrate de que esta línea esté comentada o eliminada

# Copia el JAR compilado desde la etapa 'builder'
ARG JAR_FILE=target/teloappbackend-0.0.1-SNAPSHOT.jar 
COPY --from=builder /app/${JAR_FILE} app.jar

# Define el punto de entrada para ejecutar la aplicación
# Usamos el modo estándar 'java -jar' ya que sabemos que funciona localmente
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=production"]