package lt.itakademija.electors.party;

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

<<<<<<< HEAD
	public PartyEntity getById(Long id) {
		return em.find(PartyEntity.class, id);
	}
=======
    public PartyEntity getById(Long id) {
        return em.find(PartyEntity.class,id);
    }
>>>>>>> 3e06cced98022a8b331b3cea0cf9691b7f667ddf
}
