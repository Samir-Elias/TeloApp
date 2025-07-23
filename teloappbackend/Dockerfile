# --------------- STAGE 1: Build la aplicación Spring Boot ---------------
FROM openjdk:17-jdk-slim as builder # cache-bust-v1.0.0 # <-- AÑADE ESTE COMENTARIO AL FINAL DE LA LÍNEA
                                    # Puedes cambiar 'cache-bust-v1.0.0' a algo como 'cache-bust-v2.0.0'
                                    # cada vez que necesites forzar una reconstrucción completa.

# Instalar herramientas necesarias para descargar y descomprimir Maven
RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/*

# ... (el resto de tu Dockerfile permanece EXACTAMENTE igual) ...

# Descargar y configurar Maven
ARG MAVEN_VERSION=3.9.11
ARG MASH_URL=https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip

RUN wget ${MASH_URL} -O /tmp/maven.zip && \
    unzip /tmp/maven.zip -d /opt/ && \
    rm /tmp/maven.zip

ENV M2_HOME /opt/apache-maven-${MAVEN_VERSION}
ENV PATH ${M2_HOME}/bin:${PATH}

# Define el directorio de trabajo dentro del contenedor para la compilación
WORKDIR /app

# Copia los archivos del proyecto Maven (pom.xml y src)
COPY pom.xml .
COPY src ./src

# Ejecuta el build de Maven. -DskipTests para omitir las pruebas.
RUN mvn clean install -DskipTests

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