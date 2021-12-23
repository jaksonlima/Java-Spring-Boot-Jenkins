# FROM  adoptopenjdk/openjdk11
# VOLUME /tmp
# ARG JAR_FILE
# COPY ${JAR_FILE} app.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","/app.jar"]

# syntax=docker/dockerfile:experimental
FROM adoptopenjdk/openjdk11 AS build

# RUN addgroup -S demo && adduser -S demo -G demo
# USER demo

WORKDIR /workspace/app
COPY . /workspace/app

EXPOSE 8080

RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

# ENTRYPOINT ["java","-jar","/app.jar"]