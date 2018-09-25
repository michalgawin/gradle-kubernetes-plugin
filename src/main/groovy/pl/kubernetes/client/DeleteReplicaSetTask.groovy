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
import io.kubernetes.client.Configuration
import io.kubernetes.client.apis.AppsV1Api
import io.kubernetes.client.models.ExtensionsV1beta1Deployment
import io.kubernetes.client.models.V1ReplicaSetList
import io.kubernetes.client.models.V1DeleteOptions
import io.kubernetes.client.models.V1Namespace
import io.kubernetes.client.models.V1Service
import io.kubernetes.client.models.V1Status

import java.io.IOException


class DeleteReplicaSetTask extends AbstractKubernetesTask {

    void taskAction() {
        KubernetesFileDescriptor kubernetesFileDescriptor = new KubernetesFileDescriptor(getConf())
        ExtensionsV1beta1Deployment deployment = (ExtensionsV1beta1Deployment) kubernetesFileDescriptor.mapFileToKubernetesObject()

        setClient()
        AppsV1Api api = new AppsV1Api()
        
        String pretty = null
        String cont = null
        String fieldSelector = null
        Boolean includeUninitialized = true
        //remove brackets and replace character between key & value
        String labelSelector = kubernetesFileDescriptor.getLabelSelectors()
        Integer limit = 0
        String resourceVersion = null
        Integer timeoutSeconds = null
        Boolean watch = null

        try {
            V1ReplicaSetList result = api.listNamespacedReplicaSet(getNamespace(),
                pretty,
                cont,
                fieldSelector,
                includeUninitialized,
                labelSelector,
                limit,
                resourceVersion,
                timeoutSeconds,
                watch
            )

            result.items.each { replicaSet ->
                String replicaSetName = replicaSet.metadata.name.toString()
                Integer gracePeriodSeconds = 0
                Boolean orphanDependents = null
                String propagationPolicy = 'Foreground'

                V1Status response = api.deleteNamespacedReplicaSet(replicaSetName,
                    getNamespace(),
                    new V1DeleteOptions(),
                    pretty,
                    gracePeriodSeconds,
                    orphanDependents,
                    propagationPolicy
                )
                logger.info(response.toString())
            }
        } catch (JsonSyntaxException e) {
            logger.error("Known issue: https://github.com/kubernetes-client/java/issues/86");
        } catch (ApiException e) {
            logger.error("Exception when calling AppsV1beta1Api#deleteNamespacedDeployment:\n${e.getResponseBody()}");
            e.printStackTrace();
        }
    }

}
