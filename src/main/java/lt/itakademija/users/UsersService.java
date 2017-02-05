package lt.itakademija.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Pavel on 2017-01-10.
 */
@Service
public class UsersService {

    @Autowired
    UserAuthentification auth;

    @Autowired
    UsersRepository rep;

    public String checkWhoIsLogged() {
        return auth.getUserLogged();
    }

    public UsersEntity findUserByUsername(UsersEntity user){
        List<UsersEntity> userToFind = rep.findByUsername(user);
        if (userToFind == null) {
            return null;
        } else {
            return userToFind.iterator().next();
        }
    }

    public boolean login(UsersEntity user) {
        List<UsersEntity> resp = rep.checkUserLoginPassword(user);
        if (resp.size() == 0) {
            return false;
        } else {
            auth.setUserLogged(resp.get(0).getUsername());
            return true;
        }
    }

    public void logout() {
        auth.setUserLogged("none");
    }

    @Transactional
    public void saveUser(UsersEntity user){
        String hashword = Md5.generate(user.getPassword());
        user.setPassword(hashword);
        rep.save(user);
    }
}
