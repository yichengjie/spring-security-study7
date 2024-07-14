### HttpSecurityConfiguration#httpSecurity 构建 HttpSecurity 对象
1. 构建 LazyPasswordEncoder 对象
2. 构建 AuthenticationManagerBuilder 对象
3. 构建 HttpSecurity 对象
4. 配置 HttpSecurity 对象

### 构建 LazyPasswordEncoder 对象
1. 如果系统配置 PasswordEncoder 对象， 则使用系统配置的 PasswordEncoder 对象
2. 否则使用 PasswordEncoderFactories.createDelegatingPasswordEncoder()创建一个默认的 PasswordEncoder 对象

### 构建 AuthenticationManagerBuilder 对象
1. UserDetailsServiceAutoConfiguration#inMemoryUserDetailsManager