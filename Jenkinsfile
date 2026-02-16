pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    environment {
        DOCKER_IMAGE_NAME = 'fari59/sep01-project'
        DOCKER_IMAGE_TAG  = 'latest'
        DOCKER_CREDENTIALS_ID = 'docker-hub'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Fari-panah/SEP01_Project.git'
            }
        }

        stage('Build') {
            steps {
                //bat 'mvn clean package -DskipTests'
                bat 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn test -Dtest=model.* -Dsurefire.failIfNoSpecifiedTests=false'
            }
        }

        stage('Code Coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
                        bat """
                        "${tool 'SonarScanner'}\\bin\\sonar-scanner" ^
                        -Dsonar.projectKey=SEP01_Project ^
                        -Dsonar.projectName=SEP01_Project ^
                        -Dsonar.sources=src ^
                        -Dsonar.java.binaries=target/classes ^
                        -Dsonar.token=%SONAR_TOKEN%
                        """
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_IMAGE_NAME%:%DOCKER_IMAGE_TAG% ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'docker-hub',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    bat """
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    docker push %DOCKER_IMAGE_NAME%:%DOCKER_IMAGE_TAG%
                    """
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true,
                  testResults: '**/target/surefire-reports/*.xml'

            jacoco execPattern: '**/target/jacoco.exec'
        }
    }
}