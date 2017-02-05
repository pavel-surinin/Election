package lt.itakademija.electors.county;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Pavel on 2017-01-12.
 */
@Repository
public class CountyRepository {

    @Autowired
    EntityManager em;

    public List<CountyEntity> findAll() {
        List list = em
                .createQuery("SELECT a FROM CountyEntity a")
                .getResultList();
        return list;
    }

    public CountyEntity save(CountyEntity county) {
        if (county.getId() == null) {
            em.persist(county);
        }
        em.merge(county);
        return county;
    }

    public void delete(Long id) {
        CountyEntity county = em.find(CountyEntity.class, id);
        em.remove(county);
    }

    public CountyEntity findById(Long id) {
        return em.find(CountyEntity.class, id);
    }
}
