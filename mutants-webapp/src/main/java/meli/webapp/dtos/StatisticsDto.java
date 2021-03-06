package meli.webapp.dtos;

import meli.models.Statistics;

/**
 * Dto for modeling the statistics to send
 */
public class StatisticsDto {

    /**
     * The quantity of mutants analyzed
     */
    private long count_mutant_dna;

    /**
     * The quantity of humans analyzed
     */
    private long count_human_dna;

    /**
     * The ratio of mutants/total analyzed
     */
    private double ratio;

    public StatisticsDto(Statistics stats){
        this.count_human_dna = stats.getNonMutantsQuantity();
        this.count_mutant_dna = stats.getMutantsQuantity();
        this.ratio = stats.getRatio();
    }

    public long getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public void setCount_mutant_dna(long count_mutant_dna) {
        this.count_mutant_dna = count_mutant_dna;
    }

    public long getCount_human_dna() {
        return count_human_dna;
    }

    public void setCount_human_dna(long count_human_dna) {
        this.count_human_dna = count_human_dna;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
