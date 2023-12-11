package vuttr.config.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class FoodWebClient {

    @Bean
    @LoadBalanced
    public WebClient.Builder builder() {
        return WebClient.builder().baseUrl("http://myessen-service");
    }
}
