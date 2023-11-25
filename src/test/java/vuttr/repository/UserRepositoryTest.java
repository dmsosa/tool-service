package vuttr.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import vuttr.domain.user.User;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles(value = "test")
public class UserRepositoryTest {
    Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Database populated")
    void databaseIsNotEmpty() {
        Assertions.assertThat(userRepository.findAll().isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Find User When Exists")
    void shouldUserExists() {
        Optional<UserDetails> foundUser = userRepository.findByUsername("Carlos");
        User user = (User) foundUser.get();
        logger.info(user.getUsername() + user.getBio());
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("User do not exists")
    void whenUserDoesNotExists_thenReturnNull() {
        Optional<UserDetails> foundUser = userRepository.findByUsername("Idontexist");
        Assertions.assertThat(foundUser.isEmpty()).isTrue();
    }
}
