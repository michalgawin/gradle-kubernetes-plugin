package pl.kubernetes.client

import java.io.File

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.ExtensionsV1beta1Api
import io.kubernetes.client.models.ExtensionsV1beta1Deployment


class CreateDeploymentTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesFileDescriptor kubernetesFileDescriptor = new KubernetesFileDescriptor(getConf())
        ExtensionsV1beta1Deployment body = (ExtensionsV1beta1Deployment) kubernetesFileDescriptor.mapFileToKubernetesObject()

        setClient()
        ExtensionsV1beta1Api api = new ExtensionsV1beta1Api()
        
        try {
            logger.debug(body.toString())
            ExtensionsV1beta1Deployment result = api.createNamespacedDeployment(getNamespace(), body, 'pretty_example')
            logger.info(result.toString())
        } catch (ApiException e) {
            logger.error("Exception when calling AppsV1beta1Api#createNamespacedDeployment:\n${e.getResponseBody()}");
            e.printStackTrace();
        }
    }

}
