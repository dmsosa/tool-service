package vuttr.controller;

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
import vuttr.domain.user.*;
import vuttr.security.TokenService;
import vuttr.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;

    //Get current user
    @GetMapping("/login")
    ResponseEntity<LoginDTO> loggedUser(@RequestHeader HttpHeaders header) {
        String token = header.get("Authorization").get(0)
                .replace("Bearer ","");
        logger.info("token: ISSSS " + token);
        String username = tokenService.validateToken(token);
        UserDetails user = userService.findByUsername(username);
        LoginDTO responseBody = new LoginDTO(token, (User) user);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    };

    @PostMapping("/login")
    ResponseEntity<LoginDTO> logUser(@RequestBody AuthorizationDTO authDTO) throws Exception {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        LoginDTO loginResponse = new LoginDTO(token, (User) auth.getPrincipal());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody RegisterDTO registerDTO) throws Exception {


        if (
                userService.findByUsername(registerDTO.username()) != null ||
                        userService.findByEmail(registerDTO.email()) != null
        ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //In dateibank speichern
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO, encryptedPassword);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
