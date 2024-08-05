1. 简单注解类
    ```java
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface ErasePasswordAnno {
    @AliasFor(attribute = "expression")
    String value() default "" ;
    
        String expression() default "" ;
    }
    ```
2. 嵌套注解类
   ```java
   @Target({ElementType.METHOD})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   @ErasePasswordAnno
   public @interface MapErasePasswordAnno {
       @AliasFor(annotation = ErasePasswordAnno.class, attribute = "value")
       String value() default "['recordList']" ;
   
       @AliasFor(annotation = ErasePasswordAnno.class, attribute = "expression")
       String expression() default "['recordList']" ;
   }
   ```
3. 编写测试代码
   ```java
   @Slf4j
   public class AliasForAnnoTest {
   
       @Test
       void simple() throws NoSuchMethodException {
           Method method = MapErasePasswordAnnoTest.class.getMethod("hello");
           ErasePasswordAnno annotation = AnnotationUtils.getAnnotation(method, ErasePasswordAnno.class);
           Assertions.assertNotNull(annotation);
           log.info("annotation value : {}", annotation.value());
           log.info("annotation expression : {}", annotation.value());
       }
   
       @Test
       void complex() throws NoSuchMethodException {
           Method method = MapErasePasswordAnnoTest.class.getMethod("hello2");
           ErasePasswordAnno annotation = AnnotatedElementUtils.findMergedAnnotation(method, ErasePasswordAnno.class);
           Assertions.assertNotNull(annotation);
           log.info("annotation value : {}", annotation.value());
           log.info("annotation expression : {}", annotation.value());
       }
       
       @ErasePasswordAnno("username")
       public void hello(){
   
       }
   
       @MapErasePasswordAnno
       public void hello2(){
   
       }
   }
   ```