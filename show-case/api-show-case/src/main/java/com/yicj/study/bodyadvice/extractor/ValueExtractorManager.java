//package com.yicj.study.bodyadvice.extractor;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * <p>
// * ValueExtractorManager
// * </p>
// *
// * @author yicj
// * @since 2024年07月26日 10:33
// */
//@Component
//public class ValueExtractorManager implements ApplicationContextAware {
//
//    private static ApplicationContext context ;
//
//    public static Object extract(Object data, String extractorName){
//        ValueExtractor bean = context.getBean(extractorName, ValueExtractor.class);
//        return bean.extract(data) ;
//    }
//
//
//    @Override
//    public void setApplicationContext(ApplicationContext context) throws BeansException {
//        this.context = context ;
//    }
//}
