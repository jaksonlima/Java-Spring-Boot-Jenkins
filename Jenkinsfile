pipeline {
  agent any

  stages {
      stage("Get Source") {
        steps{
            git url: "https://github.com/jaksonlima/Java-Spring-Boot-Jenkins.git", branch: "main", credentialsId: "github"
        }
      }

      stage("Build Gradle") {
        steps {
          script {
            "./gradlew clean build"
          }
        }
      }

      stage("Docker Build") {
        steps{
            script {
              dockerapp = docker.build("jaksonsneider/spring:${env.BUILD_ID}", "-f ./Dockerfile .")
            }
        }
      }

      stage("Docker push Image") {
        steps {
          script {
            docker.withDockerRegistry("https://registry.hub.docker.com", "dockerhuba")
            dockerapp.push("push")
            dockerapp.push("${env.BUILD_ID}")
          }
        }
      }
  }
}