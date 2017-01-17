package lt.itakademija.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Pavel on 2017-01-10.
 */
@RestController
public class UsersController {

    @Autowired
    UsersService service;

    @GetMapping("/users/logged")
    public String whoIsLogged(){
        return service.checkWhoIsLogged();
    }

    @PostMapping("/users/login")
    public boolean login(@RequestBody UsersEntity user){
        return service.login(user);
    }

    @GetMapping("/users/logout")
    public void logout(){
        service.logout();
    }

}
