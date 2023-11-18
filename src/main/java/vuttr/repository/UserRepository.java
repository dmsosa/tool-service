package vuttr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import vuttr.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<UserDetails> findByUsername(String username);
    Optional<UserDetails> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
