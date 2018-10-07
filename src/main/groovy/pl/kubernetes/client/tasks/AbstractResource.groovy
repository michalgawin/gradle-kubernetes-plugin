package pl.kubernetes.client.tasks

import io.kubernetes.client.ApiClient
import io.kubernetes.client.Configuration
import io.kubernetes.client.auth.ApiKeyAuth
import io.kubernetes.client.util.Config
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

abstract class AbstractResource {

    protected static final Logger logger = Logging.getLogger(AbstractResource.class)

    final String address
    final String namespace
    final String authenticationMethod
    final String apiKey
    final ApiClient client

    AbstractResource(String address, String namespace, String apiKey, String authenticationMethod) {
        this.address = address
        this.namespace = namespace
        this.authenticationMethod = authenticationMethod
        this.apiKey = apiKey
        this.client = Config.defaultClient()
    }

    protected void initApiClient() {
        if (address) {
            setApiClientAddress(address)
        }
        if (apiKey && authenticationMethod) {
            setApiClientAuthentication(apiKey, authenticationMethod)
        }
        Configuration.setDefaultApiClient(client)
    }

    private void setApiClientAddress(String address) {
        logger.debug "Kubernetes address: ${address}"
        client.setBasePath(address)
    }

    private void setApiClientAuthentication(String apiKey, String authenticationMethod) {
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) client.getAuthentication(authenticationMethod)
        apiKeyAuth.setApiKey(apiKey)
    }

}
