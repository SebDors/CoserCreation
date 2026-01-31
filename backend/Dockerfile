# =========================================
# Stage 1: Build the Java Application
# =========================================
ARG MAVEN_VERSION=3.9-eclipse-temurin-21
ARG JDK_VERSION=21-jre-alpine

# Use a Maven image with JDK 21 for building (customizable via ARG)
FROM maven:${MAVEN_VERSION} AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and the pom.xml to leverage Docker's caching for dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies using the Maven wrapper
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the application source code into the container
COPY src ./src

# Build the application, skipping tests to speed up the build process
# The resulting JAR will be in /app/target/
RUN ./mvnw package -DskipTests

# =========================================
# Stage 2: Prepare Java Runtime to Serve the Application
# =========================================

FROM eclipse-temurin:${JDK_VERSION} AS runner

WORKDIR /app

# Create a dedicated non-root user and group for security
# RUN addgroup -S spring && adduser -S spring -G spring

# Copy the executable JAR from the build stage
# COPY --from=builder --chown=spring:spring /app/target/CoserCreation-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder /app/target/CoserCreation-0.0.1-SNAPSHOT.jar app.jar

# Switch to the non-root user
# USER spring

# Expose port 8080 to allow HTTP traffic (default for Spring Boot)
EXPOSE 8080

# Start the Java application
ENTRYPOINT ["java", "-jar", "app.jar"]
