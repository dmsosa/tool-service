package vuttr.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vuttr.domain.user.RegisterDTO;
import vuttr.domain.user.User;
import vuttr.domain.user.UserRole;
import vuttr.repository.ToolRepository;
import vuttr.repository.UserRepository;
import vuttr.security.SecurityConfiguration;
import vuttr.security.SecurityFilter;
import vuttr.security.TokenService;
import vuttr.service.ToolService;
import vuttr.service.UserService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@WebMvcTest(controllers = AuthController.class)
@ContextConfiguration(classes = {SecurityConfiguration.class, SecurityFilter.class})
public class AuthControllerTest {
    @MockBean
    UserService userService;
    @MockBean
    TokenService tokenService;
    @Autowired
    AuthenticationManager authenticationManager;
    @MockBean
    UserRepository userRepository;
    @MockBean
    SecurityFilter securityFilter;
    @Autowired
    private MockMvc mockMvc;





    @Test
    void whenUserRegisterisNew_thenStatusIsOk() throws Exception {
        Mockito.when(userService.existsByUsername("sample")).thenReturn(false);
        Mockito.when(userService.existsByEmail("sample@gmail.com")).thenReturn(false);
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterDTO registerDTO = new RegisterDTO("sample", "sample@gmail.com", "123", UserRole.BASIC);
        String userRegister = objectMapper.writeValueAsString(registerDTO);
        HttpHeaders headers = new HttpHeaders();
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegister)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
