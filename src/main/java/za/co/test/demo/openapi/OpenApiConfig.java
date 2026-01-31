package za.co.test.demo.openapi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Employee Skills API")
                .version("1.0")
                .description("API demonstrating dependency injection with employee skills"))
            .externalDocs(new ExternalDocumentation()
                .description("Spring Boot Documentation")
                .url("https://spring.io/projects/spring-boot"));
    }
}
