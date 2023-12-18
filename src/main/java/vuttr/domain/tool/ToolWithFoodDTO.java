package vuttr.domain.tool;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public record ToolWithFoodDTO(
        @Schema(example = "notion")
        String title,
        @Schema(example = "https://www.notion.so/")
        String link,
        @Schema(example = "Very useful notes, cheat sheets, plans and whatever you need to write down")
        String description,
        @Schema(example = "[\"Notes\", \"Studying\", \"Planning\", \"Organization\"]")
        List<String> tags,
        List<Food> foodList) {
    public ToolWithFoodDTO(Tool tool, List<Food> foodList) {
        this(tool.getTitle(), tool.getLink(), tool.getDescription(), tool.getTags(), foodList);
    }
}
