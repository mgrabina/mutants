package meli.services;

import meli.exceptions.InvalidDna;
import meli.interfaces.DetectionService;
import meli.interfaces.StatisticsDao;
import meli.models.Constants;
import meli.models.DnaAccumulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * The service responsible of managing the logic of detection
 */
@Service
public class DetectionServiceImpl implements DetectionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetectionServiceImpl.class);

    @Autowired
    private StatisticsDao statisticsDao;

    /**
     * This function provides a logic for determining whether a dna corresponds to a mutant or not, based on the
     * sequence {@param dna}.
     *
     * It is developed in order to minimize time complexity and uses custom DnaAccumulators for tracking those
     * repeated sequences needed.
     *
     * @param dna   An array of Strings that represents the dna of the subject.
     * @return      A {@link Boolean} indicating whether the input represents a mutant or not.
     */
    @Override
    public boolean isMutant(String[] dna) throws InvalidDna{
        if (!DnaUtils.isValidDna(dna)) return false;
        int dnaLength = dna.length, mutantSequences = 0;
        Set<DnaAccumulator> DnaAccumulators = DnaUtils.generateAccumulators(dnaLength);

        // Iterate over each letter only once (NxN)
        for (String s : dna) {
            char[] str = s.toCharArray();
            for (int j = 0; j < dnaLength; j++) {
                // Update DnaAccumulators with a new letter and check if the minimum has been reached
                final char letter = DnaUtils.getAndValidateLetter(str, j);
                final boolean isLast = (j == dnaLength - 1);
                DnaAccumulators.parallelStream().forEach(a -> DnaUtils.updateDnaAccumulator(a, letter, isLast));
                if (DnaUtils.calculateMutantSequences(DnaAccumulators) >= Constants.MINIMUM_SEQUENCE_TO_BE_MUTANT) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Both checks if it is mutant but also saves the information in the database for later analysis
     * @param dna       The dna code to analyse
     * @return          A {@link Boolean} indicating if the dna represents a Mutant or not
     */
    @Override
    public boolean checkIfMutantAndSave(String[] dna) {
        Boolean isMutant = isMutant(dna);
        saveDna(dna, isMutant);
        return isMutant;
    }

    /**
     * Sends the information to the database asynchronously in order to not to stop the code execution and
     * respond as quick as possible, since the saving is only for analysis purposes.
     *
     * @param dna       The analyzed dna
     * @param isMutant  The result of the analysis
     */
    private void saveDna(String[] dna, Boolean isMutant){
        new Thread(new Runnable() {
            public void run() {
                try {
                    if(!statisticsDao.findByCode(dna).isPresent()){
                        LOGGER.debug("Saving new dna to the database: {}", Arrays.toString(dna));
                        statisticsDao.registerNewDna(dna, isMutant, dna.length);
                    }
                } catch (Exception e) {
                    LOGGER.error("There was an error saving the dna: {}, the exception was: {}",
                            Arrays.toString(dna), e.getMessage());
                }
            }
        }).start();
    }
}