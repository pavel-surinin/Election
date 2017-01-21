package lt.itakademija.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void saveUser(UsersEntity user){
        rep.save(user);
    }
}
