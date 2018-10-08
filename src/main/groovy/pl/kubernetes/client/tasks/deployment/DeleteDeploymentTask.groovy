package pl.kubernetes.client.tasks.deployment

import pl.kubernetes.client.tasks.AbstractKubernetesTask

class DeleteDeploymentTask extends AbstractKubernetesTask {

    void taskAction() {
        def response = new Deployment(getAddress(),
                getNamespace(),
                getApiKey(),
                getAuthentication()
        ).delete(getRequestFile())

        if (responseFile) {
            responseFile.text = response.toString()
        }
    }

}
