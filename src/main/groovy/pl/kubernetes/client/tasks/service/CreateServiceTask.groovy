package pl.kubernetes.client.tasks.service

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1Service
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class CreateServiceTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(getRequestFile())
        V1Service body = (V1Service) kubernetesResourceDescriptor.getObjectModel()

        initApiClient()
        CoreV1Api api = new CoreV1Api()
        
        try {
            V1Service response = api.createNamespacedService(getNamespace(), body, 'pretty_example')
            if (responseFile) {
                responseFile.text = response.toString()
            }
            logger.info("Response: ${response.toString()}")
        } catch (ApiException e) {
            logger.error("Create of Service failed:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}