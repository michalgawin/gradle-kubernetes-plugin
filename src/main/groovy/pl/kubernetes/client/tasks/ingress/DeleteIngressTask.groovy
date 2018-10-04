package pl.kubernetes.client.tasks.ingress

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.ExtensionsV1beta1Api
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1Status
import io.kubernetes.client.models.V1beta1Ingress
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class DeleteIngressTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(getRequestFile())
        V1beta1Ingress ingress = (V1beta1Ingress) kubernetesResourceDescriptor.getObjectModel()

        initApiClient()
        ExtensionsV1beta1Api api = new ExtensionsV1beta1Api()
        
        try {
            int gracePeriodSeconds = 0
            boolean orphanDependents = null
            String propagationPolicy = 'Foreground'

            V1Status response = api.deleteNamespacedIngress(ingress.metadata.name,
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
            logger.error("Delete of Ingress failed:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}
