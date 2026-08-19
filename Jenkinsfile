pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo "Entered checkout stage "
                checkout scm
            }
        }

        stage('Build') {
            steps {
                 echo "Entered Build stage "
                bat './mvnw clean package -DskipTests'
            }
        }
    }
}
