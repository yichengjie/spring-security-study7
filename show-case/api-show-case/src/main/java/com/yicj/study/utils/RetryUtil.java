package com.yicj.study.utils;

import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * <p>
 * RetryUtils
 * </p>
 *
 * @author yicj
 * @since 2024/9/3 21:52
 */
public class RetryUtil {

    public static RetryTemplate retryTemplate(){
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        // 最大重试次数
        retryPolicy.setMaxAttempts(3);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        // 倍数
        backOffPolicy.setMultiplier(2);
        // 初始间隔时间
        backOffPolicy.setInitialInterval(1000);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }

}
