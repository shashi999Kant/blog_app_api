pipeline {
    agent any
    
    environment {
        // Define environment variables
        MAVEN_HOME = tool 'Maven'
        JAVA_HOME = tool 'Java'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout source code from your Git repository
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build the Spring Boot application using Maven
                sh "${MAVEN_HOME}/bin/mvn clean install"
            }
        }

        stage('Test') {
            steps {
                // Run tests if needed
                sh "${MAVEN_HOME}/bin/mvn test"
            }
        }

        stage('Deploy') {
            steps {
                // Deploy the application (e.g., package as JAR or WAR)
                // You may need to customize this based on your project structure
                sh "${MAVEN_HOME}/bin/mvn package"
            }
        }

      

    }

}
