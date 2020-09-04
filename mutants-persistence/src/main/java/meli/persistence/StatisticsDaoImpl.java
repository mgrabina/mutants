package meli.persistence;

import meli.interfaces.StatisticsDao;
import meli.models.DnaRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Dao responsible of managing Statistics
 */
@Repository
public class StatisticsDaoImpl implements StatisticsDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * Registers a new dna in the database
     *
     * @param dna       The dna code analyzed
     * @param isMutant  The result of the analysis indicating if the dna represents a mutant or not
     */
    @Override
    @Transactional
    public void registerNewDna(String[] dna, Boolean isMutant, int length) {
        em.persist(new DnaRegistry(dna, isMutant, length));
    }

    /**
     * Finds a Registry by its dna code
     *
     * @param dna   The dna code to search
     * @return      An {@link Optional} having a {@link DnaRegistry} instance with the saved information.
     */
    @Override
    public Optional<DnaRegistry> findByCode(String[] dna) {
        return Optional.ofNullable(em.find(DnaRegistry.class, String.join("", dna)));
    }

    /**
     * Deletes from the database a registry by a given dna code
     *
     * @param dna       The dna code to delete
     */
    @Override
    public void removeByCode(String[] dna){
        Optional<DnaRegistry> dnaRegistry = findByCode(dna);
        if (!dnaRegistry.isPresent()){
            throw new NoSuchElementException();
        }
        em.remove(dnaRegistry.get());
        em.flush();
    }

    /**
     * Returns the quantity of mutants (or humans) are in the database at the moment
     *
     * @param isMutant      Indicates if it is supposed to search mutants or humans
     * @return      A long number indicating the requested quantity
     */
    @Override
    public long countByIfMutants(Boolean isMutant) {
        long count;
        try {
            count = em.createQuery("select count(dna) from DnaRegistry dna where dna.isMutant = :isMutant", Long.class)
                    .setParameter("isMutant", isMutant).getSingleResult();
        } catch (Exception e){
            // Nothing Found
            count = 0;
        }
        return count;
    }
}