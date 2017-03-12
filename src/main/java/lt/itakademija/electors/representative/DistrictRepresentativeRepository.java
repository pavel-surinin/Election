package lt.itakademija.electors.representative;

import lt.itakademija.users.UsersEntity;
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

    public Integer getIdByName(String name) {
        List<UsersEntity> reps = em.createQuery("SELECT c FROM UsersEntity c WHERE c.username = :name")
                .setParameter("name", name).getResultList();
        if (reps.size() == 0){
            return 0;
        } else {
            return reps.get(0).getDistrictId();
        }
    }

    public List<DistrictRepresentativeEntity> search(String string) {
        String lowerCase = string.toLowerCase();
        String firstUpper = string.substring(0,1).toUpperCase() + string.substring(1,string.length()).toLowerCase();
        return
                em.createQuery("SELECT a FROM DistrictRepresentativeEntity a" +
                        " WHERE a.name LIKE CONCAT('%', :string, '%') " +
                        "OR a.name LIKE CONCAT('%', :stringU, '%') " +
                        "OR a.surname LIKE CONCAT('%', :string, '%') " +
                        "OR a.surname LIKE CONCAT('%', :stringU, '%')")
                        .setParameter("string", lowerCase)
                        .setParameter("stringU", firstUpper)
                        .getResultList();
    }
}
