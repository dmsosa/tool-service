package vuttr.security;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {SecurityFilter.class})
public class SecurityContextTest {
    @MockBean
    SecurityFilter securityFilter;

    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( authz -> authz
                        .requestMatchers(HttpMethod.GET, "/api/tools").authenticated()
                        .anyRequest().denyAll()
                )
                .build();
    }

}

