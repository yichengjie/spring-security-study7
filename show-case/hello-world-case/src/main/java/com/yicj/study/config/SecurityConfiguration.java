package com.yicj.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
@EnableWebSecurity/*(debug = true)*/
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
        } ;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //@formatter:off
        http.authorizeHttpRequests((authorize) ->
            authorize
                .requestMatchers("/hello/index").permitAll()
                .anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        //.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .userDetailsService(userDetailsManager())
        ;
        //@formatter:on
        return http.build() ;
    }

    //@Bean
    private InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user) ;
    }
}
