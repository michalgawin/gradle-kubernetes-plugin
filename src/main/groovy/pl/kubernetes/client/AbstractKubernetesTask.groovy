package pl.kubernetes.client

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

import io.kubernetes.client.auth.ApiKeyAuth
import io.kubernetes.client.util.Config
import io.kubernetes.client.ApiClient
import io.kubernetes.client.Configuration


abstract class AbstractKubernetesTask extends DefaultTask {

    @Input
    def address

    @Input @Optional
    def apiKey

    @Input @Optional
    def namespace

    @Input @Optional
    File conf

    String getAddress() {
        if (address instanceof Closure) {
            return address()
        }
        return address.toString()
    }

    String getApiKey() {
        if (apiKey instanceof Closure) {
            return apiKey()
        }
        return apiKey.toString()
    }

    String getNamespace() {
        if (namespace instanceof Closure) {
            return namespace()
        }
        return namespace.toString()
    }

    String getConf() {
        if (conf instanceof Closure) {
            return conf()
        }
        return conf.toString()
    }

    def initApiClient() {
        ApiClient client = Config.defaultClient()
        if (getAddress()) {
            logger.debug("Kubernetes address: ${getAddress().toString()}")
            client.setBasePath(getAddress())
        }
        if (getApiKey()) {
            ApiKeyAuth BearerToken = (ApiKeyAuth) client.getAuthentication("BearerToken")
            BearerToken.setApiKey(getApiKey())
        }
        Configuration.setDefaultApiClient(client)
    }

    @TaskAction
    abstract void taskAction()

}