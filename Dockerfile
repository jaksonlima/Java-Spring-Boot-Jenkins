FROM openjdk:11-jdk
VOLUME /tmp
COPY build/libs/jenkins-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]