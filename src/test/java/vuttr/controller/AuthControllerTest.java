package vuttr.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vuttr.security.TokenService;
import vuttr.service.UserService;

import static org.mockito.Mockito.when;


@WebMvcTest
public class AuthControllerTest {
    @MockBean
    UserService userService;
    @MockBean
    TokenService tokenService;

    @MockBean
    SecurityContext securityContext;



    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Test
    void contextLoads() {
        Assertions.assertThat(authController).isNotNull(); //check Controller Exists
    }


    @Test
    void whenTokenIsValid_thenReturnUser() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJteWVzc2VuIiwic3ViIjoibWFyayIsImlhdCI6MTcwMDMxODg4OCwiZXhwIjoxNzAwMzM2ODg4LCJlbWFpbCI6Im1hcmtAZ21haWwuY29tIn0.MqV7Ga0fxNvPrWnJnpu-N_Ezmwwzx9eAM3JUcrX3Vpw");
        when(tokenService.validateToken("faketoken")).thenThrow(JWTDecodeException.class);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/auth/login")
                        .headers(headers)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isForbidden());
        ;
    }

}
