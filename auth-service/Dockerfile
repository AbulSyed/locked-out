FROM amazoncorretto:17-alpine

RUN apk add --no-cache curl

WORKDIR /app

COPY build/libs/*.jar auth-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "auth-service.jar"]
