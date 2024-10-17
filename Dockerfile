FROM openjdk:21-jdk

ARG JAR_FILE=target/rule-utils-service-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]