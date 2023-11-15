package vuttr.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Table(name = "user")
@Entity
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String login;
    private String email;
    private String password;
    private String bio;
    private UserRole role;


    public User(RegisterDTO registerDTO, String encryptedPassword) {
        this.login = registerDTO.username();
        this.email = registerDTO.email();
        this.password = encryptedPassword;
        this.role = registerDTO.role();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.BASIC) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_BASIC"));
        } else if (this.role == UserRole.BASIC) {
            return List.of(new SimpleGrantedAuthority("ROLE_BASIC"));
        }
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
