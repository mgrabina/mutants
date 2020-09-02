package meli.models;

import java.util.Optional;

/**
 * An adaptation of the {@link CharactersAccumulator} that includes a type (that is needed by logic)
 * and an accumulator of valid sequences.
 * Also contains an aux in order to save information allowing to shift elements without loosing information (diagonals).
 */
public class DnaAccumulator extends CharactersAccumulator{

    /**
     * The quantity of sequences that corresponds to a mutant
     */
    int sequencesCount = 0;

    /**
     * The type of the accumulator
     */
    TYPE type;

    /**
     * An aux variable to save a char in order to use it in the next iteration
     */
    char aux;

    /**
     * An aux variable in order to save the accumulation integer of the corresponding aux char.
     */
    int auxAccum;


    public DnaAccumulator(Character[] values, int[] accumulator, TYPE type) {
        super(values, accumulator);
        this.type = type;
    }


    public void saveAux(){
        this.aux = this.values[currentIndex];
        this.auxAccum = this.accumulator[currentIndex];
    }

    public void cleanAux(){
        this.aux = '0';
        this.auxAccum = 0;
    }


    public char getAux() {
        return aux;
    }

    public int getAuxAccum() {
        return auxAccum;
    }

    public void incrementCounter(){
        this.sequencesCount++;
    }

    public int getSequencesCount() {
        return sequencesCount;
    }

    public TYPE getType() {
        return type;
    }

    public enum TYPE {
        HORIZONTAL,
        VERTICAL,
        D1,
        D2
    }
}