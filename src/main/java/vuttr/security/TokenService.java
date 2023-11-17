package vuttr.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vuttr.domain.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${application.secret-key}")
    private String secret;

    public String generateToken(User user) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("myessen")
                    .withSubject(user.getUsername())
                    .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.of("+00:00")))
                    .withExpiresAt(genExpirationDate())
                    .withClaim("email", user.getEmail())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new Exception("Error while generating token", exception);
        }
    }
    public String validateToken(String token) {
        token = recoverToken(token); //removing "Bearer " characters if any
        Algorithm algorithm = Algorithm.HMAC256(secret);
         DecodedJWT decoded = JWT.require(algorithm)
                .withIssuer("myessen")
                .build().verify(token);
         String subject = decoded.getSubject();
         Instant issuedAt = decoded.getIssuedAtAsInstant();
         return subject;
    }
    private String recoverToken(String token) {
        return token.replace("Bearer ", "");
    }
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("+00:00"));
    }
}
