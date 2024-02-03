pipeline {
    agent any 
    tools {
        maven 'Maven'
    }
    stages {
        stage('Build Maven') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/shashi999Kant/blog_app_api']])
                bat 'mvn clean install'
            }
        }
        stage('Build docker image') {
            steps {
                script {
                    bat 'docker build -t shashi9721/blogging-app-backend .'
                }
            }
        }
        stage('Push docker image to docker hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                        bat "docker login -u shashi9721 -p ${dockerhubpwd}"
                        bat 'docker push shashi9721/blogging-app-backend'
                    }
                }
            }
        }
    }
}
