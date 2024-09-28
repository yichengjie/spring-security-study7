1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    </dependency>
    ```
2. 编写配置类
    ```java
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
    ```
3. application.yml配置
    ```yaml
    springdoc:
      api-docs:
        enabled: true
        path: /v3/api-docs
      swagger-ui:
        enabled: true # 开启swagger界面，依赖OpenApi，需要OpenApi同时开启
        path: /swagger-ui/index.html
    ```