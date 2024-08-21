pipeline{
    agent { label 'master' }
    environment {
        M2_HOME = tool 'M2_HOME'
        JAVA_HOME = tool 'JAVA_HOME'

        SONARQUBE_URL = "http://20.224.44.44:9000"
        SONARQUBE_PROJECT_NAME = "bank-api"
        SONARQUBE_CREDENTIALS_ID = "sonarqube-creds"
        SONARQUBE_PROJECT_KEY = "ebank-api"

        PROJECT_VERSION = '0.0.1'
        IMAGE_NAME_API = "bank-api"
        DOCKER_REGISTRY = "test"

        K8S_NAMESPACE_STAGING = "ebank-api-k8s-staging"
        K8S_NAMESPACE_PROD = "ebank-api-k8s-production"
        
    }
    stages{
        stage('Checkout'){
            steps{
                echo 'Checkout start...'
                checkout scm
                echo 'Successfully checkout'
            }
        }
        stage('Pre-integration Tests'){
            parallel {
                stage('Code Style Test') {
                    steps{
                        echo 'CI/CD: Code Style Tests'
                    }
                }
                stage('Unit Test') {
                    steps{
                        echo 'CI/CD: Unit Tests'

                        echo 'Unit Test of bank-api start...'
                        sh "${M2_HOME}/bin/mvn clean test"
                        echo 'Successfully Unit Test of bank-api'
                    }
                    post {
                        always {
                            echo 'Archive Unit Test of bank-api start...'
                            junit '**/target/surefire-reports/*.xml'
                            echo 'Successfully Reports Unit results tests for bank-api'
                        }
                    }
                }
                stage('Security Test') {
                    steps{
                        echo 'CI/CD: Security Tests'
                    }
                }
                stage('Code Coverage Test') {
                    steps{
                        echo 'CI/CD: Code Coverage Tests'

                        echo 'Code coverage of bank-api start...'
                        sh "${M2_HOME}/bin/mvn clean test jacoco:report"
                        echo 'Successfully Code coverage of bank-api'
                    }
                    post{

                        success{
                            
                            echo 'Report Code Coverage Start...'
                            publishHTML(target: [
                                reportName: 'JaCoCo Coverage Report',
                                reportDir: 'target/site/jacoco',
                                reportFiles: 'index.html',
                                keepAll: true,
                                alwaysLinkToLastBuild: true,
                                allowMissing: false
                            ])
                            echo 'Successfully Report Code Coverage'

                            echo 'Archive Code Coverage Reports start...'
                            archiveArtifacts artifacts: 'target/site/jacoco/*.xml', allowEmptyArchive: true
                            echo 'Successfully Archive Code Coverage Reports'

                        }
                    }
                }
            }
            
        }
        stage ('Sonarqueb Code Analysis'){
            steps{
                echo 'Running SonarQube analysis...'
                withCredentials([
                    string(credentialsId: 'sonarque-creds-1', variable: 'SONARQUBE_CREDENTIALS_TOKEN'),
                    string(credentialsId: 'sonarque-creds-2', variable: 'SONARQUBE_PROJECT_KEY')
                    // usernamePassword(credentialsId: 'sonarque-creds-1', usernameVariable: 'SONARQUBE_CREDENTIALS-1', passwordVariable: 'SONARQUBE_CREDENTIALS_TOKEN'),
                    // usernamePassword(credentialsId: 'sonarque-creds-2', usernameVariable: 'SONARQUBE_CREDENTIALS-2', passwordVariable: 'SONARQUBE_PROJECT_KEY')
                ]){
                    withSonarQubeEnv('SonarQube Server') {
                        sh "${M2_HOME}/bin/mvn clean verify -DskipTests sonar:sonar -Dsonar.projectName=${SONARQUBE_PROJECT_NAME} -Dsonar.projectKey=${SONARQUBE_PROJECT_KEY} -Dsonar.host.url=${SONARQUBE_URL} -Dsonar.token=${SONARQUBE_CREDENTIALS_TOKEN}"
                    }
                }
                echo 'Sonarqube Analysis Successfully...'
                
            }
        }
        stage('Sonarqube Quality Gate'){
            steps{
                echo 'CI/CD: Sonarqube Code Quality Gates Tests'
                script{
                    timeout(time: 3, unit: 'MINUTES') {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                    echo 'Quality Gate is OK'
                }
            }
        }
        stage('Build Image'){
            steps{
                echo 'CI/CD : Build Image Step'
                echo 'Starting Build container image for bank-api...'
                sh "docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME_API}:${PROJECT_VERSION} ."
                echo 'Successfully Build container image for bank-api...'
            }
        }
        stage('Push Image'){
            steps{
                echo 'CI/CD : Push Container Images To registry ...'
                withCredentials([usernamePassword(credentialsId: 'docker-registry-creds', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    sh "echo $DOCKER_PASSWORD | docker login ${DOCKER_REGISTRY} -u $DOCKER_USERNAME --password-stdin"
                    sh "docker push ${DOCKER_REGISTRY}/${IMAGE_NAME_API}:${PROJECT_VERSION}"
                    echo 'Successfully Push container image for bank-api'
                }
            }
        }
        stage('Deploy Release to Staging'){
            steps{
                echo 'Deploying to the staging environment...'
                withCredentials([file(credentialsId: KUBECONFIG_CREDENTIALS_ID, variable: 'KUBECONFIG')]) {
                    sh """
                    kubectl --namespace=${K8S_NAMESPACE_STAGING} set image deployment/${IMAGE_NAME_API} ${IMAGE_NAME_API}=${DOCKER_REGISTRY}/${IMAGE_NAME_API}:${PROJECT_VERSION}
                    
                    kubectl --namespace=${K8S_NAMESPACE_STAGING} rollout status deployment/${IMAGE_NAME_API}
                    """
                    echo 'Successfully Deploy to the staging environment...'
                }
            }
        }

        stage('Integration Tests') {
            steps {
                echo 'Running integration tests on staging...'
                // Add your integration test commands here
            }
        }

        stage('Deploy to Production') {
            when {
                branch 'master'
            }
            steps {
                echo 'Deploying to the production environment...'
                withCredentials([file(credentialsId: KUBECONFIG_CREDENTIALS_ID, variable: 'KUBECONFIG')]) {
                    sh """
                    kubectl --namespace=${K8S_NAMESPACE_PROD} set image deployment/${IMAGE_NAME_API} ${IMAGE_NAME_API}=${DOCKER_REGISTRY}/${IMAGE_NAME_API}:${PROJECT_VERSION}
                    
                    kubectl --namespace=${K8S_NAMESPACE_PROD} rollout status deployment/${IMAGE_NAME_API}
                    """
                    echo 'Successfully Deploying to the production environment...'
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            cleanWs()
        }
        success {
            
            echo 'Pipeline completed successfully!'
            
            // archiveArtifacts artifacts: "**/target/*.jar"
            // echo 'Archiving artifacts successfully !!!'
            
            emailext (
                to: 'test@example.com',
                subject: "Build Success - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """<p>Job '${env.JOB_NAME}' (#${env.BUILD_NUMBER}) was successful.</p>
                         <p>Check console output at <a href="${env.BUILD_URL}">${env.BUILD_URL}</a>.</p>"""
            )
            echo 'Email Send Successfully...'
        }
        failure {
            echo 'Pipeline failed. Notifying team...'
            emailext (
                to: 'test@example.com',
                subject: "Build Failure - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """<p>Job '${env.JOB_NAME}' (#${env.BUILD_NUMBER}) has failed.</p>
                         <p>Check console output at <a href="${env.BUILD_URL}">${env.BUILD_URL}</a>.</p>"""
            )
            echo 'Email Send successfully...'
            // Add notification logic here, e.g., email or Slack notification

        }
    }
}