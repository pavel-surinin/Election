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

    public CandidateEntity finById(Long id){
        return em.find(CandidateEntity.class, id);
    }

    public CandidateEntity findByNumberInPartyNameSurnameParty(CandidateEntity can) {
        List<CandidateEntity> list = em.createQuery("SELECT c FROM CandidateEntity c WHERE c.name = :cName AND c.surname" +
                " =:cSurname AND c.numberInParty =:cNum AND c.partyDependencies =:cPar")
                .setParameter("cName", can.getName())
                .setParameter("cSurname", can.getSurname())
                .setParameter("cNum", can.getNumberInParty())
                .setParameter("cPar", can.getPartyDependencies())
                .getResultList();
        if (list.size() != 0) {return list.get(0);}
        return null;
    }

    public Long getCandidatesCount(){
        return em.createQuery("SELECT COUNT(c) FROM CandidateEntity c", Long.class).getSingleResult();
    }
}
