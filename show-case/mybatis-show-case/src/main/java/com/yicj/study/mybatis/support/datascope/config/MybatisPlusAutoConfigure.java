package com.yicj.study.mybatis.support.datascope.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.yicj.study.mybatis.support.datascope.handler.DataScopeHandler;
import com.yicj.study.mybatis.support.datascope.interceptor.DataScopeInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusAutoConfigure {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(
                            DataScopeHandler dataScopeHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(
            new DataScopeInnerInterceptor(dataScopeHandler)
        );
        return interceptor;
    }
}