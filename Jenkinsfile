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
          sh "chmod +x gradlew"
          sh "./gradlew clean build --info"
        }
      }

      stage("Docker BuildKit") {
        steps {
            sh "DOCKER_BUILDKIT=1 docker build ."
        }
      }
   
      stage("Docker Build") {
        steps {
            script {
              dockerapp = docker.build("jaksonsneider/spring:${env.BUILD_ID}", "-f ./Dockerfile .")
            }
        }
      }

      stage("Docker push Image") {
        steps {
          script {
            docker.withRegistry("https://registry.hub.docker.com", "dockerhuba") {
              dockerapp.push("latest")
              dockerapp.push("${env.BUILD_ID}")
            }
          }
        }
      }

      stage("Deploy Kubernetes") {
        agent {
          kubernetes {
            cloud "kubernetes"
          }
        }

        environment {
          tag_version = "${env.BUILD_ID}"
        }

        steps {
          sh 'sed -i "s/{{tag}}/$tag_version/g" ./k8s/deploy.yaml'
          sh 'cat ./k8s/deploy.yaml'
          kubernetesDeploy(configs: "**/k8s/**", kubeconfigId: "kubeconfig")
        }
      }
  }
}