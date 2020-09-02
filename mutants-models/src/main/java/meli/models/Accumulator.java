package meli.models;

import java.util.Optional;

/**
 * A sample model
 */
public class Accumulator {

    Character[] values;
    int[] accumulator;
    int size;
    int sequencesCount = 0;
    int currentIndex = 0;
    TYPE type;
    char aux;
    int auxAccum;

    public Accumulator(Character[] values, int[] accumulator, TYPE type) {
        this.values = values;
        this.accumulator = accumulator;
        this.size = values.length;
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


    public void setNewCurrent(char c){
        values[currentIndex] = c;
        accumulator[currentIndex] = 1;
    }

    public char getAux() {
        return aux;
    }

    public void setAux(char aux) {
        this.aux = aux;
    }

    public int getAuxAccum() {
        return auxAccum;
    }

    public void setAuxAccum(int auxAccum) {
        this.auxAccum = auxAccum;
    }

    public void setValue(int index, char c){
        values[index] = c;
    }

    public void setCurrentValue(char c){
        values[currentIndex] = c;
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
            this.currentIndex = 0;
            return;
        }
        this.currentIndex++;
    }

    public int getCurrentIndex() {
        return currentIndex;
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