package pl.kubernetes.client

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.InputStream
import java.util.HashMap
import java.util.Map

import com.google.gson.JsonSyntaxException

import io.kubernetes.client.ApiException
import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1Service
import io.kubernetes.client.models.V1Status

import java.io.IOException


class DeleteServiceTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesFileDescriptor kubernetesFileDescriptor = new KubernetesFileDescriptor(getConf())
        V1Service service = (V1Service) kubernetesFileDescriptor.mapFileToKubernetesObject()
		logger.debug(service.toString())

        setClient()
        CoreV1Api api = new CoreV1Api()
        
        try {
            int gracePeriodSeconds = 0
            boolean orphanDependents = null
            String propagationPolicy = 'Foreground'

            V1Status response = api.deleteNamespacedService(kubernetesFileDescriptor.getName(),
                getNamespace(),
                new V1DeleteOptions(),
                null,
                gracePeriodSeconds,
                orphanDependents,
                propagationPolicy
            )
            logger.info(response.toString())
        } catch (JsonSyntaxException e) {
            logger.error("Known issue: https://github.com/kubernetes-client/java/issues/86");
        } catch (ApiException e) {
            logger.error("Exception when calling AppsV1beta1Api#deleteNamespacedService:\n${e.getResponseBody()}");
            e.printStackTrace();
        }
    }

}
