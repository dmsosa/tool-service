package vuttr.service;

import org.springframework.stereotype.Service;
import vuttr.controller.exception.tool.ToolExistsException;
import vuttr.controller.exception.tool.ToolNotFoundException;
import vuttr.domain.tool.Tool;

import java.util.List;

@Service
public interface ToolService {
    List<Tool> getAllTools();
    Tool getToolById(Long id) throws ToolNotFoundException;
    Tool createTool(Tool tool) throws ToolExistsException;
    void deleteTool(Long id) throws ToolNotFoundException;
    void updateTool(Long id, Tool newTool);
}
