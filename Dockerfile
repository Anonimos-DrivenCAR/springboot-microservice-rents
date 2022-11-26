FROM openjdk:18.0.2.1-jdk

WORKDIR /app

COPY ./target/springboot-microservice-rents-0.0.1-SNAPSHOT.jar .

EXPOSE 8005

ENTRYPOINT ["java", "-jar","springboot-microservice-rents-0.0.1-SNAPSHOT.jar"]