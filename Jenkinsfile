pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/Roge55555/socialnetwork.git'
                bat 'mvn clean compile'
            }
        }
    }
}