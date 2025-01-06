# Use a Java 17 base image
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory
WORKDIR /app

# Copy the JAR file to the container
COPY target/*.jar app.jar

# Expose the port (default Spring Boot port)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
