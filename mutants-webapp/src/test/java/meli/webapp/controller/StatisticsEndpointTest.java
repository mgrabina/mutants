package meli.webapp.controller;

import meli.webapp.config.TestConfig;
import meli.webapp.dtos.DnaDto;
import meli.webapp.dtos.StatisticsDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StatisticsEndpointTest {

    @Autowired
    private StatisticsEndpoint statisticsEndpoint;

    @Test
    public void getStatsReponse() {
        Response response = statisticsEndpoint.getStatistics();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertTrue(response.getEntity() instanceof StatisticsDto);
    }
}
