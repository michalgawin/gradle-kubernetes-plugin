package pl.kubernetes.client.tasks.pod

import io.kubernetes.client.models.V1Pod
import pl.kubernetes.client.tasks.AbstractKubernetesTask

class PodListTask extends AbstractKubernetesTask {

    void taskAction() {
        List<V1Pod> pods = new Pod(getAddress(),
                getNamespace(),
                getApiKey(),
                getAuthentication()
        ).getAll().getItems()

        if (responseFile) {
            def responseBuilder = StringBuilder.newInstance()
            for (V1Pod v1Pod : pods) {
                responseBuilder << v1Pod.toString() << '\n'
            }
            responseFile.text = responseBuilder.toString()
            logger.info "${responseBuilder.toString()}"
        }
    }
}
