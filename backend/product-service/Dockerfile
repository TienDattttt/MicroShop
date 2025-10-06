
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy file JAR đã build sẵn
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
