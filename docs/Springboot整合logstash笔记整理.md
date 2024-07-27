1. 参考文章：https://blog.csdn.net/qq_33191919/article/details/138118713

### 注意事项
1. logstash与logback的版本兼容问题
    ```text
    a. logstash-logback-encoder>=7.4需要logback>=1.3
    b. 将logstash-logback-encoder降级到7.3（支持logback 1.2)，并继续使用logback 1.2
    ```
