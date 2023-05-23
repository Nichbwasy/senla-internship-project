FROM openjdk:17

RUN mkdir "/opt/authorization-microservice"

COPY run/target/authorization-run.jar opt/authorization-microservice/authorization-run.jar

ENTRYPOINT ["java", "-jar", "opt/authorization-microservice/authorization-run.jar"]