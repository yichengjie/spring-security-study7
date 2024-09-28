package com.yicj.study.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
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
            .info(new Info().title("Spring Boot 中使用 Swagger UI 构建 Restful API")
                .contact(new Contact())
                .description("Hello Restfull API")
                .version("v1.0.0")
                .license(new License().name("Apache 2.0").url("https://springdoc.org")))
            .externalDocs(new ExternalDocumentation()
                .description("外部文档")
                .url("https://springshop.wiki.github.org/docs"));
    }

}
