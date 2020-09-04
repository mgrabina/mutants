package meli.services;

import meli.exceptions.InvalidDna;
import meli.interfaces.DetectionService;
import meli.interfaces.StatisticsService;
import meli.models.Statistics;
import meli.test_config.ServicesTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Testing for the statistics service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServicesTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class StatisticsServiceImplTest {

    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void mutantExampleOneHorizontalOneVerticalAndOneDiagonal1() {
        Statistics stats = statisticsService.generateStatistics();
        Assert.assertTrue("Should be an instance of Statistics", stats instanceof Statistics);
        Assert.assertNotNull(stats.getMutantsQuantity());
        Assert.assertNotNull(stats.getNonMutantsQuantity());
        Assert.assertNotNull(stats.getRatio());
    }
}