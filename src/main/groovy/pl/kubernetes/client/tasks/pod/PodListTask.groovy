package pl.kubernetes.client.tasks.pod

import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1Pod
import io.kubernetes.client.models.V1PodList
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class PodListTask extends AbstractKubernetesTask {

    void taskAction() {
        initApiClient()

        CoreV1Api api = new CoreV1Api()
        V1PodList response = api.listPodForAllNamespaces(
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

        for (V1Pod item : response.getItems()) {
            System.out.println(item.getMetadata().getName())
        }
        if (responseFile) {
            responseFile.text = response.toString()
        }
    }
}
