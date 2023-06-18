FROM openjdk:17

ARG JAR_FILE=running/target/*.jar

RUN mkdir "/opt/server-eureka-microservice"

COPY ${JAR_FILE} opt/server-eureka-microservice/app.jar

ENTRYPOINT ["java", "-jar", "/opt/server-eureka-microservice/app.jar"]