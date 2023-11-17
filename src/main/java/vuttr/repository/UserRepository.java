package vuttr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import vuttr.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByUsername(String username);
    UserDetails findByEmail(String email);


}
