package lt.itakademija.electors.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Repository
public class CandidateRepository {

    @Autowired
    EntityManager em;

    public CandidateEntity save(CandidateEntity candidateEntity) {
        if (candidateEntity.getId() == null) {
            em.persist(candidateEntity);
        }
        em.merge(candidateEntity);
        return candidateEntity;
    }

    public List<CandidateEntity> getCandidatesList() {
        List list = em
                .createQuery("SELECT p FROM CandidateEntity p")
                .getResultList();
        return list;
    }

    public boolean delete(Long id) {
        CandidateEntity candidate = em.find(CandidateEntity.class, id);
        em.remove(candidate);
        return true;
    }
}
