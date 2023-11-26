package vuttr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vuttr.controller.exception.GlobalExceptionHandler;
import vuttr.controller.exception.tool.ToolExistsException;
import vuttr.domain.tool.Tool;
import vuttr.domain.tool.ToolDTO;
import vuttr.repository.ToolRepository;
import vuttr.security.SecurityConfiguration;
import vuttr.security.SecurityFilter;
import vuttr.service.ToolService;

import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@WebMvcTest(controllers = {ToolController.class})
@ContextConfiguration(classes = {SecurityConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ToolControllerTest {

//This will use our TestSecurityConfiguration.
    @MockBean
    ToolService toolService; //toolController depends on toolService

    //Mocking Security Beans so all requests are permitted
    @MockBean
    SecurityFilter securityFilter;
    @MockBean
    SecurityFilterChain securityFilterChain;

    private MockMvc mockMvc; //mockMvc to perform pseudo http requests


    private List<Tool> toolList = new ArrayList<Tool>(); //fake List our toolService will work with
    @BeforeAll
    //populate list
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

        //Build our Mock
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ToolController(toolService)).build();

    }
    @Test
    @DisplayName("Get All Tools")
    void whenGetAllTools_thenReturnList() throws Exception {
        Mockito.when(toolService.getAllTools()).thenReturn(toolList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tools/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    @DisplayName("Get Specific Tool")
    void whenGetExistingTool_thenReturnTool() throws Exception {
        Mockito.when(toolService.getToolById((long) 2)).thenReturn(toolList.get(2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tools/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        Mockito.verify(toolService).getToolById((long) 2);

    }
    @Test
    @DisplayName("Create A Tool")
    void whenPostNewTool_thenReturnTool() throws Exception {
        ToolDTO toolDTO = new ToolDTO("newTool", "Link", "A new wonderful tool", Arrays.asList("Wunderbar","Schon","Tranquillement"));
        Tool newTool = new Tool(toolDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newTool);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/tools/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(json));
    }
    @Test
    @DisplayName("Tool Already Exists")
    void whenPostExistingTool_thenThrowException() throws ToolExistsException, Exception {
        ToolDTO toolDTO = new ToolDTO("newTool", "Link", "A new wonderful tool", Arrays.asList("Wunderbar","Schon","Tranquillement"));
        Tool newTool = new Tool(toolDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        Mockito.doThrow(ToolExistsException.class).when(toolService).createTool(Mockito.any(Tool.class));
        String json = objectMapper.writeValueAsString(newTool);
       Assertions.assertThrows(ServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/tools/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isConflict());
        });
       Mockito.verify(toolService).createTool(Mockito.any(Tool.class));
    }
}
