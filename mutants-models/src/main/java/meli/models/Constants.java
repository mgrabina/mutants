package meli.models;

import java.util.HashSet;

public abstract class Constants {
    /**
     * The minimum quantity of repeated letters sequences in order to be considered a mutant.
     */
    public static final int MINIMUM_SEQUENCE_TO_BE_MUTANT = 2;

    /**
     * The minimum quantity of letters in order to be considered a valid mutant sequence.
     */
    public static final int MINIMUM_IDENTICAL_BASE_COUNT = 4;

    /**
     * Valid Bases to be validated inside the dna
     */
    public static final HashSet<Character> validBases = new HashSet<Character>(){{ add('A'); add('T'); add('C'); add('G');}};
}
