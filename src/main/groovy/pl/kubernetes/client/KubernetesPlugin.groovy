import org.gradle.api.Plugin
import org.gradle.api.Project

import pl.kubernetes.client.AbstractKubernetesTask

class KubernetesPluginExtension {
    String address = 'http://127.0.0.1:8080'
    String apiKey = null
    String namespace = 'default'
    String authentication = 'BearerToken'
}

class KubernetesPlugin implements Plugin<Project> {
    void apply(Project project) {
        def extension = project.extensions.create('kubernetes', KubernetesPluginExtension)

        project.tasks.withType(AbstractKubernetesTask) { task ->
            task.address = { project.kubernetes.address }
            task.apiKey = { project.kubernetes.apiKey }
            task.namespace = { project.kubernetes.namespace }
            task.authentication = { project.kubernetes.authentication }
        }
    }
}
