package meli.interfaces;

import meli.models.DnaRegistry;

import java.util.Optional;

public interface StatisticsDao {

    void registerNewDna(String[] dna, Boolean isMutant);
    long countByIfMutants(Boolean isMutant);
    Optional<DnaRegistry> findByCode(String[] dna);
}
