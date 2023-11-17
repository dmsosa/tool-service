package vuttr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vuttr.controller.exception.tool.ToolExistsException;
import vuttr.controller.exception.tool.ToolNotFoundException;
import vuttr.domain.tool.Tool;
import vuttr.repository.ToolRepository;
import vuttr.service.ToolService;

import java.util.List;
import java.util.Optional;

@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    ToolRepository toolRepository;

    @Override
    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    @Override
    public Tool getToolById(Long id) throws ToolNotFoundException {
        Optional<Tool> tool = toolRepository.findById(id);
        return tool.orElseThrow(() -> new ToolNotFoundException(id));
    }

    @Override
    public Tool createTool(Tool tool) throws ToolExistsException {
        if (toolRepository.existsByTitle(tool.getTitle())) {
            throw new ToolExistsException(tool.getTitle());
        }
        toolRepository.save(tool);
        return tool;
    }

    @Override
    public void deleteTool(Long id) throws ToolNotFoundException {
        if (!toolRepository.existsById(id)) {
            throw new ToolNotFoundException(id);
        }
        toolRepository.deleteById(id);
    }

    @Override
    public void updateTool(Long id, Tool newTool) {
        Optional<Tool> oldTool = toolRepository.findById(id);
        if (oldTool.isPresent()) {
            oldTool.get().setTitle(newTool.getTitle());
            oldTool.get().setDescription(newTool.getDescription());
            oldTool.get().setLink(newTool.getLink());
            oldTool.get().setTags(newTool.getTags());
            toolRepository.save(oldTool.get());
        } else {
            toolRepository.save(newTool);
        }
    }
}
