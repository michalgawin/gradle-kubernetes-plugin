package pl.kubernetes.client.tasks.ingress

import pl.kubernetes.client.tasks.AbstractKubernetesTask

class DeleteIngressTask extends AbstractKubernetesTask {

    void taskAction() {
        def response = new Ingress(getAddress(),
                getNamespace(),
                getApiKey(),
                getAuthentication()
        ).delete(getRequestFile())

        if (responseFile) {
            responseFile.text = response.toString()
        }
    }

}
