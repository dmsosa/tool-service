package vuttr.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vuttr.controller.exception.user.UserExistsException;
import vuttr.controller.exception.user.UserNotFoundException;
import vuttr.domain.user.User;
import vuttr.repository.UserRepository;
import vuttr.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails findByUsername(String username) throws UserNotFoundException {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new UserNotFoundException(username);
        }
        return userRepository.findByUsername(username).get();
    }
    @Override
    public UserDetails findByEmail(String email) throws UserNotFoundException {
        if (userRepository.findByUsername(email).isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return userRepository.findByUsername(email).get();
    }

    @Override
    public UserDetails findByLogin(String login) throws UserNotFoundException  {
        if (!userRepository.existsByUsername(login) && !userRepository.existsByEmail(login)) {
            throw new UserNotFoundException(login);
        }
        if (userRepository.findByUsername(login).isPresent()) {
            return userRepository.findByUsername(login).get();
        } else {
            return userRepository.findByEmail(login).get();
        }

    }

    @Override
    public UserDetails createUser(User user) throws UserExistsException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserExistsException(user.getUsername());
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserExistsException(user.getEmail());
        } return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
