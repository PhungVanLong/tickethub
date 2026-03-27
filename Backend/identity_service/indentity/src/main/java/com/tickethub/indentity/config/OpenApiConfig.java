package com.tickethub.indentity.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI identityOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("IdentityService API")
                        .version("1.0.0")
                        .description("Authentication, users, and logs service"));
    }
}
