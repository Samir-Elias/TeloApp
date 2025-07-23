# --------------- STAGE 1: Build la aplicación Spring Boot ---------------
FROM openjdk:17-jdk-slim as builder

# Instalar herramientas necesarias para descargar y descomprimir Maven
RUN apt-get update && \
    apt-get install -y wget unzip && \
    rm -rf /var/lib/apt/lists/*

# Descargar y configurar Maven
# Asegúrate de que esta sea la versión y la URL exactas.
# Verifica en https://maven.apache.org/download.cgi si hay una versión más reciente o un enlace diferente.
ARG MAVEN_VERSION=3.9.11
ARG MASH_URL=https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.zip

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


# Copia el JAR compilado desde la etapa 'builder'
# Asegúrate de que el nombre del JAR coincida con el artifactId en tu pom.xml
ARG JAR_FILE=target/teloappbackend-0.0.1-SNAPSHOT.jar 
COPY --from=builder /app/${JAR_FILE} app.jar

# Define el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-cp", "app.jar", "com.teloapp.teloappbackend.TeloappBackendApplication", "--spring.profiles.active=production"]