package meli.services;

import meli.exceptions.InvalidDna;
import meli.models.DnaAccumulator;
import meli.models.Constants;

import java.util.HashSet;
import java.util.Set;


/**
 * A Utils class for modularization of the
 */
public abstract class DnaUtils {

    /**
     * A helper function that generates a set including one accumulator of each {@link DnaAccumulator.TYPE}
     *
     * @param dnaLength     The length of the dna's sequences parts.
     * @return              A {@link Set} of {@link DnaAccumulator}s
     */
    static Set<DnaAccumulator> generateAccumulators(int dnaLength){
        return new HashSet<DnaAccumulator>(){{
            add(new DnaAccumulator(new Character[1], new int[1], DnaAccumulator.TYPE.HORIZONTAL));
            add(new DnaAccumulator(new Character[dnaLength], new int[dnaLength], DnaAccumulator.TYPE.VERTICAL));
            add(new DnaAccumulator(new Character[dnaLength], new int[dnaLength], DnaAccumulator.TYPE.D1));
            add(new DnaAccumulator(new Character[dnaLength], new int[dnaLength], DnaAccumulator.TYPE.D2));
        }};
    }

    /**
     * Helper function that sums the sequences of all the given DnaAccumulators
     *
     * @param DnaAccumulators      A {@link Set} of DnaAccumulators
     * @return                  A {@link Integer} representing the sum of the valid sequences of all DnaAccumulators.
     */
    static int calculateMutantSequences(Set<DnaAccumulator> DnaAccumulators){
        return DnaAccumulators.parallelStream().map(DnaAccumulator::getSequencesCount).reduce(0, Integer::sum);
    }

    /**
     * A helper function that updates the given DnaAccumulator with a new letter. This process is different depending
     * on the {@link DnaAccumulator.TYPE} of the {@link DnaAccumulator}, because that is what gives it the logic to search
     * for repetition sequences in the corresponding direction.
     *
     * E.g. Horizontal needs to save only one letter and checks with the letter on the right side.
     * While Vertical needs to be an array (of same size, N), in order to compare each saved letter
     * with the one above it, and diagonal needs to compare diagonally which is represented as the
     * previous or the next letter in the array (depending on which diagonal).
     *
     * @param a             The {@link DnaAccumulator} that is going to be updated.
     * @param letter        The char representing the next letter
     * @param isLast        A {@link Boolean} indicating if the next letter is the last of the row, to avoid borders.
     */
    public static void updateDnaAccumulator(DnaAccumulator a, char letter, boolean isLast){
        switch (a.getType()){
            case HORIZONTAL: // Working with only one character (comparing with the letter on the left side)
            case VERTICAL:   // Working with an array of N (comparing each letter with up side's letter)
                if (a.getCurrentValue().isPresent() && a.getCurrentValue().get().equals(letter)){
                    a.incrementCurrentAccumulation();
                    if (a.getCurrentAccumulation() == Constants.MINIMUM_IDENTICAL_BASE_COUNT){
                        a.incrementCounter();
                    }
                } else {
                    a.setCurrentValue(letter);
                    a.setAccumulation(a.getCurrentIndex(), 1);
                }
                if (isLast && a.getType() == DnaAccumulator.TYPE.HORIZONTAL){
                    //Avoid circular accumulations
                    a.setCurrentValue('0');
                    a.setAccumulation(a.getCurrentIndex(), 0);
                }
                break;
            case D1:    // From the left to the right: Inserts in the first place and 'shifts' to the right
                char lastAux = a.getAux();
                int lastAuxAccum = a.getAuxAccum();
                if (isLast){
                    a.cleanAux();
                } else if (a.getCurrentValue().isPresent()){
                    a.saveAux();
                }
                if (lastAux == letter && a.getCurrentValue().isPresent()){
                    a.setCurrentValue(lastAux);
                    a.setAccumulation(a.getCurrentIndex(), lastAuxAccum + 1);
                    if (a.getCurrentAccumulation() == Constants.MINIMUM_IDENTICAL_BASE_COUNT){
                        a.incrementCounter();
                    }
                } else {
                    a.setNewCurrent(letter);
                }
                break;
            case D2:    // From the right to the left: Inserts in the last place and 'shifts' to the left
                a.setNewCurrent(letter);
                if (!isLast && a.getValue(a.getCurrentIndex() + 1).isPresent() && a.getValue(a.getCurrentIndex() + 1).get().equals(letter)){
                    a.setAccumulation(a.getCurrentIndex(), a.getAccumulation(a.getCurrentIndex() + 1) + 1);
                }
                if (a.getCurrentAccumulation() == Constants.MINIMUM_IDENTICAL_BASE_COUNT) {
                    a.incrementCounter();
                }
                break;
        }
        a.nextIndex();
    }

    //////////////////////////////////////////////////////////////////////////////
    ////////                    Validations                             //////////
    //////////////////////////////////////////////////////////////////////////////

    /**
     * Helper function that validates the given dna
     *
     * @param dna           An array of {@link String} representing the dna
     * @throws InvalidDna   If the input is invalid and it is not possible to be processed
     * @return              A {@link Boolean} indicating if the dna is valid to be processed
     */
    static boolean isValidDna(String[] dna) throws InvalidDna{
        if (dna == null) throw new InvalidDna("The dna is null.");
        for (String str : dna)
            if (dna.length != str.length()) throw new InvalidDna("The size is not squared NxN.");
        if (dna.length < Constants.MINIMUM_IDENTICAL_BASE_COUNT) return false;
        return true;
    }

    /**
     * Gets if valid (based on valid dna letters) the letter in the index given for a str given
     *
     * @param str       The str that contains the letter/
     * @param index     The index of the letter
     * @return          A char with the requested letter.
     * @throws InvalidDna       If the letter is not in the authorized letters set
     */
    static char getAndValidateLetter(char[] str, int index) throws InvalidDna {
        final char letter = str[index];
        if (!Constants.validBases.contains(letter))
            throw new InvalidDna("Invalid base letter.");
        return letter;
    }
}