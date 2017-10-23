package com.github.sightler.fabric8.kubernetes;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceList;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.AppsAPIGroupDSL;
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
            ImageStreamList imageStreamList = client.imageStreams().list();
            if (imageStreamList == null) {
                return "ERROR no image stream list returned!";
            }
            List<ImageStream> imageStreams = imageStreamList.getItems();
            for (ImageStream imageStream : imageStreams) {
                result.append("Service " + imageStream.getMetadata().getName() + " has version: " + imageStream.getApiVersion() + System.lineSeparator());
            }
            result.append("Found " + imageStreams.size() + " imagestreams(s)" + System.lineSeparator());

            ServiceList serviceList = client.services().list();
            if (serviceList == null) {
                return "ERROR no service list returned!";
            }
            List<Service> services = serviceList.getItems();
            for (Service service : services) {
                result.append("Service " + service.getMetadata().getName() + " has version: " + service.getApiVersion() + System.lineSeparator());
            }
            result.append("Found " + services.size() + " services(s)" + System.lineSeparator());
        } catch (KubernetesClientException e) {
            result.append("Failed: " + e);
            result.append(System.lineSeparator());
            e.printStackTrace();
        }
        return result.toString();
    }
}
