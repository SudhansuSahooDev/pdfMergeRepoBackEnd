# Use Maven and OpenJDK base image
FROM maven:3.8.4-openjdk-11-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml /app/

# Download the dependencies (caching dependencies)
RUN mvn dependency:go-offline

# Copy the source code
COPY src /app/src

# Build the project (compile and package)
RUN mvn clean package

# Use OpenJDK to run the project
FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/pdf-merge-backend-1.0-SNAPSHOT.jar /app/pdf-merge-backend.jar

# Run the application
CMD ["java", "-jar", "/app/pdf-merge-backend.jar"]
