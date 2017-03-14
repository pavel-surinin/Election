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

    public DistrictEntity findById(Long id){
        return em.find(DistrictEntity.class, id);
    }

    public List<DistrictEntity> findByNameAndAdress(DistrictEntity apylinke) {
        return
                em.createQuery("SELECT a FROM DistrictEntity a WHERE a.name = :an AND a.county =:ac")
                        .setParameter("an", apylinke.getName())
                        .setParameter("ac", apylinke.getCounty())
                        .getResultList();
    }

    public Long getDistrictsCount(){
        return em.createQuery("SELECT COUNT(c) FROM DistrictEntity c", Long.class).getSingleResult();
    }

    public List<DistrictEntity> getByFirstLetter(String letter) {
        return
                em.createQuery("SELECT a FROM DistrictEntity a WHERE a.name LIKE CONCAT(:letter, '%') OR  a.name LIKE CONCAT(:letterL, '%')")
                        .setParameter("letter", letter)
                        .setParameter("letterL", letter.toLowerCase())
                        .getResultList();
    }

    public List<DistrictEntity> search(String string) {
        String lowerCase = string.toLowerCase();
        String firstUpper = string.substring(0,1).toUpperCase() + string.substring(1,string.length()).toLowerCase();
        return
                em.createQuery("SELECT a FROM DistrictEntity a WHERE a.name LIKE CONCAT('%', :string, '%') OR a.name LIKE CONCAT('%', :stringU, '%')")
                        .setParameter("string", lowerCase)
                        .setParameter("stringU", firstUpper)
                        .getResultList();
    }
}
