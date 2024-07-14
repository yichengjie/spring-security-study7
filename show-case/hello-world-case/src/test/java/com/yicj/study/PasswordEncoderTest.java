package com.yicj.study;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * PasswordEncoderTest
 * </p>
 *
 * @author yicj
 * @since 2024年07月14日 15:06
 */
@Slf4j
class PasswordEncoderTest {

    private static final String CUSTOM_PASSWORD_PREFIX = "{custom-noop}";
    @Test
    void test1(){
        PasswordEncoder encoder = new BCryptPasswordEncoder() ;
        String encode = encoder.encode("123456") ;
        log.info("encode : {}", encode);
    }

    @Test
    void test2(){
        PasswordEncoder encoder = new Pbkdf2PasswordEncoder() ;
        String encode = encoder.encode("123456") ;
        log.info("encode : {}", encode);
    }

    @Test
    void test3(){
        var encodedPassword = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return CUSTOM_PASSWORD_PREFIX + rawPassword ;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(CUSTOM_PASSWORD_PREFIX + rawPassword) ;
            }
        } ;
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        String encodingId = "bcrypt";
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("custom-noop", encodedPassword);
        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder(encodingId, encoders);
        String encode = encoder.encode("123456") ;
        log.info("encode : {}", encode);
    }


    @Test
    void test4(){
        String content = "{bcrypt}$2a$10$Ily8UHMcGxOfa9HujhPHVOlz1.AulCLTxuxr/.D.AZl5EebqMBmDC" ;
        var encodedPassword = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return CUSTOM_PASSWORD_PREFIX + rawPassword ;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(CUSTOM_PASSWORD_PREFIX + rawPassword) ;
            }
        } ;
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        String encodingId = "bcrypt";
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("custom-noop", encodedPassword);
        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder(encodingId, encoders);
        //
        boolean matches = encoder.matches("123456", content) ;
        log.info("matches : {}", matches);
    }

}
