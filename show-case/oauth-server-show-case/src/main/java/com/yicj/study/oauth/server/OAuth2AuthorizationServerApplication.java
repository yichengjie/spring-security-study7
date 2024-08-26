package com.yicj.study.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * OAuth2AuthorizationServerApplication
 * </p>
 *
 * @author yicj
 * @since 2024/08/26 16:46
 */
// https://www.ruanyifeng.com/blog/2019/04/oauth-grant-types.html?ivk_sa=1024320u
@SpringBootApplication
public class OAuth2AuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2AuthorizationServerApplication.class, args) ;
    }
}
