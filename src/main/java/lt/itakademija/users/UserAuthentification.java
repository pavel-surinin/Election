package lt.itakademija.users;

import org.springframework.stereotype.Service;

/**
 * Created by Pavel on 2017-01-10.
 */
@Service
public class UserAuthentification {

    private String userLogged = "none";

    public String getUserLogged() {
        return userLogged;
    }

    public void setUserLogged(String userLogged) {
        this.userLogged = userLogged;
    }
}
