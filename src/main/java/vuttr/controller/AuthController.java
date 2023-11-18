package vuttr.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vuttr.controller.exception.user.UserExistsException;
import vuttr.controller.exception.user.UserNotFoundException;
import vuttr.domain.user.*;
import vuttr.security.TokenService;
import vuttr.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    //Get current user
    @GetMapping("/login")
    ResponseEntity<LoginDTO> loggedUser(@RequestHeader HttpHeaders header) throws UserNotFoundException  {

        String token = header.get("Authorization").get(0)
                .replace("Bearer ","");
        logger.info("token: ISSSS " + token);
        String username = tokenService.validateToken(token);
        UserDetails user = userService.findByUsername(username);
        LoginDTO responseBody = new LoginDTO(token, (User) user);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    };

    @PostMapping("/login")
    ResponseEntity<LoginDTO> logUser(@RequestBody AuthorizationDTO authDTO) throws UserNotFoundException, Exception {
        if (authDTO.login() == null || authDTO.password() == null) {
            throw new Exception("Missing parameters: Logind and Password are required");
        }
        UserDetails user = userService.findByLogin(authDTO.login()); //checking if user exists
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        LoginDTO loginResponse = new LoginDTO(token, (User) auth.getPrincipal());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody RegisterDTO registerDTO) throws UserExistsException {


        if (userService.existsByUsername(registerDTO.username())) {
            throw new UserExistsException(registerDTO.username());
        } else if (userService.existsByEmail(registerDTO.email())) {
            throw new UserExistsException(registerDTO.email());
        }

        //In dateibank speichern
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO, encryptedPassword);
        user = (User) userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
