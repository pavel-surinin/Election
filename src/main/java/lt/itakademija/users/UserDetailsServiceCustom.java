package lt.itakademija.users;

import lt.itakademija.exceptions.BadCredentialsEnteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pavel on 2017-03-03.
 */
@Service
public class UserDetailsServiceCustom implements UserDetailsService {
    @Autowired
    UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws BadCredentialsEnteredException {
        List<UsersEntity> byUsername = repository.findByUsername(s);
        if (!byUsername.isEmpty()) {
//            throw new BadCredentialsEnteredException("Bad Credentials entered for user " + s + " or bad password");
            UsersEntity usersEntity = byUsername.get(0);
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return new User(
                    usersEntity.getUsername(),
                    usersEntity.getPassword(),
                    AuthorityUtils.createAuthorityList(usersEntity.getRolesStrings().toArray(new String[usersEntity.getRolesStrings().size()]))
            );
        }
    return null;
    }
}
