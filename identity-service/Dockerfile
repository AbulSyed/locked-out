FROM amazoncorretto:17-alpine

RUN apk add --no-cache curl

WORKDIR /app

COPY build/libs/*.jar identity-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "identity-service.jar"]
