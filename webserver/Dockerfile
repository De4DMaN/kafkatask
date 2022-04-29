FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
ARG JAR_FILE=target/webserver-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
ADD ${JAR_FILE} webserver.jar
ENTRYPOINT ["java","-jar","/opt/app/webserver.jar"]
