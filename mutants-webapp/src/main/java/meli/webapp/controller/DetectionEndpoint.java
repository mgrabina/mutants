package meli.webapp.controller;

import meli.interfaces.DetectionService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.time.LocalTime;
import meli.webapp.dtos.*;


/**
 * Main endpoints path of the system containing all endpoints under the path /mutants/ (i.e. detection).
 */
@Path("mutants")
@Component
public class DetectionEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetectionEndpoint.class);

    @Context
    private UriInfo uriInfo;

    @Autowired
    private DetectionService detectionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response detectMutant(DnaDto dnaDto) {
//        LOGGER.info("New detection required at {}", LocalTime.now(), );
//        LOGGER.info("New detection required at {}", LocalTime.now());
//        LOGGER.debug("Detection required {}", dnaDto.);
//        LOGGER.warn("Hello, world! This is a warning log! Time is {}", LocalTime.now());
//        LOGGER.error("Hello, world! This is an error log!! Time is {}", LocalTime.now());
        if (detectionService.isMutant(dnaDto.getDnaCode()))
            return Response.ok().build();
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
