package pl.kubernetes.client.tasks.ingress

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.ExtensionsV1beta1Api
import io.kubernetes.client.models.V1beta1Ingress
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class CreateIngressTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(getRequestFile())
        V1beta1Ingress body = (V1beta1Ingress) kubernetesResourceDescriptor.getObjectModel()

        initApiClient()
        ExtensionsV1beta1Api api = new ExtensionsV1beta1Api()
        
        try {
            V1beta1Ingress response = api.createNamespacedIngress(getNamespace(), body, 'pretty_example')
            if (responseFile) {
                responseFile.text = response.toString()
            }
            logger.info("Response: ${response.toString()}")
        } catch (ApiException e) {
            logger.error("Create of Ingress failed:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}