package lt.itakademija.electors.party;

import lt.itakademija.electors.candidate.CandidateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Repository
public class PartyRepository {

    @Autowired
    EntityManager em;

    public PartyEntity save(PartyEntity party) {
        if (party.getId() == null) {
            em.persist(party);
        }
        em.merge(party);
        return party;
    }

    public List<PartyEntity> findAll() {
        List list = em
                   .createQuery("SELECT p FROM PartyEntity p")
                   .getResultList();
        return list;
    }

	public PartyEntity getById(Long id) {
		return em.find(PartyEntity.class, id);
	}

	public void detach(PartyEntity partyEntity) {
        em.detach(partyEntity);
    }

    public PartyEntity getPartyByNumber(Integer number) {
        List<PartyEntity> list = em.createQuery("SELECT p FROM PartyEntity p WHERE p.partyNumber = :pNum")
                .setParameter("pNum", number)
                .getResultList();
        if (list.size() != 0) {
            list.stream().forEach(partyEntity -> System.out.println("-" + partyEntity.getName()));
            return list.get(0);
        }
        return null;
    }

    public void delete(Long id) {
        em.remove(em.find(PartyEntity.class, id));
    }
}
