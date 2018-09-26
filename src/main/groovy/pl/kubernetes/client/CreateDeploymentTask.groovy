package pl.kubernetes.client

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.ExtensionsV1beta1Api
import io.kubernetes.client.models.ExtensionsV1beta1Deployment


class CreateDeploymentTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesFileDescriptor kubernetesFileDescriptor = new KubernetesFileDescriptor(getConf())
        ExtensionsV1beta1Deployment body = (ExtensionsV1beta1Deployment) kubernetesFileDescriptor.mapFileToKubernetesObject()

        initApiClient()
        ExtensionsV1beta1Api api = new ExtensionsV1beta1Api()
        
        try {
            ExtensionsV1beta1Deployment response = api.createNamespacedDeployment(getNamespace(), body, 'pretty_example')
            logger.info("Response: ${response.toString()}")
        } catch (ApiException e) {
            logger.error("Exception when calling AppsV1beta1Api#createNamespacedDeployment:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}
