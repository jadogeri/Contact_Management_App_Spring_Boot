package com.josephadogeri.contact_management_app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Contact Management App").version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth")) // Or "bearerAuth" for JWT
                .components(new Components()
                                .addSecuritySchemes("basicAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .description("Basic Authentication"))
                        // Add other security schemes like Bearer Token for JWT if needed
                );
    }
}