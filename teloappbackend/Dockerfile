# --------------- STAGE 1: Build la aplicación Spring Boot ---------------
FROM openjdk:17-jdk-slim as builder

# Instalar Maven
# Algunas dependencias para wget y descompresión
RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/*

# Descargar y configurar Maven
ARG MAVEN_VERSION=3.9.8 # Puedes usar la última versión estable de Maven
ARG MASH_URL=https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip

RUN wget ${MASH_URL} -O /tmp/maven.zip && \
    unzip /tmp/maven.zip -d /opt/ && \
    rm /tmp/maven.zip

ENV M2_HOME /opt/apache-maven-${MAVEN_VERSION}
ENV PATH ${M2_HOME}/bin:${PATH}


# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos del proyecto Maven (pom.xml y src)
COPY pom.xml .
COPY src ./src

# Ejecuta el build de Maven. -DskipTests para omitir las pruebas.
RUN mvn clean install -DskipTests

# --------------- STAGE 2: Crea la imagen final ligera ---------------
FROM openjdk:17-jdk-slim

# Crea un volumen para los logs (opcional, pero buena práctica)
VOLUME /tmp

# Copia el JAR compilado desde la etapa 'builder'
ARG JAR_FILE=target/teloappbackend-0.0.1-SNAPSHOT.jar # Debería ser teloappbackend ahora
COPY --from=builder /app/${JAR_FILE} app.jar

# Define el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-cp", "app.jar", "com.teloapp.teloappbackend.TeloappBackendApplication", "--spring.profiles.active=production"]