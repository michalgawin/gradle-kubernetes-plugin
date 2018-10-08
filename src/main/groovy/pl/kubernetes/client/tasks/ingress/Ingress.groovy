package pl.kubernetes.client.tasks.ingress

import io.kubernetes.client.ApiException
import io.kubernetes.client.models.V1DeleteOptions
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractResource

class Ingress extends AbstractResource {

    Ingress(String address, String namespace, String apiKey, String authenticationMethod) {
        super(address, namespace, apiKey, authenticationMethod)
    }

    def create(File requestFile) {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(requestFile)
        def api = kubernetesResourceDescriptor.getClientApi()

        try {
            def response = api.createNamespacedIngress(namespace,
                    kubernetesResourceDescriptor.getObjectModel(),
                    'pretty_example'
            )
            logger.info "Response: ${response.toString()}"
            return response
        } catch (ApiException e) {
            logger.error("Create of Ingress failed:\n${e.getResponseBody()}")
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

            def response = api.deleteNamespacedIngress(name,
                    namespace,
                    v1DeleteOptions,
                    pretty,
                    gracePeriodSeconds,
                    orphanDependents,
                    propagationPolicy
            )
            logger.info "Response: ${response.toString()}"
            return response
        } catch (ApiException e) {
            logger.error("Delete of Ingress failed:\n${e.getResponseBody()}")
        }
        return null
    }
}
