def call(Map parameters){
        
    stage ('Push') {
        sh "aws ecr get-login-password --region ${parameters.region} | docker login --username AWS --password-stdin ${parameters.registryURL}"
        sh "docker tag ${parameters.appName}:latest ${parameters.registryURL}/${parameters.appName}:${parameters.appVersion}"
        sh "docker tag ${parameters.appName}:latest ${parameters.registryURL}/${parameters.appName}:latest"
        sh "docker push --all-tags ${parameters.registryURL}/${parameters.appName}"
        
    }

}
