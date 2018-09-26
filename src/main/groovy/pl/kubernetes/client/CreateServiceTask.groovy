package pl.kubernetes.client

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1Service


class CreateServiceTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesFileDescriptor kubernetesFileDescriptor = new KubernetesFileDescriptor(getConf())
        V1Service body = (V1Service) kubernetesFileDescriptor.mapFileToKubernetesObject()

        initApiClient()
        CoreV1Api api = new CoreV1Api()
        
        try {
            V1Service response = api.createNamespacedService(getNamespace(), body, 'pretty_example')
            logger.info("Response: ${response.toString()}")
        } catch (ApiException e) {
            logger.error("Exception when calling CoreV1Api#createNamespacedService:\n${e.getResponseBody()}");
            e.printStackTrace();
        }
    }

}
