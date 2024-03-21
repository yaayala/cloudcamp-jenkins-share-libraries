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
        def appVersion = "1.0.0-beta.3"

        //Call checkout stage method
        checkoutFromRepo(branch: "${branchName}", repoURL: "${repoURL}")
        //Call build stage method
        dockerBuild(appName: appName, dockerFilepath: dockerFilepath, context: dockerContext)
        //Call push stage method
        dockerPush(region: "${awsRegion}", registryURL: "${registryURL}", appName: "${appName}", appVersion: "${appVersion}")



    } 
}

