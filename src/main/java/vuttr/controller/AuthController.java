package vuttr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vuttr.domain.user.AuthorizationDTO;
import vuttr.domain.user.RegisterDTO;
import vuttr.domain.user.User;
import vuttr.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;


    @GetMapping("/login")
    ResponseEntity<User> logUser(@RequestBody AuthorizationDTO authDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        if (
                user == null
        ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return
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
