package com.exam.quiz.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io. swagger. v3.oas. annotations.info.Info(
                title = "Customer Referral Service API",
                version = "2.0",
                description = "This API exposes endpoints to manage customer referrals."
        )
)
@Configuration
class SpringDocConfig {
    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI defineOpenApi() {
        Info information = new Info()
                        .title("Quiz Service API")
                        .version("2.0")
                        .description("This API exposes endpoints to manage quiz service.");

        return new OpenAPI()
                .info(information)
                .openapi("3.0.0")
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
        );
    }
}


