package pl.kubernetes.client.formatter

import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1Service
import io.kubernetes.client.models.V1beta1Ingress
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

abstract class ResourceFileFormatStrategy {

    protected static final Logger logger = Logging.getLogger(ResourceFileFormatStrategy.class)

    Map<String,Object> mapOfResourcesToModels = ['Deployment':
                                                         ['extensions/v1beta1': ExtensionsV1beta1Deployment.class],
                                                 'Ingress'   :
                                                         ['extensions/v1beta1': V1beta1Ingress.class],
                                                 'Service'   :
                                                         ['v1': V1Service.class]
    ]

    abstract def getModel()

    abstract def getMap()

    def mapResourceToModel() {
        return mapOfResourcesToModels
                .get(getMap().get('kind'))
                .get(getMap().get('apiVersion'))
    }

    /* Remove brackets and replace character between key & value. */
    String getLabelSelectors() {
        return getMap().get('metadata').get('labels').toMapString()[1..-2].replaceAll(':', '=')
    }

}