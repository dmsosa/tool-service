package vuttr.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import vuttr.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;


@Service
public class SecurityFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);
        if (token != null) {
            String username = tokenService.validateToken(token);
            logger.info(username+" USER!!");
            Optional<UserDetails> user = userRepository.findByUsername(username);
            var authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    public String getToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ","");
    }
}
