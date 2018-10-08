package pl.kubernetes.client.tasks.deployment

import pl.kubernetes.client.tasks.AbstractKubernetesTask

class CreateDeploymentTask extends AbstractKubernetesTask {

    void taskAction() {
        def response = new Deployment(getAddress(),
                getNamespace(),
                getApiKey(),
                getAuthentication()
        ).create(getRequestFile())

        if (responseFile) {
            responseFile.text = response.toString()
        }
    }

}
