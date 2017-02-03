package lt.itakademija.electors.results.multi;

import lt.itakademija.electors.district.DistrictEntity;
import lt.itakademija.electors.results.single.ResultSingleEntity;
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

    public List<ResultMultiEntity> getByDistrictId(DistrictEntity district) {
        List<ResultMultiEntity> list = em
                .createQuery("SELECT r FROM ResultMultiEntity r WHERE r.district = :rDistrict")
                .setParameter("rDistrict", district)
                .getResultList();
        return list;
    }

    public void delete(ResultMultiEntity res) {
        em.remove(res);
    }
}
