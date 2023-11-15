package vuttr.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vuttr.domain.user.User;

@Service
public interface UserService {
    UserDetails findByUsername(String username);
    UserDetails findByEmail(String email);
    UserDetails findByLogin(String login); //Searchs both by Username and Email
    void saveUser(User user);
}
