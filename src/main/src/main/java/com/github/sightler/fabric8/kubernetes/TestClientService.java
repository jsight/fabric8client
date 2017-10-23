package com.github.sightler.fabric8.kubernetes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author <a href="mailto:jesse.sightler@gmail.com">Jesse Sightler</a>
 */
@Path("/test")
public class TestClientService {
    @GET
    public String listImageStreams() {
        return "hello";
    }
}
