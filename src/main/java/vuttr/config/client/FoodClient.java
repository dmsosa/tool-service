package vuttr.config.client;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import vuttr.domain.tool.Food;

import java.util.List;

@HttpExchange
public interface FoodClient {
    @GetExchange("/tool/{toolId}")
    public List<Food> getByToolId(@PathVariable Long toolId);
}
