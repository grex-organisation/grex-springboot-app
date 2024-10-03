# Start with an OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar to the container
COPY target/grex-springboot-app-*-SNAPSHOT.jar grex-springboot-app.jar

# to override pass
ENV SPRING_PROFILES_ACTIVE=live

# Command to run the application
ENTRYPOINT ["java", "-jar", "grex-springboot-app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
