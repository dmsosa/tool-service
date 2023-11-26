package vuttr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuttr.controller.exception.tool.ToolExistsException;
import vuttr.controller.exception.tool.ToolNotFoundException;
import vuttr.domain.tool.Tool;
import vuttr.domain.tool.ToolDTO;
import vuttr.service.ToolService;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

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
    ResponseEntity deleteTool(@PathVariable Long id) throws ToolNotFoundException {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
