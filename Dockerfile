FROM openjdk:11.0.8-jre-buster

EXPOSE 8000

ARG JAR_FILE=build/libs/secrets-manager-backend-0.0.1-SNAPSHOT.war
COPY ${JAR_FILE} .
CMD [ "java", "-jar",  "/secrets-manager-backend-0.0.1-SNAPSHOT.war"]