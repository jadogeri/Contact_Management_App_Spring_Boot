package com.josephadogeri.contact_management_app.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.checkerframework.checker.guieffect.qual.AlwaysSafe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                .title("Contact Management App")
                .termsOfService("https://swagger.io/terms/")
                .description(getDescription())
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                .contact(new Contact().name("John Doe").email("john.doe@example.com"))
                .version("1.0"))
                .externalDocs(new ExternalDocumentation().description("Find out more about Swagger").url("https://swagger.io"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth")) // Or "bearerAuth" for JWT
                .components(new Components()
                                .addSecuritySchemes("basicAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                                        .description("Basic Authentication"))
                        // Add other security schemes like Bearer Token for JWT if needed
                );

    }

    private static String getDescription() {
        return "   This is a sample Pet Store Server based on the OpenAPI 3.0 specification.  You can find out more about\n" +
                "    Swagger at [https://swagger.io](https://swagger.io). In the third iteration of the pet store, we've switched to the design first approach!\n" +
                "    You can now help us improve the API whether it's by making changes to the definition itself or to the code.\n" +
                "    That way, with time, we can improve the API in general, and expose some of the new features in OAS3.\n" +
                "\n" +
                "    Some useful links:\n" +
                "    - [The Pet Store repository](https://github.com/swagger-api/swagger-petstore)\n" +
                "    - [The source API definition for the Pet Store](https://github.com/swagger-api/swagger-petstore/blob/master/src/main/resources/openapi.yaml)";
    }


}

