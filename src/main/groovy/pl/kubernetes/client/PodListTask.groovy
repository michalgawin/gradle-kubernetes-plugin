package pl.kubernetes.client

import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1Pod
import io.kubernetes.client.models.V1PodList


class PodListTask extends AbstractKubernetesTask {

    void taskAction() {
        initApiClient()

        CoreV1Api api = new CoreV1Api()
        V1PodList list = api.listPodForAllNamespaces(
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

        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName())
        }
    }
}
