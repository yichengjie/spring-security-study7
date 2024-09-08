1. 版本升级：
    ```text
    https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
    ```
2.  获取lambda 方法名称
   ```text
   SFunction<UserInfo, Boolean> func = UserInfo::getDeletedFlag;
   Method method = func.getClass().getDeclaredMethod("writeReplace");
   method.setAccessible(true);
   SerializedLambda lambda = (SerializedLambda)method.invoke(func);
   String methodName = lambda.getImplMethodName();
   log.info("methodName:{}", methodName);
   ```