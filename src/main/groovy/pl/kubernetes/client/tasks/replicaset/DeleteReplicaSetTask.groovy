package pl.kubernetes.client.tasks.replicaset

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.AppsV1Api
import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1ReplicaSetList
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1Status
import pl.kubernetes.client.KubernetesResourceDescriptor
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class DeleteReplicaSetTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesResourceDescriptor kubernetesResourceDescriptor = new KubernetesResourceDescriptor(getRequestFile())
        ExtensionsV1beta1Deployment deployment = (ExtensionsV1beta1Deployment) kubernetesResourceDescriptor.getObjectModel()

        initApiClient()
        AppsV1Api api = new AppsV1Api()
        
        String pretty = null
        String cont = null
        String fieldSelector = null
        Boolean includeUninitialized = true
        //remove brackets and replace character between key & value
        String labelSelector = kubernetesResourceDescriptor.getLabelSelectors()
        Integer limit = 0
        String resourceVersion = null
        Integer timeoutSeconds = null
        Boolean watch = null

        def responseBuilder = StringBuilder.newInstance()
        try {
            V1ReplicaSetList result = api.listNamespacedReplicaSet(getNamespace(),
                pretty,
                cont,
                fieldSelector,
                includeUninitialized,
                labelSelector,
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
                    getNamespace(),
                    new V1DeleteOptions(),
                    pretty,
                    gracePeriodSeconds,
                    orphanDependents,
                    propagationPolicy
                )
                responseBuilder << response.toString() << '\n'
                logger.info("Response: ${response.toString()}")
            }
            if (responseFile) {
                responseFile.text = responseBuilder.toString()
            }
        } catch (ApiException e) {
            logger.error("Delete of ReplicaSet failed:\n${e.getResponseBody()}")
            e.printStackTrace()
        }
    }

}
