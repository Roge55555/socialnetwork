pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/Roge55555/socialnetwork/tree/travis/coverals'
                bat 'mvn clean compile'
            }
        }
    }
}