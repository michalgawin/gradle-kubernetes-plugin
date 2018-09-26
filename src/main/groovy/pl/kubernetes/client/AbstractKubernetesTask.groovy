package pl.kubernetes.client

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.InputStream
import java.util.HashMap
import java.util.Map

import org.gradle.api.tasks.TaskAction
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional

import io.kubernetes.client.auth.ApiKeyAuth
import io.kubernetes.client.util.Config
import io.kubernetes.client.ApiClient
import io.kubernetes.client.Configuration
import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1Namespace
import io.kubernetes.client.models.V1Service


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

    def setClient() {
        ApiClient client = Config.defaultClient()
        if (getAddress()) {
            System.out.println("Kubernetes address: " + getAddress().toString())
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