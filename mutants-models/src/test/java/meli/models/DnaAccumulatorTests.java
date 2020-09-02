package meli.models;

import meli.test_config.ModelsTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Testing for {@link DnaAccumulator} model
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelsTestConfig.class)
public class DnaAccumulatorTests {

    private DnaAccumulator createTestAccumulator(int size){
        return new DnaAccumulator(new Character[size], new int[size], DnaAccumulator.TYPE.VERTICAL);
    }

    private DnaAccumulator createInitializedAccumulator(int size){
        DnaAccumulator da = createTestAccumulator(size);
        char c = 'A';
        for (int i = 0 ; i < size ; i++){
            da.setNewCurrent(c++);
            da.setAccumulation(i, i % (size/3) + 1);
        }
        return da;
    }

    @Test
    public void saveAux() {
        DnaAccumulator da = createTestAccumulator(10);
        char testLetter = 'A';
        da.setNewCurrent(testLetter);
        da.incrementCurrentAccumulation();
        int accum = da.getCurrentAccumulation();
        da.saveAux();
        Assert.assertTrue("Aux letter", da.getAux() == testLetter);
        Assert.assertTrue("Aux accum", da.getAuxAccum() == accum);
    }

    @Test
    public void cleanAux(){
        DnaAccumulator da = createTestAccumulator(10);
        char testLetter = 'A';
        da.setNewCurrent(testLetter);
        da.saveAux();
        da.cleanAux();
        Assert.assertTrue("Aux letter", da.getAux() != testLetter);
        Assert.assertTrue("Aux accum", da.getAuxAccum() == 0);
    }

    @Test
    public void incrementSequences() {
        int size = 10;
        DnaAccumulator da = createInitializedAccumulator(size);
        int previous = da.getSequencesCount();
        da.incrementCounter();
        Assert.assertTrue("Sequences incremented", previous + 1 == da.getSequencesCount());
    }

}