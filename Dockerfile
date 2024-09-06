# Start with an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar to the container
COPY target/grex-springboot-app-24.9.0-SNAPSHOT.jar app.jar

# Expose the port the Spring Boot app will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
