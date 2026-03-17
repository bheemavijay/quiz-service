# Use lightweight Java 17 image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/*.jar app.jar

# Expose port (change if needed)
EXPOSE 8084

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]