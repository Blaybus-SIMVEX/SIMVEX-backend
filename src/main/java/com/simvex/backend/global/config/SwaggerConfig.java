package com.simvex.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = "sessionToken";

        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://simvex.xn--yq5b.xn--3e0b707e").description("Production Server (HTTPS)"),
                        new Server().url("http://localhost:8080").description("Local Server")
                ))
                .info(new Info()
                        .title("SIMVEX API")
                        .description("3D 기계 부품 뷰어 학습 솔루션 API")
                        .version("v1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .description("세션 토큰 (POST /api/sessions로 발급)")));
    }
}
