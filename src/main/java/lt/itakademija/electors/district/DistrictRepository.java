package lt.itakademija.electors.district;

import lt.itakademija.electors.county.CountyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Pavel on 2017-01-14.
 */
@Repository
public class DistrictRepository {

    @Autowired
    EntityManager em;

    public List<DistrictEntity> findAll() {
        List list = em
                .createQuery("SELECT a FROM DistrictEntity a")
                .getResultList();
        return list;
    }


    public DistrictEntity save(DistrictEntity district) {
        if (district.getId() == null) {
            em.persist(district);
        }
        em.merge(district);
        return district;
    }

    public void delete(Long id) {
        DistrictEntity district = em.find(DistrictEntity.class, id);
        em.remove(district);
    }
}
