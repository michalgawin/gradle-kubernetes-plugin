package pl.kubernetes.client.tasks.service

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1Service
import io.kubernetes.client.models.V1Status
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class DeleteServiceTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(getRequestFile())
        V1Service service = (V1Service) kubernetesResourceDescriptor.getObjectModel()

        initApiClient()
        CoreV1Api api = new CoreV1Api()
        
        try {
            int gracePeriodSeconds = 0
            boolean orphanDependents = null
            String propagationPolicy = 'Foreground'

            V1Status response = api.deleteNamespacedService(service.metadata.name,
                getNamespace(),
                new V1DeleteOptions(),
                null,
                gracePeriodSeconds,
                orphanDependents,
                propagationPolicy
            )
            if (responseFile) {
                responseFile.text = response.toString()
            }
            logger.info("Response: ${response.toString()}")
        } catch (ApiException e) {
            logger.error("Delete of Service failed:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}
