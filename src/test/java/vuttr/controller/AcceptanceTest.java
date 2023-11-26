package vuttr.controller;

import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vuttr.domain.user.User;
import vuttr.repository.UserRepository;
import vuttr.security.TokenService;


import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class AcceptanceTest {

    private String testToken;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void setup() throws Exception {
        UserDetails user = userRepository.findByUsername("test").get();
        testToken = tokenService.generateToken((User) user);
    }

    @Test
    @DisplayName("GET: Forbidden")
    void givenGet_whenNoToken_thenStatusIsForbidden() throws Exception {
        mockMvc.perform(get("/api/tools/"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    @Test
    @DisplayName("POST: Forbidden")
    void givenPost_whenNoToken_thenStatusIsForbidden() throws Exception {
        mockMvc.perform(post("/api/tools/"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("GET: Get all tools")
    void whenValidToken_thenGetAllTools() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", testToken);
        mockMvc.perform(get("/api/tools/")
                .headers(headers))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
