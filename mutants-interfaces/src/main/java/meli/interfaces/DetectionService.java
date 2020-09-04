package meli.interfaces;

/**
 * The Detection Service interface including the information of all detection methods.
 */
public interface DetectionService  {
    boolean isMutant(String[] dna);
    boolean checkIfMutantAndSave(String[] dna);
}