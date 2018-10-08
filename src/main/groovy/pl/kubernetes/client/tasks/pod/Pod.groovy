package pl.kubernetes.client.tasks.pod

import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1PodList
import pl.kubernetes.client.tasks.AbstractResource

class Pod extends AbstractResource {

    Pod(String address, String namespace, String apiKey, String authenticationMethod) {
        super(address, namespace, apiKey, authenticationMethod)
    }

    def get(String labels) {
        CoreV1Api coreV1Api = new CoreV1Api()
        String pretty = 'pretty_example'

        V1PodList pods = coreV1Api.listNamespacedPod(namespace,
                pretty,
                null,
                null,
                true,
                labels,
                null,
                null,
                10,
                null
        )
        return pods
    }

    def getAll() {
        CoreV1Api api = new CoreV1Api()

        V1PodList pods = api.listPodForAllNamespaces(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        )
        return pods
    }
}
