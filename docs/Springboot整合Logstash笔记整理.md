1. 编写核心配置类
   ```java
   @Configuration(proxyBeanMethods = false)
   @EnableConfigurationProperties({LogstashProperties.class})
   public class LogstashConfig implements InitializingBean, EnvironmentAware {
      private final Logger log = LoggerFactory.getLogger(LogstashConfig.class);
      private Environment env ;
      @Autowired
      private LogstashProperties logstashProperties ;
   
      @Override
      public void afterPropertiesSet() throws Exception {
         String appEnv = Optional.ofNullable(env.getProperty("spring.profiles.active")).orElse("dev") ;
         String appName = Optional.ofNullable(env.getProperty("spring.application.name")).orElse("unknown") ;
         String appPort = Optional.ofNullable(env.getProperty("server.port")).orElse("8080") ;
         this.init(appEnv, appName, appPort);
      }
   
      private void init(String appEnv, String appName, String appPort) throws JsonProcessingException {
         if (!logstashProperties.isEnabled() || StringUtils.isBlank(logstashProperties.getHost())) {
            log.info("[LogstashConfig] Stash log disabled.");
            return;
         }
         LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory() ;
         Map<String, String> map = new HashMap<>();
         map.put("app_env", appEnv) ;
         map.put("app_name", appName) ;
         map.put("app_port", appPort) ;
         String customFields = new ObjectMapper().writeValueAsString(map);
         LogstashEncoder logstashEncoder = new LogstashEncoder();
         logstashEncoder.setCustomFields(customFields);
         ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
         throwableConverter.setRootCauseFirst(true);
         logstashEncoder.setThrowableConverter(throwableConverter);
         logstashEncoder.setContext(loggerContext);
         logstashEncoder.setIncludeCallerData(true);
         LogstashTcpSocketAppender logstashTcpSocketAppender = getLogstashTcpSocketAppender(loggerContext, logstashEncoder);
         AsyncAppender asyncLogstashAppender = new AsyncAppender();
         asyncLogstashAppender.setContext(loggerContext);
         asyncLogstashAppender.setName("ASYNC_LOGSTASH");
         asyncLogstashAppender.setQueueSize(logstashProperties.getQueueSize());
         asyncLogstashAppender.addAppender(logstashTcpSocketAppender);
         asyncLogstashAppender.setIncludeCallerData(true);
         asyncLogstashAppender.start();
         loggerContext.getLogger("ROOT").addAppender(asyncLogstashAppender);
         log.info("[LogstashConfig] Stash log init done.");
      }
   
      private LogstashTcpSocketAppender getLogstashTcpSocketAppender(
              LoggerContext loggerContext, LogstashEncoder logstashEncoder) {
         LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
         logstashTcpSocketAppender.setName("LOGSTASH");
         logstashTcpSocketAppender.setContext(loggerContext);
         logstashTcpSocketAppender.addDestination(logstashProperties.getHost() + ":" + logstashProperties.getPort());
         logstashTcpSocketAppender.setEncoder(logstashEncoder);
         logstashTcpSocketAppender.setIncludeCallerData(true);
         logstashTcpSocketAppender.start();
         return logstashTcpSocketAppender;
      }
   
      @Override
      public void setEnvironment(Environment environment) {
         this.env = environment ;
      }
   }
   ```
2. 编写Properties类
   ```java
   @Data
   @ConfigurationProperties(prefix = "sss.logstash")
   public class LogstashProperties {
      private boolean enabled ;
      private String host ;
      private String port ;
      private int queueSize ;
   }
   ```
3. 配置文件
   ```yaml
   sss:
     logstash:
       enabled: true
       host: 127.0.0.1
       port: 8888
       queueSize: 512
   ```
### 其他问题
1. logstash与logback的版本兼容问题
    ```text
    a. logstash-logback-encoder>=7.4需要logback>=1.3
    b. 将logstash-logback-encoder降级到7.3（支持logback 1.2)，并继续使用logback 1.2
    ```
2. logstash 配置 (logstash.conf)
   ```conf
   input {
     tcp {
       mode => "server"
       host => "0.0.0.0"
       port => 8888
       codec => json_lines
     }
   }
   output {
     elasticsearch {
       hosts => ["http://localhost:9200"]
       index => "log-%{[app_env]}-%{+YYYY.MM.dd}"
     }
   }
   ```
