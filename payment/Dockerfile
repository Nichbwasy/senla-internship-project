FROM openjdk:17

ARG JAR_FILE=run/target/*.jar

RUN mkdir "/opt/payment-microservice"

COPY ${JAR_FILE} opt/payment-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/payment-microservice/app.jar"]