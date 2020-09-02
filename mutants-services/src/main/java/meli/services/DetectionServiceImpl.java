package meli.services;

import meli.exceptions.InvalidDna;
import meli.interfaces.DetectionService;
import meli.models.Constants;
import meli.models.DnaAccumulator;
import org.springframework.stereotype.Service;
import java.util.Set;

/**
 * The service responsible of managing the logic of detection
 */
@Service
public class DetectionServiceImpl implements DetectionService {

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
}