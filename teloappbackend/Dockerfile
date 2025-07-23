# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Crea un volumen para los logs (opcional, pero buena práctica para despliegues)
VOLUME /tmp

# Copia el archivo JAR de tu aplicación (asegúrate de que el nombre del JAR coincida con el que genera tu build)
ARG JAR_FILE=target/teloappbackend-0.0.1-SNAPSHOT.jar  # <-- ¡CAMBIAR AQUÍ: SIN GUION BAJO!

# Añade tu JAR al contenedor
COPY ${JAR_FILE} app.jar

# Define el punto de entrada para ejecutar la aplicación
# NOTA: Añadimos -cp app.jar explícitamente para asegurar que el JAR esté en el classpath
ENTRYPOINT ["java", "-cp", "app.jar", "com.teloapp.teloappbackend.TeloappBackendApplication", "--spring.profiles.active=production"]