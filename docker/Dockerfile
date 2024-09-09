FROM openjdk:17-jdk-slim

WORKDIR /employee-service

COPY build/libs/employee-service-0.0.1-SNAPSHOT.jar employee-service.jar

ENTRYPOINT ["java", "-jar", "/employee-service/employee-service.jar"]
