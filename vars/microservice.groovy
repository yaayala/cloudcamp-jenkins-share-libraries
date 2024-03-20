#!/usr/bin/env groovy
def call() {

node ('docker&&linux'){

    // Docker Build and Push Stages variables
    def appName = "hello-world-python"
    // Docker Build Stage variables
    def dockerFilepath = "_scm_docker/Dockerfile"
    def dockerContext = "_scm_docker/"
    // Repo clone checkout Stage variables
    def branchName = "bootcamp"
    def repoURL = "https://github.com/yaayala/cloudcamp-jenkins-lab"
    // Docker Push Stage variables
    def awsRegion = "us-east-1"
    def registryURL = "851725481871.dkr.ecr.us-east-1.amazonaws.com"
    def appVersion = "1.0.0-beta.2"

    //Call checkout stage method
    checkoutFromRepo("${branchName}", "${repoURL}")
    //Call build stage method
    dockerBuild(appName, dockerFilepath, dockerContext)
    //Call push stage method
    dockerPush("${awsRegion}", "${registryURL}", "${appName}", "${appVersion}")



} 

// Methods
// def checkoutFromRepo(branch, repoURL, credentialsID){
def checkoutFromRepo(branch, repoURL){

    stage('Checkout') {
        checkout scmGit(
                        branches: 
                        [[name: branch]], 
                        extensions: [], 
                        // userRemoteConfigs: [[url: repoURL, credentialsId: credentialsID]]
                        userRemoteConfigs: [[url: repoURL]]
                        ) 
    }

}

def dockerBuild(appName, dockerFilepath="Dockerfile", context="."){


    stage ('Build') {
        sh "docker build -t ${appName} -f ${dockerFilepath} ${context}"
    }

}

def dockerPush(region, registryURL, appName, appVersion){
    

    stage ('Push') {
        sh "aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${registryURL}"
        sh "docker tag ${appName}:latest ${registryURL}/${appName}:${appVersion}"
        sh "docker tag ${appName}:latest ${registryURL}/${appName}:latest"
        sh "docker push --all-tags ${registryURL}/${appName}"
        
    }

}

}

