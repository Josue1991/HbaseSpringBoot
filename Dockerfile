# Usar una imagen de Java 17 (o la versión que uses)
FROM eclipse-temurin:17-jdk

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR de la aplicación al contenedor
COPY target/*.jar app.jar

# Exponer el puerto en el que corre Spring Boot
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
