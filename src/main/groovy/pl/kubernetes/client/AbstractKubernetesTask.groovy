package pl.kubernetes.client

import org.gradle.api.tasks.OutputFile
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
    def authentication = "BearerToken"

    @Input @Optional
    def apiKey

    @Input @Optional
    def namespace

    @Input @Optional
    def requestFile

    @OutputFile
    def responseFile

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

    File getRequestFile() {
        if (requestFile instanceof Closure) {
            return requestFile()
        }
        return requestFile
    }

    File getResponseFile() {
        if (responseFile instanceof Closure) {
            return responseFile()
        }
        return responseFile
    }

    def initApiClient() {
        ApiClient client = Config.defaultClient()
        if (getAddress()) {
            logger.debug("Kubernetes address: ${getAddress().toString()}")
            client.setBasePath(getAddress())
        }
        if (getApiKey() && getAuthentication()) {
            ApiKeyAuth apiKeyAuth = (ApiKeyAuth) client.getAuthentication(getAuthentication())
            apiKeyAuth.setApiKey(getApiKey())
        }
        Configuration.setDefaultApiClient(client)
    }

    @TaskAction
    abstract void taskAction()

}