package pl.kubernetes.client

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.InputStream
import java.util.HashMap
import java.util.Map

import org.yaml.snakeyaml.Yaml

import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1Namespace
import io.kubernetes.client.models.V1Service


class KubernetesFileDescriptor {

    final File kubernetesDescriptor
    final Yaml yaml = new Yaml()
    Map fileMap = null

    Map<String,Object> mapKindToK8s = ['Deployment': ExtensionsV1beta1Deployment.class,
                                 'Namespace': V1Namespace.class,
                                 'Service': V1Service.class]

    public KubernetesFileDescriptor(File kubernetesDescriptor) {
        this.kubernetesDescriptor = kubernetesDescriptor
    }

    public KubernetesFileDescriptor(String kubernetesDescriptor) {
        this(new File(kubernetesDescriptor))
    }

    Object mapFileToKubernetesObject() {
		return yaml.loadAs(new FileReader(kubernetesDescriptor), (Class<Object>) mapKindToK8s.get(getKind()));
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

    String getName() {
        return getFileMap().get('metadata').get('name')
    }

}