package com.yicj.study.service.impl;

import com.yicj.study.service.HelloService;
import org.springframework.beans.factory.BeanNameAware;

/**
 * <p>
 * HelloServiceImpl
 * </p>
 *
 * @author yicj
 * @since 2024年07月13日 20:54
 */
public class HelloServiceImpl implements HelloService, BeanNameAware {

    private String beanName ;

    @Override
    public String sayHello(String name) {
        return "hello, " + name + " !" ;
    }

    @Override
    public String getBeanName() {
        return this.beanName;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = beanName ;
    }
}
