package lt.itakademija.electors.representative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Pavel on 2017-01-16.
 */
@Repository
public class DistrictRepresentativeRepository {

    @Autowired
    EntityManager em;

    public List<DistrictRepresentativeEntity> findAll() {
        List list = em
                .createQuery("SELECT a FROM DistrictRepresentativeEntity a")
                .getResultList();
        return list;
    }

    public DistrictRepresentativeEntity save(DistrictRepresentativeEntity representative) {
        if (representative.getId() == null) {
            em.persist(representative);
        }
        em.merge(representative);
        return representative;
    }

    public Long getDistrictRepsCount(){
        return em.createQuery("SELECT COUNT(c) FROM DistrictRepresentativeEntity c", Long.class).getSingleResult();
    }
}
