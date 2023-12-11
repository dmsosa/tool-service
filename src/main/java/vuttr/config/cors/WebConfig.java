package vuttr.config.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/api/**")
                .allowedHeaders(HttpHeaders.AUTHORIZATION, HttpHeaders.ACCEPT)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOriginPatterns("http://localhost:8061/**")
                .maxAge(3600*10);

    }
}