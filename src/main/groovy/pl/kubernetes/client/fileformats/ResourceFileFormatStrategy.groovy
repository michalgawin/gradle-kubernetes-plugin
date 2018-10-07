package pl.kubernetes.client.fileformats

import io.kubernetes.client.apis.AppsV1Api
import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.apis.ExtensionsV1beta1Api
import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1Deployment
import io.kubernetes.client.models.V1Service
import io.kubernetes.client.models.V1beta1Ingress
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

abstract class ResourceFileFormatStrategy {

    protected static final Logger logger = Logging.getLogger(ResourceFileFormatStrategy.class)

    final Map<String,Object> mapOfResourcesToModels = ['Deployment':
                                                               ['extensions/v1beta1': ExtensionsV1beta1Deployment.class,
                                                                'apps/v1'           : V1Deployment.class],
                                                       'Ingress'   :
                                                               ['extensions/v1beta1': V1beta1Ingress.class],
                                                       'Service'   :
                                                               ['v1'                : V1Service.class]
    ]

    Map<String,Object> mapOfResourcesToApi = ['apps/v1'           : AppsV1Api.class,
                                              'extensions/v1beta1': ExtensionsV1beta1Api.class,
                                              'v1'                : CoreV1Api.class
    ]

    abstract def getModel()

    abstract def getMap()

    def mapResourceToModel() {
        return mapOfResourcesToModels
                .get(getMap().get('kind'))
                .get(getMap().get('apiVersion'))
    }

    def mapResourceToClientApi() {
        return mapOfResourcesToApi.get(getMap().get('apiVersion')).newInstance()
    }

    /* Remove brackets and replace character between key & value. */
    String getLabelSelectors() {
        return getMap().get('metadata').get('labels').toMapString()[1..-2].replaceAll(':', '=')
    }

}