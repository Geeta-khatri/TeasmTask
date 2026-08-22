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
                bat "mvnw.cmd clean package -DskipTests -DfinalName=${env.JOB_BASE_NAME}_${env.BUILD_NUMBER}"

            }
        }

        stage('Archive JAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
