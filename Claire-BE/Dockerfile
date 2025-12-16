FROM eclipse-temurin:21-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/claire-0.0.1-SNAPSHOT.jar claire.jar
ENTRYPOINT ["java", "-jar", "/claire.jar"]