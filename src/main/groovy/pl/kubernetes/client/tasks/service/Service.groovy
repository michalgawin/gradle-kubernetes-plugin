package pl.kubernetes.client.tasks.service

import io.kubernetes.client.ApiException
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1Status
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractResource

class Service extends AbstractResource {

    Service(String address, String namespace, String apiKey, String authenticationMethod) {
        super(address, namespace, apiKey, authenticationMethod)
        initApiClient()
    }

    def create(File requestFile) {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(requestFile)
        def api = kubernetesResourceDescriptor.getClientApi()

        try {
            def response = api.createNamespacedService(namespace,
                    kubernetesResourceDescriptor.getObjectModel(),
                    'pretty_example'
            )
            logger.info "Response: ${response.toString()}"
            return response
        } catch (ApiException e) {
            logger.error("Create of Service failed:\n${e.getResponseBody()}")
        }
        return null
    }

    def delete(File requestFile) {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(requestFile)
        def api = kubernetesResourceDescriptor.getClientApi()

        try {
            String name = kubernetesResourceDescriptor.getObjectModel().metadata.name
            V1DeleteOptions v1DeleteOptions = new V1DeleteOptions()
            String pretty = null
            int gracePeriodSeconds = 0
            boolean orphanDependents = null
            String propagationPolicy = 'Foreground'

            V1Status response = api.deleteNamespacedService(name,
                    namespace,
                    v1DeleteOptions,
                    pretty,
                    gracePeriodSeconds,
                    orphanDependents,
                    propagationPolicy
            )
            logger.info "Response: ${response.toString()}"
        } catch (ApiException e) {
            logger.error("Delete of Service failed:\n${e.getResponseBody()}")
        }
        return null
    }
}
