package pl.kubernetes.client.formatter

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper

class JsonFileFormat extends ResourceFileFormatStrategy {

    final JsonSlurper json = new JsonSlurper()
    final File resourceFile
    final Map fileMap

    JsonFileFormat(File resourceFile) {
        this.resourceFile = resourceFile
        this.fileMap = json.parse(resourceFile)
    }

    @Override
    def getModel() {
        ObjectMapper objectMapper = new ObjectMapper()
        return objectMapper.readValue(new FileReader(resourceFile), (Class<Object>) mapResourceToModel())
    }

    @Override
    def getMap() {
        return fileMap
    }

}
