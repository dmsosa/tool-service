package vuttr.controller;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import vuttr.controller.exception.tool.ToolExistsException;
import vuttr.controller.exception.tool.ToolNotFoundException;
import vuttr.domain.tool.*;
import vuttr.service.ToolService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
@Slf4j
public class ToolController {



    @Autowired
    WebClient.Builder builder;

    private final ToolService toolService;
    private final Logger logger = LoggerFactory.getLogger(ToolController.class);

    // basic CRUD operations
    @GetMapping("/")
    ResponseEntity<List<Tool>> getAllTools() {
        return new ResponseEntity<>(toolService.getAllTools(), HttpStatus.OK);
    }

    @PostMapping("/")
    ResponseEntity<Tool> createTool(@RequestBody ToolDTO toolDTO) throws ToolExistsException {
        Tool tool = new Tool(toolDTO);
        toolService.createTool(tool);
        return new ResponseEntity<>(tool, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Tool> getToolById(@PathVariable Long id) throws ToolNotFoundException {
        Tool tool = toolService.getToolById(id);
        return new ResponseEntity<>(tool, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    ResponseEntity<Tool> updateTool(@PathVariable Long id, @RequestBody ToolDTO toolDTO) {
        Tool updatedTool = new Tool(toolDTO);
        toolService.updateTool(id, updatedTool);
        return new ResponseEntity<>(updatedTool, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTool(@PathVariable Long id) throws ToolNotFoundException {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // methods with food
    @GetMapping("{id}/withfood/")
    ResponseEntity<ToolWithFoodDTO> getToolWithFoodById(@PathVariable Long id) throws  ToolNotFoundException {
        Tool tool = toolService.getToolById(id);
        List<Food> foodList = builder.build().get().uri("/api/foods/toolId/"+id)
                .retrieve()
                .bodyToMono(FoodClientResponse.class)
                .block().getFoodList();
        return new ResponseEntity<>(new ToolWithFoodDTO(tool, foodList), HttpStatus.OK);
    }

    @GetMapping("/withfood/")
    ResponseEntity<List<ToolWithFoodDTO>> getAllWithFood() {
        List<Tool> toolList = toolService.getAllTools();
        List<ToolWithFoodDTO> results = toolList.stream().map(
                tool -> new ToolWithFoodDTO(tool,
                        builder.build().get().uri("/api/foods/toolId/"+tool.getId())
                                .retrieve()
                                .bodyToMono(FoodClientResponse.class)
                                .block().getFoodList())).collect(Collectors.toList());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
