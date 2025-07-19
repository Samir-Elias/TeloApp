# Etapa 1: Construir la aplicación
# Usa una imagen base de Maven con OpenJDK 17
FROM maven:3.8-openjdk-17 AS build

# Establece el directorio de trabajo dentro del contenedor
# Aquí es donde se copiarán los archivos del backend
WORKDIR /app

# Copia los archivos necesarios para construir el proyecto
# El contexto de build de Render (Root Directory) ya es 'teloapp-backend'
# Por lo tanto, 'pom.xml' y 'src' están en la raíz del contexto.
COPY pom.xml .
COPY src ./src

# Asegúrate de copiar el wrapper de Maven si estás usando './mvnw'
# (Según tu captura, sí tienes mvnw y mvnw.cmd, así que son necesarios)
COPY .mvn .mvn/
COPY mvnw .
COPY mvnw.cmd .

# Ejecuta la construcción con Maven Wrapper
# ... otras líneas de COPY ...

# Asegúrate de que el script del Maven Wrapper sea ejecutable
RUN chmod +x mvnw
 
# Ejecuta la construcción con Maven Wrapper
RUN ./mvnw clean package -DskipTests

# ... el resto de tu Dockerfile ...
# Etapa 2: Crear la imagen final ligera
# Usa una imagen JRE (solo entorno de ejecución) para reducir el tamaño
FROM eclipse-temurin:17-jre-alpine

# Establece el directorio de trabajo en la imagen final
WORKDIR /app

# Copia el JAR generado de la etapa de construcción 'build'
# Asegúrate de que el nombre del JAR coincide exactamente con el que genera Maven
# (teloapp-backend-0.0.1-SNAPSHOT.jar es el nombre predeterminado)
COPY --from=build /app/target/teloapp-backend-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que tu aplicación Spring Boot usa (generalmente 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]