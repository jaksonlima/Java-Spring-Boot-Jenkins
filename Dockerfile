FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE
RUN echo ${JAR_FILE} 
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]