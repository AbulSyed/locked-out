# Build stage
FROM gradle:7.2.0-jdk17 AS build
WORKDIR /app

# Copy Gradle related files
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Download Gradle dependencies
RUN ./gradlew --no-daemon --version

COPY . .
RUN ./gradlew --no-daemon clean build -x test

# Application stage
FROM gradle:7.2.0-jdk17
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar ./auth-service.jar

# Run the application
CMD ["java", "-jar", "auth-service.jar"]