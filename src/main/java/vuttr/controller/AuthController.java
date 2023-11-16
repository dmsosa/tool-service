package vuttr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vuttr.domain.user.AuthorizationDTO;
import vuttr.domain.user.LoginDTO;
import vuttr.domain.user.RegisterDTO;
import vuttr.domain.user.User;
import vuttr.security.TokenService;
import vuttr.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenService tokenService;


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
