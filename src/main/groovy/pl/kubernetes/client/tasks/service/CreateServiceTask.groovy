package pl.kubernetes.client.tasks.service

import pl.kubernetes.client.tasks.AbstractKubernetesTask

class CreateServiceTask extends AbstractKubernetesTask {

    void taskAction() {
        def response = new Service(getAddress(),
                getNamespace(),
                getApiKey(),
                getAuthentication()
        ).create(getRequestFile())

        if (responseFile) {
            responseFile.text = response.toString()
        }
    }

}
