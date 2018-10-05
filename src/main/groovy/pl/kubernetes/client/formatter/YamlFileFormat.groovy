package pl.kubernetes.client.formatter

import org.yaml.snakeyaml.Yaml

class YamlFileFormat extends ResourceFileFormatStrategy {

    final Yaml yaml = new Yaml()
    final File resourceFile
    final Map fileMap

    YamlFileFormat(File resourceFile) {
        this.resourceFile = resourceFile
        this.fileMap = yaml.load(new FileInputStream(resourceFile))
    }

    @Override
    def getModel() {
        return yaml.loadAs(new FileReader(resourceFile), (Class<Object>) mapResourceToModel())
    }

    @Override
    def getMap() {
        return fileMap
    }

}
