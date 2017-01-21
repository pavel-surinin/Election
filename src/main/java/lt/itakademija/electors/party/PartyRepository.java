package lt.itakademija.electors.party;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}
