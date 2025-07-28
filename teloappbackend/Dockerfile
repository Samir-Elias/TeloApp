# Etapa 1: Build de la aplicación
FROM maven:3.9.4-openjdk-17-slim AS builder

# Instalar wget para descargar dependencias si es necesario
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Descargar dependencias (esto se cachea si no cambia pom.xml)
RUN mvn dependency:go-offline -B

# Compilar la aplicación
RUN mvn clean package -DskipTests -B

# Etapa 2: Imagen de ejecución
FROM maven:3-openjdk-17-slim AS builder
# Crear usuario no root para seguridad
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR construido desde la etapa anterior
COPY --from=builder /app/target/teloappbackend-0.0.1-SNAPSHOT.jar app.jar

# Cambiar propietario del archivo
RUN chown spring:spring app.jar

# Cambiar a usuario no root
USER spring:spring

# Exponer puerto
EXPOSE 8080

# Variables de entorno por defecto
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=production

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Punto de entrada
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]