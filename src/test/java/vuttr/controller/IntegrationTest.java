package vuttr.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import vuttr.controller.AuthController;
import vuttr.domain.user.User;
import vuttr.domain.user.UserRole;
import vuttr.repository.ToolRepository;
import vuttr.security.TokenService;
import vuttr.service.ToolService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class IntegrationTest {
    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;
    private static MockMvc mockMvc;
    private String testToken;

    @BeforeAll
    void setup(WebApplicationContext wac) throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        UserDetails testUser = new User("testUser", "123", UserRole.ADMIN);
        var usernamePassword = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword(), testUser.getAuthorities());
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication authentication = this.authenticationManager.authenticate(usernamePassword);
        ctx.setAuthentication(authentication);
        testToken = tokenService.generateToken((User) testUser);
    }

    @Autowired
    private ToolController controller;


    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void whenRequestWithToken_thenReturnAllTools() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", testToken);
        mockMvc.perform(get("/api/tools").headers(headers))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
