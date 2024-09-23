package com.yicj.study.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yicj
 * @since 2024/08/26
 */
// https://www.ruanyifeng.com/blog/2019/04/oauth-grant-types.html?ivk_sa=1024320u
// https://www.51cto.com/article/796745.html
// https://juejin.cn/post/7345105899150802956
@SpringBootApplication
public class AuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args) ;
    }
}
