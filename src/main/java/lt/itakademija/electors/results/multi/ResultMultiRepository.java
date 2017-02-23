package lt.itakademija.electors.results.multi;

import lt.itakademija.electors.candidate.CandidateEntity;
import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.party.PartyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Pavel on 2017-02-03.
 */
@Repository
public class ResultMultiRepository {

    @Autowired
    EntityManager em;

    public void save(ResultMultiEntity res) {
        if (res.getId() == null){
            em.persist(res);
        } else {
            em.merge(res);
        }
    }

    public List<ResultMultiEntity> getResultsByDistrictId(DistrictEntity district) {
        List<ResultMultiEntity> list = em
                .createQuery("SELECT r FROM ResultMultiEntity r WHERE r.district = :rDistrict")
                .setParameter("rDistrict", district)
                .getResultList();
        return list;
    }

    public void delete(ResultMultiEntity res) {
        em.remove(res);
    }

    public List<ResultMultiEntity> findAll() {
        List list = em
                .createQuery("SELECT r FROM ResultMultiEntity r")
                .getResultList();
        return list;
    }

    public List<ResultMultiEntity> findByParty(PartyEntity party) {
        List<ResultMultiEntity> list = em.createQuery("SELECT v FROM ResultMultiEntity v WHERE v.party = :party")
                .setParameter("party", party)
                .getResultList();
        return list;
    }
}
