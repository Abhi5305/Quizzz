package com.exam.quiz.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin-management")
                .pathsToMatch("/welcome/users/**", "/welcome/user")
                .addOpenApiMethodFilter(method ->
                        method.isAnnotationPresent(PreAuthorize.class) && method.getAnnotation(PreAuthorize.class).value().contains("hasRole('ROLE_ADMIN')")
                )
                .build();
    }

    @Bean
    public GroupedOpenApi authenticationApi() {
        return GroupedOpenApi.builder()
                .group("authentication")
                .pathsToMatch("/welcome/generate", "/welcome/current")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-management")
                .pathsToMatch("/welcome/users/{username}", "/welcome/current")
                .addOpenApiMethodFilter(method ->
                        method.isAnnotationPresent(PreAuthorize.class) && method.getAnnotation(PreAuthorize.class).value().contains("hasAnyRole('USER','ADMIN')")
                )
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .version("1.0.0")
                        .title("User Management API")
                        .description("APIs for managing users, authentication, and roles.")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@example.com")
                                .url("https://example.com")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
