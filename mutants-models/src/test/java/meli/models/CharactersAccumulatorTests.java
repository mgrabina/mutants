package meli.models;

import meli.test_config.ModelsTestConfig;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Test;
import org.junit.Assert;

/**
 * Testing for {@link CharactersAccumulator} model
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ModelsTestConfig.class)
public class CharactersAccumulatorTests {

    private CharactersAccumulator createTestAccumulator(int size){
        return new CharactersAccumulator(new Character[size], new int[size]);
    }

    private CharactersAccumulator createInitializedAccumulator(int size){
        CharactersAccumulator ca = createTestAccumulator(size);
        char c = 'A';
        for (int i = 0 ; i < size ; i++){
            ca.setNewCurrent(c++);
            ca.setAccumulation(i, i % (size/3) + 1);
        }
        return ca;
    }

    @Test
    public void setNewValue() {
        CharactersAccumulator ca = createTestAccumulator(10);
        char testLetter = 'A';
        ca.setNewCurrent(testLetter);
        Assert.assertTrue("Letter inserted",
                ca.getCurrentValue().isPresent() && ca.getCurrentValue().get().equals(testLetter));
        Assert.assertTrue("Accumulation started", ca.getCurrentAccumulation() == 1);
    }

    @Test
    public void indexIncrement(){
        int size = 10;
        CharactersAccumulator ca = createTestAccumulator(size);
        for (int i = 0 ; i < size + 2 ; i++){
            int lastIndex =  ca.getCurrentIndex();
            ca.nextIndex();
            Assert.assertTrue("Index increment",
                    lastIndex + 1 == ca.getCurrentIndex() || ca.getCurrentIndex() == 0);
        }
    }

    @Test
    public void incrementAccum() {
        int size = 10;
        CharactersAccumulator ca = createInitializedAccumulator(size);
        int current = ca.getCurrentAccumulation();
        ca.incrementCurrentAccumulation();
        Assert.assertTrue("Incremented accumulation", current + 1 == ca.getCurrentAccumulation());
    }

}