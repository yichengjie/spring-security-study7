1. OAuth2TokenEndpointFilter 获取token
2. OAuth2AuthorizationServerConfigurer 配置授权服务器
### 启动类
1. OAuth2AuthorizationServerAutoConfiguration
2. OAuth2AuthorizationServerJwtAutoConfiguration
3. OAuth2AuthorizationServerConfigurer#init
    ```text
    OAuth2ClientAuthenticationConfigurer                   => OAuth2ClientAuthenticationFilter
    OAuth2AuthorizationServerMetadataEndpointConfigurer    => OAuth2AuthorizationServerMetadataEndpointFilter
    OAuth2AuthorizationEndpointConfigurer                  => OAuth2AuthorizationEndpointFilter
    OAuth2TokenEndpointConfigurer                          => OAuth2TokenEndpointFilter
    OAuth2TokenIntrospectionEndpointConfigurer             => OAuth2TokenIntrospectionEndpointFilter
    OAuth2TokenRevocationEndpointConfigurer                => OAuth2TokenRevocationEndpointFilter
    OAuth2DeviceAuthorizationEndpointConfigurer            => OAuth2DeviceAuthorizationEndpointFilter
    OAuth2DeviceVerificationEndpointConfigurer             => OAuth2DeviceVerificationEndpointFilter
    OidcConfigurer                                         => OidcClientRegistrationEndpointFilter
    ```
4.  OAuth2TokenEndpointConfigurer#init => createDefaultAuthenticationProviders(httpSecurity)


### Password模式
1. OAuth2ClientAuthenticationFilter
   ```text
   POST /oauth2/token, /oauth2/introspect, /oauth2/revoke, /oauth2/device_authorization
   ```