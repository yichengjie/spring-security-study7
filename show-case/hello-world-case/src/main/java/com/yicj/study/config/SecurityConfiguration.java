package com.yicj.study.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p>
 * SecurityConfiguration
 * </p>
 *
 * @author yicj
 * @since 2024年08月03日 9:21
 */
@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfiguration {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        String [] configGetUrlArray = {
                "/actuator/info",
                "/favicon.ico",
                // swagger相关
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**",
                "/doc.html",
                "/v3/api-docs/swagger-config"
        } ;
        return (web) -> {
            WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();
            ignoring.requestMatchers(HttpMethod.GET, configGetUrlArray) ;
            // ignoring.requestMatchers(HttpMethod.POST, "/auth/login") ;
        } ;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //@formatter:off
        http.authorizeHttpRequests((authorize) ->
            authorize
                .requestMatchers("/hello/index").permitAll()
                .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                .anyRequest()
                    .authenticated()
        )
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        //.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //.userDetailsService(userDetailsManager())
        ;
        // CsrfFilter 注意 post 提交json 会直接报错
        //@formatter:on
        return http.build() ;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    @Autowired
    public void configure(AuthenticationManagerBuilder builder){
        builder.eraseCredentials(true) ;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(true);
        return providerManager ;
    }


}
