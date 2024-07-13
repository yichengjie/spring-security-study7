//package com.yicj.study.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * <p>
// * ProjectConfiguration
// * 配置 UserDetailsService 和 PasswordEncoder 方式来配置Spring Security
// * </p>
// *
// * @author yicj
// * @since 2024年07月13日 16:06
// */
//@Configuration
//public class ProjectStyle1Configuration {
//
//    private static final String CUSTOM_PASSWORD_PREFIX = "{custom-noop}";
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        var userDetailsService = new InMemoryUserDetailsManager() ;
//
//        var user = User.withUsername("admin")
//                .password(CUSTOM_PASSWORD_PREFIX + "123")
//                .authorities("read")
//                .build();
//
//        userDetailsService.createUser(user) ;
//
//        return userDetailsService ;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        var encodedPassword = new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return CUSTOM_PASSWORD_PREFIX + rawPassword ;
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return encodedPassword.equals(CUSTOM_PASSWORD_PREFIX + rawPassword) ;
//            }
//        } ;
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        String encodingId = "bcrypt";
//        encoders.put(encodingId, new BCryptPasswordEncoder());
//        encoders.put("custom-noop", encodedPassword);
//        return new DelegatingPasswordEncoder(encodingId, encoders);
//    }
//}
