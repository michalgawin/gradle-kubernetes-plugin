package pl.kubernetes.client.tasks.service

import pl.kubernetes.client.tasks.AbstractKubernetesTask

class DeleteServiceTask extends AbstractKubernetesTask {

    void taskAction() {
        def response = new Service(getAddress(),
                getNamespace(),
                getApiKey(),
                getAuthentication()
        ).delete(getRequestFile())

        if (responseFile) {
            responseFile.text = response.toString()
        }
    }

}
