FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY wallet/ wallet/
COPY env.properties env.properties
ENTRYPOINT ["java","-jar","/app.jar"]