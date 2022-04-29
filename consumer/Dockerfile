FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/consumer-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
ADD ${JAR_FILE} consumer.jar
ENTRYPOINT ["java","-jar","/opt/app/consumer.jar"]
