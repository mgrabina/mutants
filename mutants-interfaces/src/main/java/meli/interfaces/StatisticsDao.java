package meli.interfaces;

import meli.models.DnaRegistry;

import java.util.Optional;

public interface StatisticsDao {

    void registerNewDna(String[] dna, Boolean isMutant, int length);
    Optional<DnaRegistry> findByCode(String[] dna);
    void removeByCode(String[] dna);
    long countByIfMutants(Boolean isMutant);
}
