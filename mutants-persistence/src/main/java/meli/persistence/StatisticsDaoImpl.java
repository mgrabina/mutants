package meli.persistence;

import meli.interfaces.StatisticsDao;
import meli.models.DnaRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * A sample dao
 */
@Repository
public class StatisticsDaoImpl implements StatisticsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void registerNewDna(String[] dna, Boolean isMutant) {
        em.persist(new DnaRegistry(dna, isMutant));
    }

    @Override
    public long countByIfMutants(Boolean isMutant) {
        return em.createQuery("select count(dna) from DnaRegistry dna where dna.isMutant = :isMutant", long.class)
                .setParameter("isMutant", isMutant).getSingleResult();
    }

    @Override
    public Optional<DnaRegistry> findByCode(String[] dna) {
        return Optional.ofNullable(em.find(DnaRegistry.class, String.join("", dna)));
    }
}