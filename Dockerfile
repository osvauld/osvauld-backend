FROM openjdk:11.0.8-jre-buster

EXPOSE 8000

# ARG JAR_FILE=build/libs/secrets-manager-backend-0.0.1-SNAPSHOT.jar
# COPY ${JAR_FILE} secrets-manager-backend-application.jar

# ENTRYPOINT [ "sh", "-c", "java -Dserver.port=8000 -Dlogging.level.root=INFO -Djava.security.egd=file:/dev/urandom -jar /home/ubuntu/backend/secrets-manager-backend-backend-application.jar -spring.config.location=config/application.properties" ]
# EXPOSE 8080
ARG JAR_FILE=build/libs/secrets-manager-backend-0.0.1-SNAPSHOT.war
COPY ${JAR_FILE} .
CMD [ "java", "-jar",  "/secrets-manager-backend-0.0.1-SNAPSHOT.war"]