package vuttr.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            method = "GET",
            summary = "Get list of tools",
            description = "Retrieve a list of all current tools stored",
            responses = {
                    @ApiResponse(
                            description = "toolList",
                            responseCode = "200",
                            content =
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Tool.class)
                                    )
                            ))
            })
    @GetMapping("/")
    ResponseEntity<List<Tool>> getAllTools() {
        return new ResponseEntity<>(toolService.getAllTools(), HttpStatus.OK);
    }

    @Operation(
            method = "GET",
            description = "Get tool according to its Id",
            summary = "Get one tool",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tool found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Tool.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tool Not Found",
                            content = {@Content()}
                    )
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<Tool> getToolById(@PathVariable Long id) throws ToolNotFoundException {
        Tool tool = toolService.getToolById(id);
        return new ResponseEntity<>(tool, HttpStatus.OK);
    }

    @Operation(
            method = "POST",
            description = "Ein neues Werkzeug erstellen und am unseres Datenbank speichern, wenn es schon existiert, ein Exception werfen werden",
            summary = "Create tool",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tool created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Tool.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Tool already exists",
                            content = {@Content()}
                    )
            }
    )
    @PostMapping("/")
    ResponseEntity<Tool> createTool(@RequestBody ToolDTO toolDTO) throws ToolExistsException {
        Tool tool = new Tool(toolDTO);
        toolService.createTool(tool);
        return new ResponseEntity<>(tool, HttpStatus.OK);
    }

    @Operation(
            method = "PUT",
            description = "Edit an existing tool, creates a new one if it does not exists",
            summary = "Update tool",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tool updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Tool.class)
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<Tool> updateTool(@PathVariable Long id, @RequestBody ToolDTO toolDTO) {
        Tool updatedTool = new Tool(toolDTO);
        toolService.updateTool(id, updatedTool);
        return new ResponseEntity<>(updatedTool, HttpStatus.OK);
    }


    @Operation(
            method = "DELETE",
            description = "Delete a stored tool from our database",
            summary = "deletes a tool",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Tool successfully deleted",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tool Not Found",
                            content = @Content()
                    )
            }

    )
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTool(@PathVariable Long id) throws ToolNotFoundException {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    // methods with food
    @Operation(
            method = "GET",
            summary = "Get a tool according to its id, the food list is included as well",
            description = "Will retrieve a tool, including all the foods that belongs to it",
            responses = {
                    @ApiResponse(
                            description = "Tool with food included",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ToolWithFoodDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Tool without food included",
                            responseCode = "206",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Tool.class)
                    )
                    )
            }
    )
    @GetMapping("{id}/withfood/")
    ResponseEntity<ToolWithFoodDTO> getToolWithFoodById(@PathVariable Long id) throws  ToolNotFoundException {
        Tool tool = toolService.getToolById(id);
        List<Food> foodList = builder.build().get().uri("/api/foods/toolId/"+id)
                .retrieve()
                .bodyToMono(FoodClientResponse.class)
                .block().getFoodList();
        return new ResponseEntity<>(new ToolWithFoodDTO(tool, foodList), HttpStatus.OK);
    }

    @Operation(
            method = "GET",
            summary = "List of tool with food",
            description = "Get a list of all tools including the food that belong to each one",
            responses = {
                    @ApiResponse(
                            description = "Tools with foods included",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = ToolWithFoodDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Tools without foods included",
                            responseCode = "206",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = Tool.class)
                                    )
                            )
                    )
            }
    )
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
