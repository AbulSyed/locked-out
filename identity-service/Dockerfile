FROM amazoncorretto:17-alpine

WORKDIR /app

COPY build/libs/*.jar identity-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "identity-service.jar"]
