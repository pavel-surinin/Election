package lt.itakademija.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Pavel on 2017-01-10.
 */
@Repository
public class UsersRepository {

    @Autowired
    EntityManager em;

    @Transactional
    public List<UsersEntity> checkUserLoginPassword(UsersEntity user) {
        return
                em.createQuery("SELECT u FROM UsersEntity u WHERE u.username = :lg AND u.password =:pw")
                        .setParameter("lg", user.getUsername())
                        .setParameter("pw", user.getPassword())
                        .getResultList();
    }
}
