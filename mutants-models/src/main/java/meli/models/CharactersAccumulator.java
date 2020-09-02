package meli.models;

import java.util.Optional;

/**
 * A custom accumulator that coordinates an array of Character and an array of Integers
 * where the accumulation of a character corresponds to the value in the same index in the integer's array.
 */
public class CharactersAccumulator {

    /**
     * The characters to be tracked
     */
    Character[] values;

    /**
     * The actual quantities of repeated characters, corresponding to 'values' array.
     */
    int[] accumulator;

    /**
     * The size of the arrays
     */
    int size;

    /**
     * The current position of the internal iterator
     */
    int currentIndex = 0;

    public CharactersAccumulator(Character[] values, int[] accumulator) {
        this.values = values;
        this.accumulator = accumulator;
        this.size = values.length;
    }

    public void setCurrentValue(char c){
        values[currentIndex] = c;
    }

    public void setNewCurrent(char c){
        values[currentIndex] = c;
        accumulator[currentIndex] = 1;
    }

    public Optional<Character> getValue(int index){
        return Optional.ofNullable(values[index]);
    }

    public Optional<Character> getCurrentValue(){
        return Optional.ofNullable(values[currentIndex]);
    }

    public void setAccumulation(int index, int acum){
        accumulator[index] = acum;
    }

    public void incrementCurrentAccumulation(){
        accumulator[currentIndex]++;
    }

    public int getAccumulation(int index){
        return accumulator[index];
    }

    public int getCurrentAccumulation(){
        return accumulator[currentIndex];
    }

    public void nextIndex(){
        if (this.currentIndex + 1 == size){
            // Avoid overflow
            this.currentIndex = 0;
            return;
        }
        this.currentIndex++;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}