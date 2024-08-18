1. 添加依赖
    ```text
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>
    ```
2. 添加配置
    ```yml
    spring:
      liquibase:
        contexts: dev,test
        enabled: true
    ```
3. 编写liquibase配置类
    ```java
    @Configuration
    @EnableConfigurationProperties(LiquibaseProperties.class)
    public class LiquibaseConfig {
        @Bean
        public SpringLiquibase liquibase(DataSource dataSource, LiquibaseProperties properties) {
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            //指定changelog的位置，这里使用的一个master文件引用其他文件的方式
            liquibase.setChangeLog("classpath:liquibase/master.xml");
            liquibase.setContexts(properties.getContexts());
            liquibase.setDefaultSchema(properties.getDefaultSchema());
            liquibase.setDropFirst(properties.isDropFirst());
            liquibase.setShouldRun(properties.isEnabled());
            liquibase.setChangeLogParameters(properties.getParameters());
            liquibase.setRollbackFile(properties.getRollbackFile());
            liquibase.setShouldRun(true);
            return liquibase;
        }
    }
    ```
4. 编写master.xml文件(注意includeAll配置的路径与changelog文件的路径)
    ```text
    <?xml version="1.0" encoding="utf-8"?>
    <databaseChangeLog
            xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.6.xsd">
        <property name="now" value="now()" dbms="h2"/>
        <property name="now" value="now()" dbms="mysql"/>
        <property name="floatType" value="float4" dbms="postgresql, h2"/>
        <property name="floatType" value="float" dbms="mysql, oracle, mssql, mariadb"/>
        <property name="clobType" value="clob" dbms="h2"/>
        <property name="clobType" value="clob" dbms="mysql, oracle, mssql, mariadb, postgresql"/>
        <property name="uuidType" value="varchar(36)" dbms="h2, mysql, mariadb"/>
        <includeAll path="liquibase/changelog/"/>
    </databaseChangeLog>
    ```
5. 编写changelog文件 00000000000000_init_schema.xml
   ```text
   <?xml version="1.0" encoding="utf-8"?>
   <databaseChangeLog
           xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
           xmlns:ext="http://www.liquibase.org/xml/ns/dbchangelog-ext"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.6.xsd
                           http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd">
       <property name="autoIncrement" value="true"/>
       <changeSet author="system" id="00000000000001" context="dev">
           <createTable tableName="jhi_date_time_wrapper">
               <column  name="id" type="BIGINT">
                   <constraints primaryKey="true" primaryKeyName="jhi_date_time_wrapperPK"/>
               </column>
               <column name="instant" type="timestamp"/>
               <column name="local_date_time" type="timestamp"/>
               <column name="offset_date_time" type="timestamp"/>
               <column name="zoned_date_time" type="timestamp"/>
               <column name="local_time" type="time"/>
               <column name="offset_time" type="time"/>
               <column name="local_date" type="date"/>
           </createTable>
       </changeSet>
   </databaseChangeLog>
   ```