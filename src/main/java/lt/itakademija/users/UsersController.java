package lt.itakademija.users;

import lt.itakademija.electors.representative.DistrictRepresentativeService;
import lt.itakademija.exceptions.BadCredentialsEnteredException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * Created by Pavel on 2017-01-10.
 */
@RestController
public class UsersController {

    @Autowired
    DistrictRepresentativeService representativeService;

    @Autowired
    UsersService service;

    @PostMapping("/search")
    public List seacrh(@RequestParam("searchFor") String string) {
        return service.search(string);
    }

    @PostMapping("/login/check")
    public Object getUserLogged() {
        if (isRole("ADMIN")) {
            return "admin";
        }
        if (isRole("REPRESENTATIVE")) {
            Long byName = representativeService.findByName(getContext().getAuthentication().getName());
            if (byName == 0L) {
                return "none";
            } else {
                return "representative";
            }
        }
        return false;
    }

    private boolean isRole(String role) {
        return getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
                .contains(role);
    }

    @GetMapping("/login")
    public void login(@RequestParam("logout") String aaa) {
    }

    @GetMapping("/users/logged")
    public String whoIsLogged() {
        return getContext().getAuthentication().getName();
    }

    @GetMapping("/users/logged/district")
    public int whoIsLoggedDistrict() {
        return service.checkWhoIsDistrict();
    }

    @PostMapping("/users/login")
    public String login(@RequestBody UsersEntity user) {
        return service.login(user);
    }

    @GetMapping("users/admin-info")
    public List<Long> getAdminPanelInfo() {
        return service.getAdminPanelInfo();
    }

    @PostMapping("/bad-credentials")
    public void login() {
        throw new BadCredentialsEnteredException("Bad credentials entered");
    }
}
