package com.udtt.backend.global.config;

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
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("로컬 서버"),
                        new Server()
                                .url("https://api.studio.udtt.org")
                                .description("운영 서버")));

    }

    private Info apiInfo() {
        return new Info()
                .title("UDTT Backend API")
                .description("UDTT 프로젝트 백엔드 API 문서입니다.")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("UDTT Backend"));
    }
}