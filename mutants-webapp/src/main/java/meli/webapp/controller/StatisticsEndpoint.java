package meli.webapp.controller;

import meli.exceptions.InvalidDna;
import meli.interfaces.DetectionService;
import meli.interfaces.StatisticsService;
import meli.webapp.dtos.DnaDto;
import meli.webapp.dtos.StatisticsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.LocalTime;
import java.util.Arrays;


/**
 * All endpoints under the path /stats/ (i.e. for retrieving mutants statistics).
 */
@Path("stats")
@Component
public class StatisticsEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsEndpoint.class);

    @Context
    private UriInfo uriInfo;

    @Autowired
    private StatisticsService statisticsService;

    /**
     * Returns a JSON with the current statistics.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatistics() {
        LOGGER.info("Retrieving statistics at {}", LocalTime.now());
        return Response.ok(new StatisticsDto(statisticsService.generateStatistics())).build();
    }
}
