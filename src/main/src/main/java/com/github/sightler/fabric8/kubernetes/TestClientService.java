package com.github.sightler.fabric8.kubernetes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
@Path("/test")
public class TestClientService {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listImageStreams() {
        return "hello";
    }
}
