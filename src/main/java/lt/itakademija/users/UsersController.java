package lt.itakademija.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/users/logged/district")
    public int whoIsLoggedDistrict(){
        return service.checkWhoIsDistrict();
    }

    @PostMapping("/users/login")
    public String login(@RequestBody UsersEntity user){
        return service.login(user);
    }

    @GetMapping("/users/logout")
    public void logout(){
        service.logout();
    }

    @GetMapping("users/admin-info")
    public List<Long> getAdminPanelInfo(){
        return service.getAdminPanelInfo();
    }

}
