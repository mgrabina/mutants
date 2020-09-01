package meli.webapp.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.time.LocalTime;


/**
 * Basic Jersey controller. Add endpoints here.
 */
@Path("mutants")
@Component
public class DetectionEndpoint {

    public static final String HELLO_WORLD = "Hello World!";

    private static final Logger LOGGER = LoggerFactory.getLogger(DetectionEndpoint.class);

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getHelloWorld() {
        LOGGER.info("Hello, world. Time is {}", LocalTime.now());
        LOGGER.warn("Hello, world! This is a warning log! Time is {}", LocalTime.now());
        LOGGER.error("Hello, world! This is an error log!! Time is {}", LocalTime.now());
        return Response.ok().build();
    }
}
