package lt.itakademija.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.config.Elements.PASSWORD_ENCODER;

/**
 * Created by Pavel on 2017-01-10.
 */
@Entity
@Table(name = "users")
public class UsersEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    private int districtId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRoles> roles;

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public List<String> getRolesStrings() {
        return roles.stream().map(r -> r.getName()).collect(Collectors.toList());
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
        this.password = PASSWORD_ENCODER.encode(password);
    }
}
