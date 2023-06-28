FROM openjdk:17

ARG JAR_FILE=run/target/*.jar

RUN mkdir "/opt/api-gateway-microservice"

COPY ${JAR_FILE} opt/api-gateway-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/api-gateway-microservice/app.jar"]