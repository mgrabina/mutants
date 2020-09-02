package meli.services;

import meli.interfaces.DetectionService;
import meli.models.Accumulator;
import meli.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A sample service
 */
@Service
public class DetectionServiceImpl implements DetectionService {

    private static final int MINIMUM_SEQUENCE_TO_BE_MUTANT = 2;
    private static final int MINIMUM_IDENTICAL_BASE_COUNT = 4;


    @Override
    public boolean isMutant(String[] dna) {
        int dnaLength = dna.length, mutantSequences = 0;
        Set<Accumulator> accumulators = new HashSet<Accumulator>(){{
            add(new Accumulator(new Character[1], new int[1], Accumulator.TYPE.HORIZONTAL));
            add(new Accumulator(new Character[dnaLength], new int[dnaLength], Accumulator.TYPE.VERTICAL));
            add(new Accumulator(new Character[dnaLength], new int[dnaLength], Accumulator.TYPE.D1));
            add(new Accumulator(new Character[dnaLength], new int[dnaLength], Accumulator.TYPE.D2));
        }};

        // Iterate over each letter only once (NxN)
        for (String s : dna) {
            char[] str = s.toCharArray();
            for (int j = 0; j < dnaLength; j++) {
                // Update accumulators and check if the minimum has been reached
                final char letter = str[j];
                final boolean isLast = (j == dnaLength - 1);
                accumulators.parallelStream().forEach(a -> updateAccumulator(a, letter, isLast));
                if (calculateMutantSequences(accumulators) > MINIMUM_SEQUENCE_TO_BE_MUTANT) {
                    return true;
                }
            }
        }
        return false;
    }

    private int calculateMutantSequences(Set<Accumulator> accumulators){
        return accumulators.parallelStream().map(Accumulator::getSequencesCount).reduce(0, Integer::sum);
    }

    public static void updateAccumulator(Accumulator a, char letter, boolean isLast){
        switch (a.getType()){
            case HORIZONTAL: // Working with only one character (comparing with the letter on the left side)
            case VERTICAL:   // Working with an array of N (comparing each letter with up side's letter)
                if (a.getCurrentValue().isPresent() && a.getCurrentValue().get().equals(letter)){
                    a.incrementCurrentAccumulation();
                    if (a.getCurrentAccumulation() == MINIMUM_IDENTICAL_BASE_COUNT){
                        a.incrementCounter();
                    }
                } else {
                    a.setCurrentValue(letter);
                    a.setAccumulation(a.getCurrentIndex(), 1);
                }
                if (isLast && a.getType() == Accumulator.TYPE.HORIZONTAL){
                    //Avoid circular accumulations
                    a.setCurrentValue('0');
                    a.setAccumulation(a.getCurrentIndex(), 0);
                }
                break;
            case D1:
                char lastAux = a.getAux();
                int lastAuxAccum = a.getAuxAccum();
                if (isLast){
                    a.cleanAux();
                } else if (a.getCurrentValue().isPresent()){
                    a.saveAux();
                }
                if (a.getAux() == letter && a.getCurrentValue().isPresent()){
                    a.setCurrentValue(lastAux);
                    a.setAccumulation(a.getCurrentIndex(), lastAuxAccum + 1);
                    if (a.getCurrentAccumulation() == MINIMUM_IDENTICAL_BASE_COUNT){
                        a.incrementCounter();
                    }
                } else {
                    a.setNewCurrent(letter);
                }
                break;
            case D2:
                if (isLast || (a.getValue(a.getCurrentIndex() + 1).isPresent() && !a.getValue(a.getCurrentIndex() + 1).equals(letter))) {
                    a.setNewCurrent(letter);
                } else {
                    a.incrementCurrentAccumulation();
                    if (a.getCurrentAccumulation() == MINIMUM_IDENTICAL_BASE_COUNT) {
                        a.incrementCounter();
                    }
                }
            break;
        }
        a.nextIndex();
    }
}