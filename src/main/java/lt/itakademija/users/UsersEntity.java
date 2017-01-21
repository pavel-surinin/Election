package lt.itakademija.users;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Pavel on 2017-01-10.
 */
@Entity
@Table(name = "users")
public class UsersEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull
    private String username;

    @Column(unique = true)
    @NotNull
    private String password;

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
        this.password = password;
    }
}
