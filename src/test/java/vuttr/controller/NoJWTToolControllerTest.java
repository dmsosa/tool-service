package vuttr.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vuttr.domain.tool.Tool;
import vuttr.service.ToolService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(controllers = ToolController.class)
@ContextConfiguration(classes = NoJWTConfig.class)
public class NoJWTToolControllerTest {
    @MockBean
    ToolService toolService;

    @Autowired
    MockMvc mockMvc;

    private final List<Tool> toolList = new ArrayList<Tool>();

    @BeforeEach
        //populate list and generate token for testUser
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

        Authentication auth = AuthenticationManager
    }


    @Test
    @DisplayName("No Auth is Forbidden")
    void whenNoAuthenticated_thenReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/tools"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("If auth then return all tools")
    @WithMockUser(username = "test")
    void whenAuthenticated_thenReturnAllTools() throws Exception {
        when(toolService.getAllTools()).thenReturn(toolList);
        mockMvc.perform(get("/api/tools"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
