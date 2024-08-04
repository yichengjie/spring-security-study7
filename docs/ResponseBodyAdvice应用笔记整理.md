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
         boolean presentController = method.isAnnotationPresent(ErasePasswordAnno.class);
         // 获取类中是否有RestController注解
         boolean restController = returnType.getDeclaringClass().isAnnotationPresent(RestController.class);
         // 支持修改结果
         boolean returnTypeSupport = ResultEntity.class.isAssignableFrom(returnType.getParameterType());
         return restController && presentController && returnTypeSupport;
      }
   
      @Override
      public Object beforeBodyWrite(
              Object body, MethodParameter returnType,
              MediaType selectedContentType,
              Class<? extends HttpMessageConverter<?>> selectedConverterType,
              ServerHttpRequest request,
              ServerHttpResponse response) {
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
         Object value = Optional.ofNullable(this.getSpelExpression(method))
                 .map(expression -> {
                    EvaluationContext context = new StandardEvaluationContext(data);
                    ExpressionParser parser = new SpelExpressionParser();
                    return parser.parseExpression(expression).getValue(context, Object.class);
                 })
                 .orElse(data);
         this.erasePassword(value);
         return body ;
      }
   
      private String getSpelExpression(Method method){
         return Optional.ofNullable(AnnotationUtils.getAnnotation(method, ErasePasswordAnno.class))
                 .map(ErasePasswordAnno::expression)
                 .filter(StringUtils::isNotBlank)
                 .map(String::trim)
                 .orElse(null);
      }
   
      private void erasePassword(Object data){
         if (data instanceof Collection<?>){
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
   @Target({ElementType.METHOD})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface ErasePasswordAnno {
      @AliasFor(attribute = "expression")
      String value() default "" ;
   
      String expression() default "" ;
   }
   ```
3. ResultEntity 类
   ```java
   @Data
   public class ResultEntity<T> implements Serializable {
      private Integer code;
      private String msg;
      private T data;
      
      public static ResultEntity<Object> success(Object object) {
         ResultEntity<Object> resultEntity = new ResultEntity<>();
         resultEntity.setCode(TeamCreamEnum.GLOBAL_SUCCESS.getCode());
         resultEntity.setMsg(TeamCreamEnum.GLOBAL_SUCCESS.getMessage());
         if (object instanceof Page) {
            Page<Object> temp = (Page)object;
            HashMap<String, Object> data = new HashMap();
            data.put("curPageNo", temp.getPageNum());
            data.put("pageSize", temp.getPageSize());
            data.put("recordCount", temp.getTotal());
            data.put("pageCount", temp.getPages());
            data.put("recordList", object);
            resultEntity.setData(data);
         } else {
            resultEntity.setData(object);
         }
         return resultEntity;
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
    
        @ErasePasswordAnno("['recordList']")
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