package vuttr.domain.tool;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public record ToolWithFoodDTO(String title, String link, String description, List<String> tags, List<Food> foodList) {
    public ToolWithFoodDTO(Tool tool, List<Food> foodList) {
        this(tool.getTitle(), tool.getLink(), tool.getDescription(), tool.getTags(), foodList);
    }
}
