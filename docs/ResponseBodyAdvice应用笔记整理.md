1. 编写核心处理逻辑
    ```java
    @Slf4j
    @ControllerAdvice
    public class ErasePasswordBodyAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            // 如果返回结果
            Method method = returnType.getMethod();
            if (method == null){
                return false;
            }
            // 获取类中是否有 ErasePasswordAnno 注解
            boolean presentController = method.isAnnotationPresent(ErasePasswordAnno.class) || method.isAnnotationPresent(MapErasePasswordAnno.class);
            // 获取类中是否有RestController注解
            boolean restController = returnType.getDeclaringClass().isAnnotationPresent(RestController.class);
            // 支持修改结果
            boolean returnTypeSupport = ResultEntity.class.isAssignableFrom(returnType.getParameterType());
            return restController && presentController && returnTypeSupport;
        }
        @Override
        public Object beforeBodyWrite(
                Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
            if (body == null){
                return null;
            }
            Method method = returnType.getMethod();
            if (method == null){
                return body;
            }
            Object data = ((ResultEntity<?>) body).getData();
            if (data == null){
                return body;
            }
            Object value = Optional.ofNullable(this.getExtractorName(method))
                    .filter(StringUtils::isNotBlank)
                    .map(String::trim)
                    .map(name -> ValueExtractorManager.extract(data, name))
                    .orElse(data);
            this.erasePassword(value);
            return body ;
        }
        private String getExtractorName(Method method){
            return Optional.ofNullable(method.getAnnotation(ErasePasswordAnno.class))
                    .map(ErasePasswordAnno::value)
                    .filter(StringUtils::isNotBlank)
                    .map(String::trim)
                    .orElseGet(()->{
                        MapErasePasswordAnno pageErasePasswordAnno = method.getAnnotation(MapErasePasswordAnno.class);
                        return pageErasePasswordAnno != null ? pageErasePasswordAnno.value() : null ;
                    });
        }
        private void erasePassword(Object data){
            if (data instanceof Iterator<?>){
                for (Object item : (Iterable<?>) data){
                    this.doErasePassword(item);
                }
            }else {
                doErasePassword(data);
            }
        }
        private void doErasePassword(Object data){
            if (data instanceof BasicUser){
                ((BasicUser) data).setPassword("******");
            }
            //todo 其他类型的数据处理
        }
    }
    ```
2. 编写注解类
    ```java
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Indexed
    public @interface ErasePasswordAnno {
        String value() default "" ;
    }
    
    @Target({ElementType.METHOD, })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @ErasePasswordAnno
    public @interface MapErasePasswordAnno {
    
        @AliasFor(annotation = ErasePasswordAnno.class, attribute = "value")
        String value() default "mapValueExtractor" ;
    }
    ```
3. 编写extractor
    ```java
    public interface ValueExtractor<T>{
        Object extract(T data) ;
    }
    @Component("mapValueExtractor")
    public class MapValueExtractor implements ValueExtractor<Map<String,Object>> {
        @Override
        public Object extract(Map<String, Object> data) {
            return data.get("recordList");
        }
    }
    @Component
    public class ValueExtractorManager implements ApplicationContextAware {
        private static ApplicationContext context ;
        public static Object extract(Object data, String extractorName){
            ValueExtractor bean = context.getBean(extractorName, ValueExtractor.class);
            return bean.extract(data) ;
        }
        @Override
        public void setApplicationContext(ApplicationContext context) throws BeansException {
            this.context = context ;
        }
    }
    ```
4. 编写测试类
    ```java
    @RestController
    @RequestMapping("/hello")
    public class HelloController {
   
        @ErasePasswordAnno
        @GetMapping("/detail/{id}")
        public ResultEntity<BasicUser> findById(@PathVariable String id){
            BasicUser basicUser = new BasicUser() ;
            basicUser.setId(id);
            basicUser.setName("yicj");
            basicUser.setPassword("123456");
            return ResultEntity.success(basicUser);
        }
    
        @MapErasePasswordAnno
        @GetMapping("/listForPage")
        public ResultEntity findPage() {
            Page<BasicUser> basicUserPage = new Page<>(1, 10, true);
            basicUserPage.setPages(1) ;
            basicUserPage.setTotal(1);
            //
            BasicUser basicUser = new BasicUser() ;
            basicUser.setId("1");
            basicUser.setName("yicj");
            basicUser.setPassword("123456");
            //
            basicUserPage.add(basicUser);
            //
            return ResultEntity.success(basicUserPage);
        }
    
        @ErasePasswordAnno
        @GetMapping("/list")
        public ResultEntity findList() {
            BasicUser basicUser = new BasicUser() ;
            basicUser.setId("1");
            basicUser.setName("yicj");
            basicUser.setPassword("123456");
            //
            List<BasicUser> basicUserList = List.of(basicUser) ;
            return ResultEntity.success(basicUserList);
        }
    }
    ```
5. 测试结果
    ```json
    {
      "code": 200,
      "msg": "成功",
      "data": [
        {
          "id": "1",
          "name": "yicj",
          "password": "******"
        }
      ]
    }
    ```