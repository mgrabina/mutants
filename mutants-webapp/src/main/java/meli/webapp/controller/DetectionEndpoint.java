package meli.webapp.controller;

import meli.exceptions.InvalidDna;
import meli.interfaces.DetectionService;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.time.LocalTime;
import java.util.Arrays;

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

    /**
     * Retrieves 200 (OK) if the given dna refers to a mutant or 403 otherwise.
     *
     * @param dnaDto    The given dna to analyze
     * @return          A HTTP {@link Response} indicating the result of the analysis.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response detectMutant(DnaDto dnaDto) {
        LOGGER.info("New detection required at {}", LocalTime.now());
        LOGGER.debug("Detection required {}", Arrays.toString(dnaDto.getDnaCode()));
        try {
            if (detectionService.checkIfMutantAndSave(dnaDto.getDnaCode()))
                return Response.ok().build();
        } catch (InvalidDna exception){
            return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
