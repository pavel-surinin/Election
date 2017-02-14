package lt.itakademija.electors.results.single;

import lt.itakademija.electors.district.DistrictEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Pavel on 2017-02-02.
 */
@Repository
public class ResultSingleRepository {

    @Autowired
    EntityManager em;

    public ResultSingleEntity save(ResultSingleEntity result) {
        if (result.getId() == null) {
            em.persist(result);
            return result;
        } else {
            return em.merge(result);
        }
    }

    public List<ResultSingleEntity> findAll() {
        List list = em
                .createQuery("SELECT r FROM ResultSingleEntity r")
                .getResultList();
        return list;
    }

    public void delete(ResultSingleEntity res){
        em.remove(res);
    }

    public List<ResultSingleEntity> getResultsByDistrictId(DistrictEntity district) {
        List<ResultSingleEntity> list = em
                .createQuery("SELECT r FROM ResultSingleEntity r WHERE r.district = :rDistrict")
                .setParameter("rDistrict", district)
                .getResultList();
        return list;
    }
}
