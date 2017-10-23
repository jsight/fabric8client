package com.github.sightler.fabric8.kubernetes;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.openshift.api.model.ImageStream;
import io.fabric8.openshift.api.model.ImageStreamList;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftAPIGroups;
import io.fabric8.openshift.client.OpenShiftClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
@Path("/test")
public class TestClientService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listImageStreams() {
        StringBuilder result = new StringBuilder();

        try {
            OpenShiftClient client = new DefaultOpenShiftClient();
            if (!client.supportsOpenShiftAPIGroup(OpenShiftAPIGroups.IMAGE)) {
                return "WARNING this cluster does not support the API Group " + OpenShiftAPIGroups.IMAGE;
            }
            ImageStreamList list = client.imageStreams().list();
            if (list == null) {
                return "ERROR no list returned!";
            }
            List<ImageStream> items = list.getItems();
            for (ImageStream item : items) {
                result.append("ImageStream " + item.getMetadata().getName() + " has version: " + item.getApiVersion() + System.lineSeparator());
            }
            result.append("Found " + items.size() + " ImageStream(s)" + System.lineSeparator());
        } catch (KubernetesClientException e) {
            result.append("Failed: " + e);
            result.append(System.lineSeparator());
            e.printStackTrace();
        }
        return result.toString();
    }
}