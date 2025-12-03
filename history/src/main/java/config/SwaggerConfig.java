package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI historyServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("History Management Service API")
                        .description("REST API для агрегации и управления историей изменений из всех микросервисов")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Bank API Team")
                                .email("api-support@bank.com")
                                .url("https://bank.com/api")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("History Service Development Server"),
                        new Server()
                                .url("https://history.api.bank.com")
                                .description("History Service Production Server")
                ));
    }
}
