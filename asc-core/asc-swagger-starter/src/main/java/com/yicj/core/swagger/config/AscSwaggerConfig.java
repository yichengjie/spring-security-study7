package com.yicj.core.swagger.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class AscSwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("SpringShop API")
                    .description("Spring shop sample application")
                    .version("v0.0.1")
                    .license(new License().name("Apache 2.0").url("https://springdoc.org")))
            .addSecurityItem(buildSecurityRequirement())
            .components(new Components().securitySchemes(buildSecuritySchemes()))
            .externalDocs(new ExternalDocumentation()
                    .description("SpringShop Wiki Documentation")
                    .url("https://springshop.wiki.github.org/docs"));
    }

    private SecurityRequirement buildSecurityRequirement() {
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("token");
        securityRequirement.addList("basic");
        return securityRequirement;
    }

    private Map<String, SecurityScheme> buildSecuritySchemes() {
        Map<String, SecurityScheme> securitySchemes = new HashMap<>();
        // token
        SecurityScheme token = new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY) // 类型
            .name("token") // 请求头的 name
            .in(SecurityScheme.In.HEADER);
        securitySchemes.put("token", token);
        // basic
        SecurityScheme basic = new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY) // 类型
            .name("Authorization")
            .in(SecurityScheme.In.HEADER) ;
        securitySchemes.put("basic", basic);
        return securitySchemes;
    }
}
