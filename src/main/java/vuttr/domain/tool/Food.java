package vuttr.domain.tool;



import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record Food

        (@Schema(example = "2")
         Long id,
         @Schema(example = "banana")
         String name,
         @Schema(example = "89")
         Long kcal,
         @Schema(example = "1.21")
         BigDecimal price,
         @Schema(example = "static://banana.jpeg")
         String image,
         @Schema(example = "Sweet and creamy")
         String description

        ) {
}
