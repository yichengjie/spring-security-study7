### 授权码模式
1. 通过浏览器访问地址获取授权码
    ```text
    http://127.0.0.1:9090/oauth2/authorize
        ?client_id=oidc-client
        &response_type=code
        &scope=user.info+openid
        &redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client
    ```
2. 使用步骤1中获取到的授权码获取token
    ```text
    POST http://127.0.0.1:9090/oauth2/token
    Authorization: Basic b2lkYy1jbGllbnQ6MTIzNDU2
    Content-Type: application/x-www-form-urlencoded
    Accept: */*
    
    grant_type=authorization_code
    &redirect_uri=http%3A%2F%2F127.0.0.1%3A8080%2Flogin%2Foauth2%2Fcode%2Foidc-client
    &code=8rIq44rqc0yxiYY7ZwSyaKhylwJwqbImt1yLdJkWNZxWhi9QSdrSS0x6L0ompXhGapI-Kf-DtJpcmapenRucoU7OyvuYc_LX8zMufDlTyTqXTozNQOylaykSHq-puw9j
    ```
### 客户端模式
1. 使用客户端模式获取token
   ```text
   POST http://127.0.0.1:8999/oauth2/token
   Content-Type: application/x-www-form-urlencoded
   Authorization: Basic b2lkYy1jbGllbnQ6MTIzNDU2
   Accept: */*
   
   grant_type=client_credentials&client_id=oidc-client
   ````


