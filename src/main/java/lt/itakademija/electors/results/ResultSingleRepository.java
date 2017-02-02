package lt.itakademija.electors.results;

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

    public void deleteById(Long id){
        ResultSingleEntity result = em.find(ResultSingleEntity.class, id);
        em.remove(result);
    }
}
