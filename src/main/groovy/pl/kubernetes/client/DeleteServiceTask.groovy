package pl.kubernetes.client

import com.google.gson.JsonSyntaxException

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1Service
import io.kubernetes.client.models.V1Status

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
        } catch (JsonSyntaxException e) {
            logger.error("Known issue: https://github.com/kubernetes-client/java/issues/86")
        } catch (ApiException e) {
            logger.error("Exception when calling AppsV1beta1Api#deleteNamespacedService:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}
