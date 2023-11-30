package vuttr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuttr.config.client.FoodClient;
import vuttr.controller.exception.tool.ToolExistsException;
import vuttr.controller.exception.tool.ToolNotFoundException;
import vuttr.domain.tool.Food;
import vuttr.domain.tool.Tool;
import vuttr.domain.tool.ToolDTO;
import vuttr.domain.tool.ToolWithFoodDTO;
import vuttr.service.ToolService;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;
    private final FoodClient foodClient;

    @GetMapping("/")
    ResponseEntity<List<Tool>> getAllTools() {
        return new ResponseEntity<>(toolService.getAllTools(), HttpStatus.OK);
    }

    @GetMapping("/withfood/")
    ResponseEntity<List<ToolWithFoodDTO>> getAllWithFood() {
        List<Tool> toolList = toolService.getAllTools();
        List<ToolWithFoodDTO> withFoodDTOList = toolList.stream().map((tool) -> new ToolWithFoodDTO(tool, foodClient.getByToolId(tool.getId()))).toList();
        return new ResponseEntity<>(withFoodDTOList, HttpStatus.OK);
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

    @GetMapping("/withfood/{id}")
    ResponseEntity<ToolWithFoodDTO> getToolWithFoodById(@PathVariable Long id) throws  ToolNotFoundException {
        ToolWithFoodDTO withFoodDTO = new ToolWithFoodDTO(toolService.getToolById(id), foodClient.getByToolId(id));
        return new ResponseEntity<>(withFoodDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<Tool> updateTool(@PathVariable Long id, @RequestBody ToolDTO toolDTO) {
        Tool updatedTool = new Tool(toolDTO);
        toolService.updateTool(id, updatedTool);
        return new ResponseEntity<>(updatedTool, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteTool(@PathVariable Long id) throws ToolNotFoundException {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
