package vuttr.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vuttr.domain.tool.Tool;
import vuttr.domain.user.User;
import vuttr.domain.user.UserRole;
import vuttr.repository.UserRepository;
import vuttr.security.SecurityConfiguration;
import vuttr.security.SecurityFilter;
import vuttr.security.TokenService;
import vuttr.service.ToolService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(controllers = ToolController.class)
@ContextConfiguration(classes = {SecurityConfiguration.class, SecurityFilter.class, TokenService.class})
public class ToolControllerTest {



    @MockBean
    ToolService toolService;
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationManager authenticationManager;
    @MockBean
    UserRepository userRepository;

    @InjectMocks
    ToolController toolController;


    @Autowired
    MockMvc mockMvc;

    private List<Tool> toolList = new ArrayList<Tool>();
    private String testToken;

    @BeforeEach
    void setUp() throws Exception {
        Tool tool1 = new Tool("Watch", "watch.com", "Always in time", Arrays.asList("Timer","O-clock"));
        toolList.add(tool1);

        //Generating token
        User testUser = new User("tester", "tester@gmail.com", UserRole.ADMIN);
        testToken = tokenService.generateToken(testUser);
    }
    @Test
    @WithMockUser(value = "tester", roles = {"ADMIN"})
    void whenGetAllTools_thenReturnList() throws Exception {
        Mockito.when(toolService.getAllTools()).thenReturn(toolList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tools/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
