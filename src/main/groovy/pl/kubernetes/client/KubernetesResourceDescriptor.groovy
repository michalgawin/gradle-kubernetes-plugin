package pl.kubernetes.client

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1Namespace
import io.kubernetes.client.models.V1Service
import pl.kubernetes.client.formatter.ResourceFileFormatStrategy
import pl.kubernetes.client.formatter.JsonFileFormat
import pl.kubernetes.client.formatter.YamlFileFormat

class KubernetesResourceDescriptor {

    private static final Logger logger = Logging.getLogger(KubernetesResourceDescriptor.class)

    final ResourceFileFormatStrategy resourceFileFormatStrategy


    Map<String,Object> mapKindToK8s = ['Deployment': ExtensionsV1beta1Deployment.class,
                                 'Namespace': V1Namespace.class,
                                 'Service': V1Service.class]

    KubernetesResourceDescriptor(File kubernetesResourceFile) {
        if (kubernetesResourceFile.getName().endsWith(".json")) {
            this.resourceFileFormatStrategy = new JsonFileFormat(kubernetesResourceFile)
        } else {
            this.resourceFileFormatStrategy = new YamlFileFormat(kubernetesResourceFile)
        }
    }

    Object getObjectModel() {
        def obj = resourceFileFormatStrategy.getModel()
        logger.debug("File content: ${obj.toString()}")
        return obj
	}

    /* Remove brackets and replace character between key & value. */
    String getLabelSelectors() {
        return resourceFileFormatStrategy.getLabelSelectors()
    }

}