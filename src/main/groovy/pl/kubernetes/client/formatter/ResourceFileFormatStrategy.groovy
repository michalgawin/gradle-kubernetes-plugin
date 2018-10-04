package pl.kubernetes.client.formatter

import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1Service
import io.kubernetes.client.models.V1beta1Ingress
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

abstract class ResourceFileFormatStrategy {

    protected static final Logger logger = Logging.getLogger(ResourceFileFormatStrategy.class)

    Map<String,Object> mapKindToK8s = ['Deployment': ExtensionsV1beta1Deployment.class,
                                       'Ingress': V1beta1Ingress.class,
                                       'Service': V1Service.class]

    abstract def getModel()

    abstract def getMap()

    String getKind() {
        return getMap().get('kind')
    }

    /* Remove brackets and replace character between key & value. */
    String getLabelSelectors() {
        return getFileMap().get('metadata').get('labels').toMapString()[1..-2].replaceAll(':', '=')
    }

}