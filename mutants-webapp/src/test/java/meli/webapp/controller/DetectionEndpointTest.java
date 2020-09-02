package meli.webapp.controller;

import meli.webapp.config.TestConfig;
import meli.webapp.dtos.DnaDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.net.URI;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DetectionEndpointTest {

    @Autowired
    private DetectionEndpoint detectionEndpoint;

    @Test
    public void mutantResponse() {
        Assert.assertEquals(HttpStatus.OK.value(), detectionEndpoint.detectMutant(new DnaDto(
                new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"}
        )).getStatus());
    }

    @Test
    public void notMutantResponse() {
        Assert.assertEquals(HttpStatus.FORBIDDEN.value(), detectionEndpoint.detectMutant(new DnaDto(
                new String[]{"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"}
        )).getStatus());
    }

    @Test
    public void invalidInputResponse() {
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), detectionEndpoint.detectMutant(new DnaDto()).getStatus());
    }
}
