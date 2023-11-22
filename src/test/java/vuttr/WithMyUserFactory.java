package vuttr;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import vuttr.controller.WithMyUser;
import vuttr.domain.user.User;
import vuttr.domain.user.UserRole;

public class WithMyUserFactory implements WithSecurityContextFactory<WithMyUser> {
    @Override
    public SecurityContext createSecurityContext(WithMyUser myUser) {
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        UserDetails user = new User(myUser.username(), "123", UserRole.ADMIN);
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), "123", user.getAuthorities());
        ctx.setAuthentication(auth);
        return ctx;
    }
}
