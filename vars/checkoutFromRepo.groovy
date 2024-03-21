def call(Map parameters){

    stage('Checkout') {
        checkout scmGit(
                        branches: 
                        [[name: parameters.branch]], 
                        extensions: [], 
                        // userRemoteConfigs: [[url: repoURL, credentialsId: credentialsID]]
                        userRemoteConfigs: [[url: parameters.repoURL]]
                        ) 
    }

}
