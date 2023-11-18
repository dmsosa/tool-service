package vuttr.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vuttr.controller.exception.user.UserExistsException;
import vuttr.controller.exception.user.UserNotFoundException;
import vuttr.domain.user.User;

@Service
public interface UserService {
    UserDetails findByUsername(String username) throws UserNotFoundException;
    UserDetails findByEmail(String email) throws UserNotFoundException;
    UserDetails findByLogin(String login) throws UserNotFoundException; //Searchs both by Username and Email
    UserDetails createUser(User user) throws UserExistsException;
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
