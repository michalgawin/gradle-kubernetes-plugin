package pl.kubernetes.client.tasks.ingress

import pl.kubernetes.client.tasks.AbstractKubernetesTask

class CreateIngressTask extends AbstractKubernetesTask {

    void taskAction() {
        def response = new Ingress(getAddress(),
                getNamespace(),
                getApiKey(),
                getAuthentication()
        ).create(getRequestFile())

        if (responseFile) {
            responseFile.text = response.toString()
        }
    }

}
