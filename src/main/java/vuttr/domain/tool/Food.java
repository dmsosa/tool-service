package vuttr.domain.tool;



import java.math.BigDecimal;

public record Food(Long id, String name, Long kcal, BigDecimal price, String image, String description) {
}
