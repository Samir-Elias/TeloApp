# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Crea un volumen para los logs (opcional, pero buena práctica para despliegues)
VOLUME /tmp

# Copia el archivo JAR de tu aplicación (asegúrate de que el nombre del JAR coincida con el que genera tu build)
# Por ejemplo, si tu JAR se llama 'teloapp-backend-0.0.1-SNAPSHOT.jar'
ARG JAR_FILE=target/teloapp-backend-0.0.1-SNAPSHOT.jar 

# Añade tu JAR al contenedor
COPY ${JAR_FILE} app.jar

# Define el punto de entrada para ejecutar la aplicación
# La opción --spring.profiles.active=production es una buena práctica para entornos de despliegue
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=production"]