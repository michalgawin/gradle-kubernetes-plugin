package pl.kubernetes.client

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.yaml.snakeyaml.Yaml

import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1Namespace
import io.kubernetes.client.models.V1Service


class KubernetesFileDescriptor {

    private static final Logger logger = Logging.getLogger(KubernetesFileDescriptor.class)

    final File kubernetesDescriptor
    final Yaml yaml = new Yaml()
    Map fileMap = null

    Map<String,Object> mapKindToK8s = ['Deployment': ExtensionsV1beta1Deployment.class,
                                 'Namespace': V1Namespace.class,
                                 'Service': V1Service.class]

    KubernetesFileDescriptor(File kubernetesDescriptor) {
        this.kubernetesDescriptor = kubernetesDescriptor
    }

    KubernetesFileDescriptor(String kubernetesDescriptor) {
        this(new File(kubernetesDescriptor))
    }

    Object mapFileToKubernetesObject() {
        def obj = yaml.loadAs(new FileReader(kubernetesDescriptor), (Class<Object>) mapKindToK8s.get(getKind()))
        logger.debug("File content: ${obj.toString()}")
        return obj
	}

    private Map getFileMap() {
        if (!fileMap) {
	        InputStream input = new FileInputStream(kubernetesDescriptor)
		    fileMap = yaml.load(input)
        }
        return fileMap
    }

    String getKind() {
        return getFileMap().get('kind')
    }

    /* Remove brackets and replace character between key & value. */
    String getLabelSelectors() {
        return getFileMap().get('metadata').get('labels').toMapString()[1..-2].replaceAll(':', '=')
    }

}