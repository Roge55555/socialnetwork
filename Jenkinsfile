pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                git url: 'https://github.com/Roge55555/socialnetwork.git'
                bat 'mvn clean compile'
            }
        }
    }
}