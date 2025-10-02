FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy file JAR đã build sẵn
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
