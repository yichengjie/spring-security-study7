package com.yicj.study.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author yicj
 * @since 2024/9/27 21:15
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("SpringShop API")
                .description("Spring shop sample application")
                .version("v0.0.1")
                .license(new License().name("Apache 2.0").url("https://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                .description("SpringShop Wiki Documentation")
                .url("https://springshop.wiki.github.org/docs"));
    }

}
