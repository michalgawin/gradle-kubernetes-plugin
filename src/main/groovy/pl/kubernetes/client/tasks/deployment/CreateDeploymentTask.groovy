package pl.kubernetes.client.tasks.deployment

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.ExtensionsV1beta1Api
import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class CreateDeploymentTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(getRequestFile())
        ExtensionsV1beta1Deployment body = (ExtensionsV1beta1Deployment) kubernetesResourceDescriptor.getObjectModel()

        initApiClient()
        ExtensionsV1beta1Api api = new ExtensionsV1beta1Api()
        
        try {
            ExtensionsV1beta1Deployment response = api.createNamespacedDeployment(getNamespace(), body, 'pretty_example')
            if (responseFile) {
                responseFile.text = response.toString()
            }
            logger.info("Response: ${response.toString()}")
        } catch (ApiException e) {
            logger.error("Create of Deployment failed:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}
