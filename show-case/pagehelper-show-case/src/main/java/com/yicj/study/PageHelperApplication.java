package com.yicj.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * PageHelperApplication
 * </p>
 *
 * @author yicj
 * @since 2024/8/15 22:33
 */
// https://juejin.cn/post/7402548056284905483?share_token=139EBB40-E724-4C32-A954-71B65A4135A5
@MapperScan("com.yicj.study.mapper")
@SpringBootApplication
public class PageHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageHelperApplication.class, args);
    }
}
