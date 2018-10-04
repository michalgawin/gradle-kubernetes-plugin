package pl.kubernetes.client.tasks.deployment

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.AppsV1Api
import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1Status
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class DeleteDeploymentTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(getRequestFile())
        ExtensionsV1beta1Deployment deployment = (ExtensionsV1beta1Deployment) kubernetesResourceDescriptor.getObjectModel()

        initApiClient()
        AppsV1Api api = new AppsV1Api()
        
        try {
            int gracePeriodSeconds = 0
            boolean orphanDependents = null
            String propagationPolicy = 'Foreground'

            V1Status response = api.deleteNamespacedDeployment(deployment.metadata.name,
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
            logger.error("Delete of Deployment failed:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}
