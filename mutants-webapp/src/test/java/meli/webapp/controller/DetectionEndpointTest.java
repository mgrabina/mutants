package meli.webapp.controller;

import meli.webapp.config.TestConfig;
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
    public void testGetHelloWorld() {
        Response response = detectionEndpoint.getHelloWorld();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
//        Assert.assertEquals(URI.create(TestConfig.MOCKED_URL), response.getLocation());
    }
}
