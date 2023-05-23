FROM openjdk:17

ARG JAR_FILE=run/target/*.jar

RUN mkdir "/opt/rental-microservice"

COPY ${JAR_FILE} opt/rental-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/rental-microservice/app.jar"]