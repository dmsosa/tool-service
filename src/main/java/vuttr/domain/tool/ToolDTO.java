package vuttr.domain.tool;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ToolDTO(@Schema(example = "Calculator")
                      String title,

                      @Schema(example = "www.calculator.com")
                      String link,

                      @Schema(example = "Calculate simple math operations with this tool")
                      String description,

                      @Schema(example = "[\"Calculate\", \"Math\", \"Numbers\"]")
                      List<String> tags) {
}
