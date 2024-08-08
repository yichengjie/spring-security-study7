### Mapper接口的代理对象构建流程 (MybatisAutoConfiguration启动加载)
1. 构建DefaultSqlSessionFactory : SqlSessionFactoryBean#getObject => buildSqlSessionFactory() 构建 DefaultSqlSessionFactory
2. SqlSessionTemplate(sqlSessionFactory) 构建 SqlSessionTemplate
3. AutoConfiguredMapperScannerRegistrar#registerBeanDefinitions 注册 MapperScannerConfigurer
4. MapperScannerConfigurer#postProcessBeanDefinitionRegistry 注册 MapperFactoryBean
5. ClassPathMapperScanner#processBeanDefinitions向spring容器注册 MapperFactoryBean
6. MapperFactoryBean#getObject => sqlSessionTemplate.getMapper(this.mapperInterface)
7. MapperProxyFactory#newInstance(new MapperProxy<>(sqlSession, mapperInterface, methodCache))
8. Proxy.newProxyInstance动态生成 MapperProxy 代理对象

### SqlSessionFactory 构建流程
1. SqlSessionFactoryBean#buildSqlSessionFactory
2. SqlSessionFactoryBuilder 属性填充
3. SqlSessionFactoryBuilder#build 构建 DefaultSqlSessionFactory

### Mybatis的Mapper代理对象执行流程
1. MapperProxy.invoke(proxy, method, args, sqlSessionTemplate)
2. MapperMethod.execute(sqlSessionTemplate, args)
3. SqlSessionTemplate.delete(statement, param)


### SqlSessionTemplate#delete 执行流程
1. SqlSessionTemplate.delete(statement, param)
2. SqlSessionInterceptor.invoke(invocation)
3. 获取 SqlSession: SqlSessionUtils.getSqlSession
4. DefaultSqlSession.delete(statement, param)

### SqlSessionUtils#getSqlSession(sessionFactory, executorType, exceptionTranslator)
1. TransactionSynchronizationManager.getResource(sessionFactory)
2. DefaultSqlSessionFactory.openSession(executorType, exceptionTranslator)
3. DefaultSqlSessionFactory.openSessionFromDataSource(dataSource, executorType, exceptionTranslator)
4. registerSessionHolder(session, sessionFactory, exceptionTranslator
5. 返回 DefaultSqlSession 对象

### DefaultSqlSessionFactory#openSessionFromDataSource 获取 sqlSession
1. configuration.getEnvironment() 获取Environment
2. environment.getTransactionFactory() 获取 SpringManagedTransactionFactory
3. transactionFactory.newTransaction(dataSource, level, autoCommit) => SpringManagedTransaction(dataSource)
4. configuration.newExecutor(tx, execType) => CachingExecutor(new SimpleExecutor())
5. 返回SqlSession: new DefaultSqlSession(configuration, executor, autoCommit)

### registerSessionHolder(session, sessionFactory, exceptionTranslator)
1. 判断当前线程是否支持事务
2. 判断environment的transactionFactory是否为SpringManagedTransactionFactory
3. 如果1、2都成立则将session注册到TransactionSynchronizationManager中与当前线程绑定

