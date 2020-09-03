package meli.models;

public class Statistics {

    private long mutantsQuantity;
    private long nonMutantsQuantity;
    private double ratio;

    public Statistics(long mutantsQuantity, long nonMutantsQuantity, double ratio) {
        this.mutantsQuantity = mutantsQuantity;
        this.nonMutantsQuantity = nonMutantsQuantity;
        this.ratio = ratio;
    }

    public long getMutantsQuantity() {
        return mutantsQuantity;
    }

    public void setMutantsQuantity(long mutantsQuantity) {
        this.mutantsQuantity = mutantsQuantity;
    }

    public long getNonMutantsQuantity() {
        return nonMutantsQuantity;
    }

    public void setNonMutantsQuantity(long nonMutantsQuantity) {
        this.nonMutantsQuantity = nonMutantsQuantity;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
