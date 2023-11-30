package vuttr.domain.tool;

import java.util.ArrayList;
import java.util.List;

public record ToolWithFoodDTO(Tool tool, List<Food> foodList) {
}
