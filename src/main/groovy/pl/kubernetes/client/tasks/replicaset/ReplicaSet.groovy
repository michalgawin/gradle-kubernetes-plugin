package pl.kubernetes.client.tasks.replicaset

import io.kubernetes.client.ApiException
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1ReplicaSetList
import io.kubernetes.client.models.V1Status
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractResource

class ReplicaSet extends AbstractResource {

    ReplicaSet(String address, String namespace, String apiKey, String authenticationMethod) {
        super(address, namespace, apiKey, authenticationMethod)
        initApiClient()
    }

    def delete(File requestFile) {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(requestFile)
        def api = kubernetesResourceDescriptor.getClientApi()

        String pretty = null
        String cont = null
        String fieldSelector = null
        Boolean includeUninitialized = true
        //remove brackets and replace character between key & value
        Integer limit = 0
        String resourceVersion = null
        Integer timeoutSeconds = null
        Boolean watch = null

        def responseBuilder = StringBuilder.newInstance()
        try {
            V1ReplicaSetList result = api.listNamespacedReplicaSet(namespace,
                    pretty,
                    cont,
                    fieldSelector,
                    includeUninitialized,
                    kubernetesResourceDescriptor.getLabelSelectors(),
                    limit,
                    resourceVersion,
                    timeoutSeconds,
                    watch
            )

            result.items.each { replicaSet ->
                String replicaSetName = replicaSet.metadata.name
                Integer gracePeriodSeconds = 0
                Boolean orphanDependents = null
                String propagationPolicy = 'Foreground'

                V1Status response = api.deleteNamespacedReplicaSet(replicaSetName,
                        namespace,
                        new V1DeleteOptions(),
                        pretty,
                        gracePeriodSeconds,
                        orphanDependents,
                        propagationPolicy
                )
                responseBuilder << response.toString() << '\n'
                logger.info "Response: ${response.toString()}"
            }
        } catch (ApiException e) {
            logger.error "Delete of ReplicaSet failed:\n${e.getResponseBody()}"
        }
        return null
    }
}
