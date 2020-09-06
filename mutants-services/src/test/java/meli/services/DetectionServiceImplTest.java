package meli.services;

import meli.exceptions.InvalidDna;
import meli.interfaces.DetectionService;
import meli.models.DnaAccumulator;
import meli.test_config.ServicesTestConfig;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Test;
import org.junit.Assert;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Testing for the detection service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServicesTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class DetectionServiceImplTest {

    @Autowired
    private DetectionService detectionService;


    //////////////////////////////////////////////////////////////////////////////
    ////////                    Classic Examples (given)                //////////
    //////////////////////////////////////////////////////////////////////////////

    @Test
    public void mutantExampleOneHorizontalOneVerticalAndOneDiagonal1() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        Assert.assertTrue("Should be mutant", detectionService.isMutant(dna));
    }

    @Test
    public void notMutantExample() {
        String[] dna = {"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
        Assert.assertFalse("Should not be mutant", detectionService.isMutant(dna));
    }

    //////////////////////////////////////////////////////////////////////////////
    ////////                    Border Examples                         //////////
    //////////////////////////////////////////////////////////////////////////////

    @Test
    public void crossedDiagonalSequences() {
        String[] dna = {"ACTCA","CATAC","GGACT","GACAT","ATGCA"};
        Assert.assertTrue("Should be mutant", detectionService.isMutant(dna));
    }

    @Test
    public void LikeACenteredPlusMutant() {
        String[] dna = {"AATAA", "AATAA", "TTTTT", "AATAA", "AATAA"};
        Assert.assertTrue("Should be mutant", detectionService.isMutant(dna));
    }

    @Test
    public void CenteredSimetricButNotEnough() {
        String[] dna = {"AATAA", "AATAA", "TTGTT", "AATAA", "AATAA"};
        Assert.assertFalse("Should not be mutant", detectionService.isMutant(dna));
    }

    @Test
    public void notEnoughDiagonalSequences() {
        String[] dna = {"ACTCA","CATAC","GGACT","GCCAA","TTGCC"};
        Assert.assertFalse("Should not be mutant", detectionService.isMutant(dna));
    }

    @Test
    public void onlyOneSequenceNotMutant() {
        String[] dna = {"AAAA","CTGT","TGCT","CCTG"};
        Assert.assertFalse("Should not be mutant", detectionService.isMutant(dna));
    }

    @Test
    public void bordersOfTheSquareIsMutant() {
        String[] dna = {"AAAA","ACTA","AGTA","AAAA"};
        Assert.assertTrue("Should be mutant", detectionService.isMutant(dna));
    }

    //////////////////////////////////////////////////////////////////////////////
    ////////                    Validations                             //////////
    //////////////////////////////////////////////////////////////////////////////

    @Test
    public void invalidBase() {
        String[] dna = {"AAAA","ABAA","AAAA", "AAAA"};
        try {
            detectionService.isMutant(dna);
            Assert.assertTrue("This should be avoided by an exception.", false);
        } catch (InvalidDna e){
            Assert.assertTrue("Invalid Base", e.getMessage().contains("base"));
        }
    }

    @Test
    public void nullInput() {
        String[] dna = null;
        try {
            detectionService.isMutant(dna);
            Assert.assertTrue("This should be avoided by an exception.", false);
        } catch (InvalidDna e){
            Assert.assertTrue("Null input", e.getMessage().contains("null"));
        }
    }

    @Test
    public void notSquared() {
        String[] dna = {"AAA","AAA"};
        try {
            detectionService.isMutant(dna);
            Assert.assertTrue("This should be avoided by an exception.", false);
        } catch (InvalidDna e){
            Assert.assertTrue("Not squared input", e.getMessage().contains("squared"));
        }
    }

    @Test
    public void notEnoughDna() {
        String[] dna = {"AAA","AAA","AAA"};
        Assert.assertFalse("Should not be mutant", detectionService.isMutant(dna));
    }

}