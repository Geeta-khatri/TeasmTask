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
             environment {
        DOCKER_PATH= credentials('Dcoker_path')
    }
            steps {
                echo "Docker version check"
                
                bat 'set "PATH=%DOCKER_PATH%;%PATH%" && docker --version'
            }
        }

        stage('Docker Build') {
            environment {
        DOCKER_PATH= credentials('Dcoker_path')
    }
            steps {
                echo "Building Docker image"
                bat 'set "PATH=%DOCKER_PATH%;%PATH%" && docker build -t myapp .'
            }
        }

        stage('Docker Deploy') {
            environment {
        DOCKER_PATH= credentials('Dcoker_path')
    }
            steps {
                echo "Docker compose version"
                bat 'set "PATH=%DOCKER_PATH%;%PATH%" && docker-compose --version'
                bat 'set "PATH=%DOCKER_PATH%;%PATH%" && docker-compose up -d'
            }
        }
    }
}
