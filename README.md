This service is a delivery managememnt service which implements an endpoint that will compute the cost and classification of a parcel given the length, width, weight, height. This api can also apply a valid discount code on the cost.


Application Specifics / Features
- default port is on 8080 , can override this via application.yml or via environment variable
- graceful shutdown default to 1minute
- x-correlation-id logging for the correlation id , to be supplied by the clients calling this api
- baseCharge for each of the classification of the parcel can be adjusted at deployment time. This will provide flexibility considering fluctiatoin of the market price / base charge will be subject to change. NOTE: This toggling is via an environment variable to consider that baseCharges should be validated by the stakeholders
and will go through the change management process. 
- openapi documentation
    - can access via endpoint 
          - ```http://<host>:<port>/api/swagger-ui/index.html?configUrl=/api/v3/api-docs/swagger-config#/```
          - ```http://<host>:<port>/api/swagger-ui/index.html?configUrl=/api/v3/api-docs/swagger-config#/parcel-controller/calculateParcel```
- readiness / liveness probe via springs actuator
    - can access via endpoint 
          - ```http://<host>:<port>/api/actuator/health```     
     

# Build locally
Pre-requisites:
- gradle v6.8.2
- gradle wrapper v6.3
- openjdk 11 / java 11
1) Perform a gradle build via a local command
-```./gradlew build``` or ```gradle build```
2) Perform a unit tests
-```./gradlew test``` or ```gradle test```
3) Perform a jacoco Test report for code coverage
-```./gradlew jacocoTestReport``` or ```gradle jacocoTestReport```
4) Perform an owasp check for the dependencies
-```./gradlew dependencyCheckAnalyze``` or ```gradle dependencyCheckAnalyze```
5) Perform an integratino test
-```./gradlew integrationTest``` or ```gradle integrationTest```
6) Docker build
-```docker build -t <artifactory_url>:<tag> --build-arg JAR_FILE=<jar file location> .``` e.g. ```docker build -t com.msi.contact:0.0.l --build-arg JAR_FILE=build/libs/contact-0.0.1.jar .```

# Run locally
- gradle
- docker
- minikube / docker for desktop w/ single node kubernetes enabled

## gradle bootRun
Run locally on gradle via plugin using the command
-```./gradlew bootRun```  or ```gradle bootRun```

## docker run local
Pre-requisite:
- docker build via invoking this command ``docker build -t <artifactory_url>:<tag> --build-arg JAR_FILE=<jar file location> .``` e.g. ```docker build -t com.mynt.delivery-mgmt-service:1.0.0 --build-arg JAR_FILE=build/libs/com.mynt.delivery-mgmt-service:1.0.0.jar .```
1) Run local docker on local
- ```docker run ---name <docker_name> -p <localport>:<containerport> -d <docker image>``` e.g. ```docker run ---name contact -p 8080:8080 -d com.msi.contact:0.0.l```

## deploy artifact to minikube / docker for desktop kubernetes cluster
Pre-requesites:
- helm
- ansible
- kubectl binaries
### Deploy using helm
1) check first the generated template via
- ```helm template helm/contact```
2) deploy helm chart via 
- ```helm install <name_of_the_deployment> <directory_path for the helm_chart>``` e.g ```helm install contact helm/deliver-mgmt-svc```
- uninstall a helm chart ```helm uninstall <name_of_the_deployment>```
3) verify via the ff.
- check if the version is installed via ```helm list```
- check if the pod is up and running ```kubectl get po``` or ```kubectl describe po <pod_name>```
- check into the pod logs via ```kubectl logs -f <pod_name>```
### Deploy using the local anisble playbook
1) deploy using the local ansible playbook from build to deployment on local kubernetes
 - ```ansible-playbook local-playbook.yaml```
 - can pass specific variables
    - ```ansible-playbook local-playbook.yaml --extra-vars "artifact_version_=build/libs/delivery-mgmt-service:1.0.0.jar"```
    - ```ansible-playbook local-playbook.yaml --extra-vars "helm_deployment_name_=delivery-mgmt-svc"```
    - ```ansible-playbook local-playbook.yaml --extra-vars "docker_image__=com.mynt.com.mynt.delivery-mgmt-service:1.0.0"```
 - can specifiy a specific phase
    - ```ansible-playbook local-playbook.yaml --tags 'build'```
    - ```ansible-playbook local-playbook.yaml --tags 'test'```
    - ```ansible-playbook local-playbook.yaml --tags 'deploy'```
 
 