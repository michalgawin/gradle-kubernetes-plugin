package pl.kubernetes.client.tasks.replicaset

import pl.kubernetes.client.tasks.AbstractKubernetesTask

class DeleteReplicaSetTask extends AbstractKubernetesTask {

    void taskAction() {
        new ReplicaSet(getAddress(),
                getNamespace(),
                getApiKey(),
                getAuthentication()
        ).delete(getRequestFile())
    }

}
