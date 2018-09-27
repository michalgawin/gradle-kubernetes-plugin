# gradle-kubernetes-plugin

Gradle plugin for kubernetes. 

## Example of build.gradle

    import pl.kubernetes.client.CreateDeploymentTask
    apply plugin: KubernetesPlugin

    buildscript {
        repositories {
            mavenLocal()
            mavenCentral()
        }
        dependencies {
            classpath group: 'pl.mlgn', name: 'gradle-kubernetes-plugin', version: '1.0'
        }
    }

    kubernetes {
        address = 'http://localhost:8080'
        namespace = 'default'
        apiKey = 'AbCdEfGh01234'
		authentication = 'BearerToken'
    }

    task createDeploymentTask(type: CreateDeploymentTask) {
        requestFile = file('deployment.yaml')
		responseFile = file("${buildDir}/deployment_response.yaml")
    }

## Documentation


|Task|Description|Kind of configuration file|
|----------------|-------------------------------|-----------------|
|CreateDeploymentTask|create Deployment based on configuration file|`Deployment`|
|DeleteDeploymentTask|delete Deployment,ReplicaSet and PODs based on configuration file|`Deployment`|
|CreateServiceTask|create Service based on configuration file|`Service`|
|DeleteServiceTask|delete Service based on configuration file|`Service`|
|DeleteReplicaSetTask|delete ReplicaSet and PODs based on configuration file|`Deployment`|
|PodListTask|print all PODs|---|

