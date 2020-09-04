package meli.persistence;

import meli.interfaces.StatisticsDao;
import meli.models.DnaRegistry;
import meli.test_config.PersistenceTestConfig;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Test;
import org.junit.Assert;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Testing statistics dao functionality, such as operations with database.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceTestConfig.class)
public class StatisticsDaoImplTest {

    @Autowired
    private StatisticsDao statisticsDao;

    @Test
    public void saveRemoveAndRetrieve() {
        String[] dna = new String[]{"AAA", "AAA", "AAA"};
        Boolean isMutant = false;
        int len = 3;
        statisticsDao.registerNewDna(dna, isMutant, len);
        Optional<DnaRegistry> optionalRegistry = statisticsDao.findByCode(dna);
        Assert.assertEquals(optionalRegistry.get().getLength(), len);
        Assert.assertEquals(optionalRegistry.get().getMutant(), isMutant);
        statisticsDao.removeByCode(dna);
        optionalRegistry = statisticsDao.findByCode(dna);
        Assert.assertFalse(optionalRegistry.isPresent());
    }

    @Test
    public void checkQuantityOfMutants() {
        statisticsDao.registerNewDna(new String[]{"AAA", "AAA", "AAA"}, false, 3);
        Assert.assertEquals(1, statisticsDao.countByIfMutants(false));
    }

}