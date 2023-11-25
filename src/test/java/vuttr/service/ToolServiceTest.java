package vuttr.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import vuttr.controller.exception.tool.ToolExistsException;
import vuttr.controller.exception.tool.ToolNotFoundException;
import vuttr.domain.tool.Tool;
import vuttr.repository.ToolRepository;
import vuttr.service.impl.ToolServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles(value = "test")
public class ToolServiceTest {

    @Mock
    ToolRepository toolRepository;

    @InjectMocks
    ToolServiceImpl toolService;

    private static List<Tool> toolList = new ArrayList<Tool>();
    private static Long exampleId = (long) 2;

    @BeforeEach
    void setUp() {
        Tool tool1 = new Tool("YouTube", "www.youtube.com", "videos and much more", Arrays.asList("videos", "share"));
        Tool tool2 = new Tool("Instagram", "www.instagram.com", "photos and friends", Arrays.asList("social", "photos"));
        Tool tool3 = new Tool("Mover", "www.mover.com", "Move with efficiency", Arrays.asList("move", "location"));
        toolList.add(tool1);
        toolList.add(tool2);
        toolList.add(tool3);
    }

    @Test
    @DisplayName("Service return all foods")
    void shouldGetListOfTools() {
        Mockito.when(toolRepository.findAll()).thenReturn(toolList);
        List<Tool> toolList = toolService.getAllTools();
        Assertions.assertThat(toolList.isEmpty()).isFalse();
    }
    @Test
    @DisplayName("DELETE: Service deletes tools")
    void whenToolExists_thenDeleteSuccessfully() throws ToolNotFoundException {
        Mockito.when(toolRepository.existsById(exampleId))
                .thenReturn(true);
        toolService.deleteTool(exampleId);
    }
    @Test
    @DisplayName("DELETE: Tool do not exists")
    void whenToolDoNotExists_thenThrowsException() throws ToolNotFoundException {
        Mockito.when(toolRepository.existsById(exampleId))
                .thenReturn(false);
        ToolNotFoundException exception = assertThrows(ToolNotFoundException.class, () -> {
            toolService.deleteTool(exampleId);
        });
        String expectedMessage = "Tool with id 2 was not found";
        Assertions.assertThat(expectedMessage.equals(exception.getMessage()));
    }
    @Test
    @DisplayName("POST: Create tools")
    void whenToolDoNotExists_thenCreatesTool() throws ToolExistsException {
        Mockito.when(toolRepository.existsByTitle("newtool")).thenReturn(false);
        Tool newTool = new Tool("NewTool", "NewTool.com", "A new tool for our DB", Arrays.asList("New","Generic Tool", "Default"));
        toolService.createTool(newTool);
    }
    @Test
    @DisplayName("POST: Tool Already Exists")
    void whenToolExists_thenThrowsException() throws ToolExistsException {
        Mockito.when(toolRepository.existsByTitle("newtool")).thenReturn(true);
        Tool newTool = new Tool("NewTool", "NewTool.com", "A new tool for our DB", Arrays.asList("New", "Generic Tool", "Default"));
        ToolExistsException exception = assertThrows(ToolExistsException.class, () -> {
            toolService.createTool(newTool);
        });
        String expectedMessage = "Tool with title New Tool already exists";
        Assertions.assertThat(expectedMessage.equals(exception.getMessage()));
    }
    @Test
    @DisplayName("GET: Find by Id")
    void whenToolExists_thenGetEntity() throws ToolNotFoundException {
        Mockito.when(toolRepository.findById(exampleId)).thenReturn(Optional.ofNullable(toolList.get(2)));
        Tool foundTool = toolService.getToolById(exampleId);
        Assertions.assertThat(foundTool).isNotNull();
    }
    @Test
    @DisplayName("GET: Throw Exception")
    void whenToolDoNotExists_thenThrowException() throws ToolNotFoundException {
        Mockito.when(toolRepository.findById(exampleId)).thenReturn(Optional.ofNullable(null));
        ToolNotFoundException exception = assertThrows(ToolNotFoundException.class, () -> {
            toolService.getToolById(exampleId); } );
        String expectedMessage = "Tool with id 2 was not found";
        Assertions.assertThat(expectedMessage.equals(exception.getMessage()));
    }
}
