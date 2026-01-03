# Etapa de construcción
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copia los archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dar permisos de ejecución a mvnw
RUN chmod +x mvnw

# Descargar dependencias (se cachea si no cambia pom.xml)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar el JAR compilado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (Spring Boot usa 8080 por defecto)
EXPOSE 8080

# Variables de entorno opcionales
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]