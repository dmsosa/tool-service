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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
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
import vuttr.security.SecurityContextTest;
import vuttr.security.SecurityFilter;
import vuttr.security.TokenService;
import vuttr.service.ToolService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = ToolController.class)
public class ToolControllerTest {





    @MockBean
    ToolService toolService; //toolController depends on toolService

    @Autowired
    MockMvc mockMvc; //mockMvc to perform pseudo http requests

    //Injecting mocks
    @InjectMocks
    ToolController toolController;

    private List<Tool> toolList = new ArrayList<Tool>(); //fake List our toolService will work with
    private String testToken; //fake token for our testUser
    private UserDetails testUser; //test User

    @BeforeEach //populate list and generate token for testUser
    void setUp() throws Exception {
        //Populating list
        Tool tool1 = new Tool("Watch", "watch.com", "Always in time", Arrays.asList("Timer","O-clock"));
        Tool tool2 = new Tool("Mirror", "mirror.com", "See yourself as you are", Arrays.asList("Mirror","Comparison"));
        Tool tool3 = new Tool("Ball", "ball.com", "Control and fun", Arrays.asList("Games","Sport"));
        Tool tool4 = new Tool("Car", "car.com", "Driving and moving", Arrays.asList("Transportation","Wheels"));
        Tool tool5 = new Tool("Buttons", "button.com", "Trigger your decisions", Arrays.asList("Trigger","Make"));
        Tool tool6 = new Tool("Bulb", "bulb.es", "Light your way", Arrays.asList("Light","Bright"));
        Tool tool7 = new Tool("Water", "water.ve", "Drink your water", Arrays.asList("Hydration","Crucial"));
        toolList.add(tool1);
        toolList.add(tool2);
        toolList.add(tool3);
        toolList.add(tool4);
        toolList.add(tool5);
        toolList.add(tool6);
        toolList.add(tool7);

    }
    @Test
    void whenGetAllTools_thenReturnList() throws Exception {
        Mockito.when(toolService.getAllTools()).thenReturn(toolList);
        Mockito.when(toolService.getToolById((long) 2)).thenReturn(toolList.get(2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tools/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
