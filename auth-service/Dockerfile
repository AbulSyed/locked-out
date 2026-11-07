FROM amazoncorretto:17-alpine

WORKDIR /app

COPY build/libs/*.jar auth-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "auth-service.jar"]
