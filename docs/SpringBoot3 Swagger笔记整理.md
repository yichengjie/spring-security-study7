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
   ```
3. application.yml配置
   ```yaml
   springdoc:
     api-docs:
       enabled: true
       path: /v3/api-docs
     swagger-ui:
       enabled: true
       path: /index.html
     packages-to-scan: com.yicj.study.springdoc.controller
   ```