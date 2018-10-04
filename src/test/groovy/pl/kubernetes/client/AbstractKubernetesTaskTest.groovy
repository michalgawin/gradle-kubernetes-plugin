package pl.kubernetes.client

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import pl.kubernetes.client.tasks.AbstractKubernetesTask

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.CALLS_REAL_METHODS
import static org.mockito.Mockito.withSettings

class AbstractKubernetesTaskTest {

    protected AbstractKubernetesTask abstractKubernetesTask = Mockito.mock(AbstractKubernetesTask.class, withSettings().defaultAnswer(CALLS_REAL_METHODS))

    @Test
    void testAddress() {
        def ip = "http://1.2.3.4:9999"
        abstractKubernetesTask.setAddress(ip)
        assertThat(abstractKubernetesTask.getAddress()).isEqualTo(ip)

        ip = "http://1.2.3.4:99"
        abstractKubernetesTask.setAddress({ ip })
        assertThat(abstractKubernetesTask.getAddress()).isEqualTo(ip)
    }

    @Test
    void testApiKey() {
        def apiKey = "A0B2C4"
        abstractKubernetesTask.setApiKey(apiKey)
        assertThat(abstractKubernetesTask.getApiKey()).isEqualTo(apiKey)

        apiKey = "ABCDEFGHIJKKL"
        abstractKubernetesTask.setApiKey({ apiKey })
        assertThat(abstractKubernetesTask.getApiKey()).isEqualTo(apiKey)
    }

    @Test
    void testNamespace() {
        def namespace = "default_namespace"
        abstractKubernetesTask.setNamespace(namespace)
        assertThat(abstractKubernetesTask.getNamespace()).isEqualTo(namespace)

        namespace = "custom_namespace"
        abstractKubernetesTask.setNamespace({ namespace })
        assertThat(abstractKubernetesTask.getNamespace()).isEqualTo(namespace)
    }

    @Test
    void testRequestFile() {
        def reqFile = new File("req_file1")
        abstractKubernetesTask.setRequestFile(reqFile)
        assertThat(abstractKubernetesTask.getRequestFile().getAbsolutePath()).isEqualTo(reqFile.getAbsolutePath())

        reqFile = new File("req_file2")
        abstractKubernetesTask.setRequestFile({ reqFile })
        assertThat(abstractKubernetesTask.getRequestFile().getAbsolutePath()).isEqualTo(reqFile.getAbsolutePath())
    }

    @Test
    void testResponseFile() {
        def responseFile = new File("resp_file1")
        abstractKubernetesTask.setResponseFile(responseFile)
        assertThat(abstractKubernetesTask.getResponseFile().getAbsolutePath()).isEqualTo(responseFile.getAbsolutePath())

        responseFile = new File("resp_file2")
        abstractKubernetesTask.setResponseFile({ responseFile })
        assertThat(abstractKubernetesTask.getResponseFile().getAbsolutePath()).isEqualTo(responseFile.getAbsolutePath())
    }

}
