def runCommand(command) {
    if (isUnix()) {
        // macOS and Linux
        sh command
    } else {
        // Windows
        bat command
    }
}

pipeline {
    agent any
    environment {
        DOCKER_IMAGE_NAME = 'fari59/sep01-project'
        DOCKER_CREDENTIALS_ID = 'docker_hub'
        DOCKER_IMAGE_TAG = 'latest'
        DOCKERHUB_REPO = 'fari59/sep01-project'
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin:${env.PATH}"
    }
    tools {
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {

               git branch: 'main', url: 'https://github.com/Fari-panah/SEP01_Project.git'
            }
        }

    stage('Build & Package') {
                    steps {
                        script {
                            if (isUnix()) {
                                sh 'mvn clean package -DskipTests'
                            } else {
                                bat 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }

        stage('Build & Test') {
            steps {
                script {
                    runCommand('mvn clean install -DskipTests')
                }
            }
        }

        stage('Unit Tests') {
            steps {
                script {
                    runCommand('mvn test -Dgroups=!UI')
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('SonarQubeServer') {
                        withCredentials([string(credentialsId: 'sonar-token-jenkins', variable: 'SONAR_TOKEN')]) {
                            if (isUnix()) {
                                sh """
                                    ${tool 'SonarScanner'}/bin/sonar-scanner \
                                    -Dsonar.projectKey=sep01-project \
                                    -Dsonar.projectName=SEP01-Project \
                                    -Dsonar.sources=src \
                                    -Dsonar.java.binaries=target/classes \
                                    -Dsonar.token=$SONAR_TOKEN
                                """
                            } else {
                                bat """
                                    ${tool 'SonarScanner'}\\bin\\sonar-scanner ^
                                    -Dsonar.projectKey=sep01-project ^
                                    -Dsonar.projectName=SEP01-Project ^
                                    -Dsonar.sources=src ^
                                    -Dsonar.java.binaries=target/classes ^
                                    -Dsonar.token=%SONAR_TOKEN%
                                """
                            }
                        }
                    }
                }
            }
        }



        stage('Code Coverage') {
            steps {
                script {
                    runCommand('mvn jacoco:report')
                }
            }
        }

        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {
            steps {
                jacoco execPattern: 'target/jacoco.exec'

            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def imageTag = "${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                    if (isUnix()) {
                        sh "docker build -t ${imageTag} ."
                    } else {
                        bat "docker build -t ${imageTag} ."
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                            if (isUnix()) {
                                sh '''
                                   echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                                   docker push $DOCKER_IMAGE_NAME:$DOCKER_IMAGE_TAG
                                '''
                                } else {
                                  bat """
                                  echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                                  docker push %DOCKER_IMAGE_NAME%:%DOCKER_IMAGE_TAG%
                                """
                                }
                            }
                }
            }
        }

    }

    post {
        always {
            junit(testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true)
            jacoco execPattern: '**/target/jacoco.exec'
        }
    }
}