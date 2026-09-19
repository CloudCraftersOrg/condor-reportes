pipeline {
    agent any

    environment {
        AWS_DEFAULT_REGION = 'us-east-1'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B package'
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
                    BUCKET=condor-reportes-artifacts-${ACCOUNT_ID}
                    KEY=${BUILD_TAG}.zip

                    rm -rf bundle bundle.zip
                    mkdir -p bundle/deploy bundle/scripts
                    cp target/condor-reportes.jar bundle/condor-reportes.jar
                    cp deploy/condor-reportes.service bundle/deploy/
                    cp scripts/start.sh scripts/stop.sh bundle/scripts/
                    cp appspec.yml bundle/
                    (cd bundle && zip -r ../bundle.zip .)

                    aws s3 cp bundle.zip s3://${BUCKET}/${KEY}
                    aws deploy create-deployment \
                        --application-name condor-reportes \
                        --deployment-group-name condor-reportes-prod \
                        --s3-location bucket=${BUCKET},key=${KEY},bundleType=zip
                '''
            }
        }
    }
}
