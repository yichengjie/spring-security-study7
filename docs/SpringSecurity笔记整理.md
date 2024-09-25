### 自定义登录页面
1. 编写登录页面
    ```html
    <!DOCTYPE html>
    <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
    <head>
        <title>Please Log In</title>
    </head>
    <body>
    <h1>Please Log In</h1>
    <form th:action="@{/login}" method="post">
        <div>
            <input type="text" name="username" placeholder="Username"/>
        </div>
        <div>
            <input type="password" name="password" placeholder="Password"/>
        </div>
        <input type="submit" value="Log in" />
    </form>
    </body>
    </html>
    ```
2. 编写LoginController
    ```java
    @Controller
    public class LoginController {
        @GetMapping("/login")
        public String login(){
            return "login" ;
        }
    }
    ```
3. 配置SpringSecurity
    ```text
    http.formLogin(form -> form
        .loginPage("/login")
        .permitAll()
    )
    ```
### 自定义AuthenticationManager 
1. 方式一 Publish AuthenticationManager
   ```java
   @EnableWebSecurity
   public class SecurityConfig {
      @Bean
      public AuthenticationManager authenticationManager(
              UserDetailsService userDetailsService,
              PasswordEncoder passwordEncoder) {
         DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
         authenticationProvider.setUserDetailsService(userDetailsService);
         authenticationProvider.setPasswordEncoder(passwordEncoder);
         //
         ProviderManager providerManager = new ProviderManager(authenticationProvider);
         providerManager.setEraseCredentialsAfterAuthentication(false);
         //
         return providerManager;
      }
   }
   ```
2. 方式二 Configure global AuthenticationManagerBuilder
   ```java
   @EnableWebSecurity
   public class SecurityConfig {
   
      @Autowired
	  public void configure(AuthenticationManagerBuilder builder) {
		builder.eraseCredentials(false);
	  }
   }
   ```
### Controller 自定义登录方法
1. 配置登录
   ```text
    http.authorizeHttpRequests(authorize -> authorize
          .requestMatchers(HttpMethod.POST,"/user/login").permitAll()
          .anyRequest().authenticated()
      )
   ```
2. 编写登录方法
   ```java
    @PostMapping("/login")
    public Authentication login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response){
        //
        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        //
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequest.username(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(token);
        //
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        return authentication ;
    }
   ```
3. 注意如果启用了formLogin则Controller中的login地址不能是/login, 否则登录会被UsernamePasswordAuthenticationFilter拦截
4. 打印详细日志：```logging.level.org.springframework.security=TRACE```
