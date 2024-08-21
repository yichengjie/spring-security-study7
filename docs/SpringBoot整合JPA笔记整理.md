### 整合JPA
1. 添加依赖
    ```xml
     <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    ```
2. application.yml配置数据源
    ```yml
    spring:
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/study?useSSL=false&serverTimezone=UTC
        username: root
        password: root
        jpa:
          show-sql: true
    ```
3. 配置JPA
    ```java
    @Configuration
    @EnableTransactionManagement
    @EntityScan(basePackages = "com.yicj.study.jpa.entity")
    @EnableJpaRepositories(basePackages = "com.yicj.study.jpa.repository")
    public class DatasourceConfig {
    
    }
    ```
4. 编写Entity
    ```java
    @Data
    @Entity
    @Table(name = "student", schema = "jpa-learn")
    public class Student extends AbstractAuditingEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Integer id;
        @Column(name = "stu_name")
        private String name;
        @Column(name = "stu_number")
        private String number;
        @Column(name = "age")
        private Integer age;
        //
        @ManyToMany(cascade = {CascadeType.ALL})
        @JoinTable(name = "student_teacher_relation",
                joinColumns = @JoinColumn(name = "student_id"),
                inverseJoinColumns = @JoinColumn(name = "teacher_id"))
        private List<Teacher> teachers;
    }
    ```
5. 编写Repository
    ```java
    @Repository
    public interface StudentRepository
            extends JpaRepositoryImplementation<FeatureMessage, String> {
    }
    ```
### 创建对应的Specification
1. 以如下SQL为例
    ```text
    select * from student AS s where s.name = 'ss007'
    ```
2. 创建Specification
    ```java
    @UtilityClass
    public class StudentSpecification {
        // 以学生名称查询
        public static Specification<Student> hasNumber(String number) {
            return new Specification<Student>() {
                @Override
                public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get(Student_.number), number);
                }
            };
        }
        //以学生年龄查询并以年龄排序
        public static Specification<Student> ageBetween(Integer minAge, Integer maxAge) {
            return (root, query, criteriaBuilder) -> {
                Predicate between = criteriaBuilder.between(root.get(Student_.age), minAge, maxAge);
                query.orderBy(criteriaBuilder.desc(root.get(Student_.age)));
                return between;
            };
        }
        //以学生的老师查询
        public static Specification<Student> hasTeacher(String teacher) {
            return (root, query, criteriaBuilder) -> {
                Join<Student, Teacher> stuTeachers = root.join(Student_.teachers);
                return criteriaBuilder.equal(stuTeachers.get(Teacher_.name), teacher);
            };
        }
    }
    ```
3. 编写Service
    ```java
    @Service
    public class MyServiceImpl implements MyService {
        @Autowired
        private JpaStudentRepository studentRepository;
        @Override
        public Page<Student> filterStudents(FilterStudentRequest filter, Pageable pageable) {
    
            Specification<Student> spec = Specification.where(null);
    
            if (StrUtil.isNotBlank(filter.getName())) {
                spec = spec.and(StudentSpecification.nameLike(filter.getName()));
            }
            if (!Objects.isNull(filter.getAgeMin()) && !Objects.isNull(filter.getAgeMax())) {
                spec = spec.and(StudentSpecification.ageBetween(filter.getAgeMin(), filter.getAgeMax()));
            }
            if (StrUtil.isNotBlank(filter.getNumber())) {
                spec = spec.and(StudentSpecification.hasNumber(filter.getNumber()));
            }
            if (StrUtil.isNotBlank(filter.getTeacher())) {
                spec = spec.and(StudentSpecification.hasTeacher(filter.getTeacher()));
            }
            return studentRepository.findAll(spec, pageable);
        }
    }
    ```
### 配置jpamodelgen工具
1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-jpamodelgen</artifactId>
        <version>${jpamodelgen.version}</version>
    </dependency>
    ```
2. 配置APT
    ```xml
    <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-compiler-plugin</artifactId>
       <version>${maven.plugin.version}</version>
       <configuration>
           <annotationProcessorPaths>
               <path>
                   <groupId>org.hibernate.orm</groupId>
                   <artifactId>hibernate-jpamodelgen</artifactId>
                   <version>${jpamodelgen.version}</version>
               </path>
           </annotationProcessorPaths>
       </configuration>
    </plugin>
    ```