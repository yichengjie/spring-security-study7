package com.yicj.study.mybatis.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataChangeRecorderInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.yicj.study.mybatis.support.datascope.handler.DataScopeHandler;
import com.yicj.study.mybatis.support.datascope.interceptor.DataScopeInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(/*DataScopeHandler dataScopeHandler*/) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DataChangeRecorderInnerInterceptor dataChangeRecorderInnerInterceptor = new DataChangeRecorderInnerInterceptor();
        // 配置安全阈值，例如限制批量更新或插入的记录数不超过 1000 条
        dataChangeRecorderInnerInterceptor.setBatchUpdateLimit(1000);
        interceptor.addInnerInterceptor(dataChangeRecorderInnerInterceptor);
        // 添加非法SQL拦截器
        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //
//        interceptor.addInnerInterceptor(
//            new DataScopeInnerInterceptor(dataScopeHandler)
//        );
        return interceptor;
    }
}
