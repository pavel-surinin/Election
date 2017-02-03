package lt.itakademija.electors.results.multi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
