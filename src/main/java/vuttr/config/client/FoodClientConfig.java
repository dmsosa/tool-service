package vuttr.config.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class FoodClientConfig {

    @Autowired
    LoadBalancedExchangeFilterFunction filterFunction;

    @LoadBalanced
    @Bean
    WebClient foodWebClient() {
        return WebClient.builder()
                .baseUrl("http://myessen-service/api/foods")
                .filter(filterFunction)
                .build();
    }

    @Bean
    FoodClient foodClient() {
        HttpServiceProxyFactory httpServiceProxyFactory =
            HttpServiceProxyFactory.builder()
                    .clientAdapter(WebClientAdapter.forClient(foodWebClient()))
                    .build();
        return httpServiceProxyFactory.createClient(FoodClient.class);
    }
}
