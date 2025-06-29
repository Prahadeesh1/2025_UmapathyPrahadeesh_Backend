# Use official OpenJDK runtime as the base image
# Using JDK 11 to match your Maven configuration
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Create a non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copy the built JAR file from your local machine to the container
# The JAR file should be built using: mvn clean package
COPY target/coin-change-api-1.0-SNAPSHOT.jar app.jar

# Copy the configuration file
COPY config.yml config.yml

# Change ownership of the files to the non-root user
RUN chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Expose the ports that your application uses
# Port 8080 for the main application
# Port 8081 for admin/health checks
EXPOSE 8080 8081

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8081/healthcheck || exit 1

# Run the application
CMD ["java", "-jar", "app.jar", "server", "config.yml"]