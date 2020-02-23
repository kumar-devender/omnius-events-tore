FROM openjdk:8-jdk-alpine
LABEL maintainer="Devender Kumar"
ARG JAR_FILE
ADD target/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]