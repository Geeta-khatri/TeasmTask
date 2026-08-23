pipeline {
    agent any

    environment {
        JWT_EXPIRATION = credentials('JWT_EXPIRATION')
        DB_USERNAME = credentials('DB_USERNAME')
        DB_PASSWORD = credentials('DB_PASSWORD')
        JWT_REF_EXPIRATION = credentials('JWT_REF_EXPIRATION')
        JWT_SECRET = credentials('JWT_SECRET')
        
    }
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

        stage('Docker Check') {
            steps {
                echo "Docker version check"
                 bat 'echo %PATH%'
                 bat 'where docker'
                bat 'docker --version'
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker image"
                bat 'docker build -t myapp .'
            }
        }

        stage('Docker Deploy') {
            steps {
                echo "Docker compose"
                bat 'docker compose up -d'
            }
        }
    }
}
