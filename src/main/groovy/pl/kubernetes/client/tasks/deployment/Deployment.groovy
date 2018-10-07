package pl.kubernetes.client.tasks.deployment

import io.kubernetes.client.ApiException
import io.kubernetes.client.models.V1DeleteOptions
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractResource

class Deployment extends AbstractResource {

    Deployment(String address, String namespace, String apiKey, String authenticationMethod) {
        super(address, namespace, apiKey, authenticationMethod)
        initApiClient()
    }

    def create(File requestFile) {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(requestFile)
        def api = kubernetesResourceDescriptor.getClientApi() // new ExtensionsV1beta1Api()

        try {
            def response = api.createNamespacedDeployment(namespace,
                    kubernetesResourceDescriptor.getObjectModel(),
                    'pretty_example'
            )
            logger.info("Response: ${response.toString()}")
            return response
        } catch (ApiException e) {
            logger.error("Create of Deployment failed:\n${e.getResponseBody()}")
        }
        return null
    }

    def delete(File requestFile) {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(requestFile)
        def api = kubernetesResourceDescriptor.getClientApi() // new AppsV1Api()

        String name = kubernetesResourceDescriptor.getObjectModel().metadata.name
        V1DeleteOptions v1DeleteOptions = new V1DeleteOptions()
        String pretty = null
        int gracePeriodSeconds = 0
        boolean orphanDependents = null
        String propagationPolicy = 'Foreground'

        try {
            def response = api.deleteNamespacedDeployment(name,
                    namespace,
                    v1DeleteOptions,
                    pretty,
                    gracePeriodSeconds,
                    orphanDependents,
                    propagationPolicy
            )
            logger.info("Response: ${response.toString()}")
            return response
        } catch (ApiException e) {
            logger.error("Delete of Deployment failed:\n${e.getResponseBody()}")
        }
        return null
    }
}
