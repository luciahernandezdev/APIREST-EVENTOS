# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# direccion del archivo JAR generado con el boton de INSTALL de maven target/nombreAppPom+versionPom
ARG JAR_FILE=target/app-0.0.1-SNAPSHOT.jar

# Esta línea copia el archivo JAR de la ubicación y crea una nuevo llamado app.jar
COPY ${JAR_FILE} app.jar

#Exponer el puerto 8080
EXPOSE 8080

# Definir el comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]