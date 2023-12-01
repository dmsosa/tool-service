package vuttr.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
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
@Slf4j
public class ToolController {

    private final ToolService toolService;
    private final FoodClient foodClient;
    private final EurekaClient discoveryClient;
    private final Logger logger = LoggerFactory.getLogger(ToolController.class);
    @GetMapping("/")
    ResponseEntity<List<Tool>> getAllTools() {
        return new ResponseEntity<>(toolService.getAllTools(), HttpStatus.OK);
    }

    @GetMapping("/withfood/")
    ResponseEntity<List<Tool>> getAllWithFood() {
        List<Tool> toolList = toolService.getAllTools();
        InstanceInfo instanceInfos = discoveryClient.getNextServerFromEureka("MYESSEN-SERVICE", false);

        logger.info("Got instance: ", instanceInfos.getAppName());
//        List<ToolWithFoodDTO> withFoodDTOList = toolList.stream().map((tool) -> new ToolWithFoodDTO(tool, foodClient.getByToolId(tool.getId()))).toList();
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
