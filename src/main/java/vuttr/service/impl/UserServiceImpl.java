package vuttr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vuttr.domain.user.User;
import vuttr.repository.UserRepository;
import vuttr.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails findByUsername(String username) {
        return userRepository.findByLogin(username);
    }
    @Override
    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails findByLogin(String login) {
        UserDetails user = userRepository.findByLogin(login);
        if ( user == null) {
            user = userRepository.findByEmail(login);
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
