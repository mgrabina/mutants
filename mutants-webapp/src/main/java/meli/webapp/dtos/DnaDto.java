package meli.webapp.dtos;

/**
 * Dto in order to recieve the dna information.
 * Can be filled with more information in the future.
 */
public class DnaDto {

    /**
     * The dna sequence itself.
     */
    private String[] dna;

    // Default constructor for Jersey
    public DnaDto(){}

    public DnaDto(String[] dna) {
        this.dna = dna;
    }

    public String[] getDnaCode() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
