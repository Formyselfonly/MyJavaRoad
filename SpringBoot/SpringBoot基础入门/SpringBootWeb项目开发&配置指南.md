[TOC]



## Idea配置

1. Java

   jdk : C:\Program Files\Java\jdk1.8.0_321

2. maven

   ![image-20221029194457696](D:\Users\Pictures\Saved Pictures\image-20221029194457696.png)
   

## 基础知识

1. 开发思路

   SpringBoot约定大于配置,用约 定最好,然后要改配置在yml里面改就行,不改的就会采取默认值. 遵守开发约定,重心放在开发就行.   最多就是改web config创建一个web config类,不过也只是implement webmvc然后override一些类即可,其他没有override的还是采取默认

2. HttpServlet

   HttpServlet是用户与服务器交互的接口,HttpServlet读取Http请求的内容,并把Http请求直接封装到HttpServlet对象中，大大简化了HttpServlet解析请求数据的工作量

3. HttpSession

   一个session就是一系列某用户和服务器间的通讯
   服务器会为每一个用户 创建一个独立的HttpSession

   当用户第一次访问Servlet时,服务器端会给用户创建一个独立的Session
   并且生成一个Session ID,这个Session ID在响应浏览器的时候会被装进cookie中,从而被保存到浏览器中
   当用户再一次访问Servlet时,请求中会携带着cookie中的Session ID去访问
   服务器会根据这个Session ID去查看是否有对应的Session对象
   有就拿出来使用;没有就创建一个Session(相当于用户第一次访问)

4. Model

   你一定知道Model类，在控制器中，数据会存放到Model对象中，当需要生成HTML的时候，模板引擎会根据名字来定位数据. ${msg}前端可以这样取得model的值.    后端可以getAttribute,addAttribute

5. 前端开发

   一般直接用别人的代码,然后自己在浏览器里面F12检查,点击要修改的部分. 然后F12里面看对应的源码,在idea里面搜索找到源代码,然后修改为自己想要的值即可.

6. 数据库连接池

   ![image-20221025142959218](D:\Users\Pictures\Saved Pictures\image-20221025142959218.png)

   总结:它是数据库与用户之间的中介,降低了资源消耗.  而且还有额外的功能,比如Druid不仅是数据库连接池,还有监控作用,能够进行Web和URl,Sql监控

   数据库连接池主要是用于和数据库进行连接、它可以负责管理、分配和释放数据库的连接

   - 数据库连接池定义：

     新建一条用户到数据库的连接耗费资源，那么为什么不重复利用已有的连接呢？所以就有了数据库连接池。用来负责分配、管理和释放数据库连接，它允许应用程序重复使用一个现有的数据库连接，而不是再重新建立一个。

   - 为什么需要数据库连接池:

     因为数据库在进行连接打开和关闭的时候对性能上有很大的消耗。当使用了数据库连接池它可以进行一些设置。如数据库和连接池有多少个长久的连接数量，也就是连接之后就不在关闭释放的路径，也叫空闲连接点

   - 用什么数据库连接池:

     国外一般用hikari也就是SpringBoot默认的数据库连接池,国内用Druid(alibaba的)即可

7. Mysql server Mysql driver JDBC Mybatis Mybatis-plus区别

   - Mysql server是Mysql应用,是安装在电脑上的数据库
   - Mysql driver是Mysql驱动,是dependency
   - JDBC（Java Database Connectivity）,Java数据库连接，是客户端访问数据库的接口,我们通常说的JDBC是面向关系型数据库的。
   - MyBatis也是数据库接口,它可以看作JDBC的升级,它是一个持久层ORM框架，何为ORM，它的全称为Object Relational Mapping ，也就是对象关系映射，通俗点讲就是让实体类的属性名与数据库中的列名一一对应，通过这样的方法封装对象。 mybatis框架的用法有两种：一种是通过xml配置的方式，一种是通过注解的方式。xml需要放在resource目录下与dao的路径名一样的包中，否则xml配置不会生效，而注解的话只需要在dao中需要与数据库交互的方法上添加相应的注解即可
   - Mybatis-plus是Mybatis的升级,非常非常好用.
   - JPA是Spring自己实现的JDBC升级接口,国外用的多,国内用Mybatis比较多

   

## SpringBoot Web场景开发

具体代码到JavaProject_end\tests\springboot-web-admin查看,当初看的雷神网课学的,可以结合雷神网课的配套文档复习

配置说白了就是dependency+yml

到网上查 eg mybatis整合springboot site:cnblogs.com 即可



1. dependency

   1. 开发工具:SpringBoot DevTools+ Lombok+ SpringBoot Configuration Processor
   2. SpringWeb+mysql+mybatis

2. 静态资源访问

   1. 什么是静态资源?  就是静态的资源,比如图片,html页面

   2. 约定:静态资源放在`/static` (or `/public` or `/resources` or `/META-INF/resources`) in the classpath  即可

      一般都是放在/static下面

   3. 可以进行如下配置

      配置过后访问静态资源时候前面加上/resources/,但是静态资源存放的路径没有改变.    比如这时候主页面的index.html就需要键入/resources 才能显示了,不能直接进入页面就显示了.不仅欢迎页没了,网页图标也没了.

       我们还可以通过spring.web.resources.static-locations改变存放的路径,不过这个没必要.  这些都是reference的web里面可以找到的.

      ```yml
      spring:
        mvc:
          static-path-pattern: /resources/**
      ```
   
3. 拦截器Interceptor

   以写LoginInterceptor为例

   1. LoginInterceptor

      很简单写一个LoginInterceptor然后继承HandlerInterceptor. 然后使用override

      ```java
      @Slf4j
      public class LoginInterceptor implements HandlerInterceptor {
          @Override
          public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
              String requestURI = request.getRequestURI();
              log.info("The url being interceptor is{}",requestURI);
              HttpSession httpSession=request.getSession();
              if(httpSession.getAttribute("user")!=null){
                  return true;
              }
              else {
                  httpSession.setAttribute("msg","please login in first");
                  response.sendRedirect("/");
                  return false;
              }
          }
      
          @Override
          public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
      
          }
      
          @Override
          public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      
          }
      }
      ```

   2. implement WebMvcConfigurer,然后override   addInterceptors

      ```java
      public class WebAdminConfig implements WebMvcConfigurer {
          /**
           *添加拦截器
           * @param registry
           */
          @Override
          public void addInterceptors(InterceptorRegistry registry) {
      //        addPathPatterns是pass的
      //        excludePathPatterns是不被pass的
              registry.addInterceptor(new LoginInterceptor())
                      .addPathPatterns("/**")
                      .excludePathPatterns("/","/login","/css/**","/js/**","/images/**");
          }
      }
      ```

4. 原生组件原生注入

   在主程序这里@ServletComponentScan

   ```
@ServletComponentScan("com.example.springbootwebadmin")
   public class SpringbootWebAdminApplication{}
   ```

![image-20221022151337039](D:\Users\Pictures\Saved Pictures\image-20221022151337039.png)

   @WebFilter(urlPatterns = {"/css/*","/images/*"})

   @WebListener

   @WebServlet(urlPatterns = {"/test1"})

   基本上都是这个套路,先是继承或者实现,然后override,如果要new的话给一个constructor

5. 错误处理

   ***使用默认异常处理***

   <!--对于默认异常处理,不需要定义异常类,也不需要自己抛出异常-->

   放在template下面的error下面的4xx,5xx会被自动解析,不需要写对应的Controller.  这些其实都可以在SpringBoot的Reference里面查到

   在4xx,5xx中自定义错误处理内容:
   <h3 th:text="${status}"></h3>
   <h3 th:text="${message}"></h3>
   <p　th:text="${trace}"></p>
   
      ***自定义异常处理***

   自行抛出异常

   ```
   if(!(users.size()>0)){
       throw new NoUserException();
   }
   ```

   自定义异常类

   ```java
   @ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "user too much!")
   public class UserTooManyException extends RuntimeException {}
   里面的内容generate constructor即可
   主要写的就是注解里面的内容
   ```

   

6. 定制化组件

   原理:SpringBoot的xxxAutoConfiguration中,会有@ConditionalOnClass  也就是说你自己不配时他才会自动配置. 一般都是不是整个接管,而是接管一些小功能.

   下面这两种方法都要会,刚好我都会哈哈哈哈

   - 编写自定义的配置类xxxConfiguration@Configuration然后  
     对对应的定制化属性@Bean  即可定制化
   
     @Configuration
      public class WebAdminConfig implements WebMvcConfigurer 
    然后override
   
   - 修改配置文件
   
     下面例子就是用配置类定制化组件的,其实很简单. implement后Override要定制化的东西即可. 
   
   ```java
   //@EnableWebMvc
   //上面这个注解代表全面接管,就是你不写的就没有了,别人不会给你默认配置,全部要自己配,所以我肯定用部分接管
   @Configuration
   public class WebAdminConfig implements WebMvcConfigurer {
    /**
        *添加拦截器
     * @param registry
        */
       @Override
       public void addInterceptors(InterceptorRegistry registry) {
   //        addPathPatterns是pass的
   //        excludePathPatterns是不被pass的
           registry.addInterceptor(new LoginInterceptor())
                   .addPathPatterns("/**")
                   .excludePathPatterns("/","/login","/css/**","/js/**","/images/**","/fonts/**");
       }
   }
   ```
   
   
   
   
   
7. 跨域

   有时候某种方法无效,换其他方法就行了,原因我现在还不知道

   1. 注解实现跨域(首选)

      ```java
      @CrossOrigin(origins = "*") //注解实现跨域,很方便
      ```

      

   2. CrosConfig类实现跨域

      1. 情况一

         ```java
         @Configuration
         public class CrosConfiguration implements WebMvcConfigurer {
         
             @Override
             public void addCorsMappings(CorsRegistry registry) {
                 registry.addMapping("/**")
                         .allowedOriginPatterns("*")
                         .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                         .allowCredentials(true)
                         .maxAge(3600)
                         .allowedHeaders("*");
             }
         
         }
         ```

         

      2. 情况二

   ```java
   @Configuration
   public class CrosConfiguration implements WebMvcConfigurer {
   
       @Override
       public void addCorsMappings(CorsRegistry registry) {
           registry.addMapping("/**")
                   .allowedOrigins("*")
                   .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                   .allowCredentials(true)
                   .maxAge(3600)
                   .allowedHeaders("*");
       }
   
   }
   ```

   

8. 表单提交&文件上传

   ```html
   <form role="form" th:action="@{/upload}" method="post" enctype="multipart/form-data">
   ```

   通过form里面的action使得提交表单时采取action,然后我们在controller里面定义对应的方法. 然后记得提交的东西要有name属性egname="headerImg" ,这样后端才能获取

   ```html
   <div class="form-group">
           <label for="exampleInputFile">头像</label>
           <input type="file" name="headerImg" id="exampleInputFile">
       </div>
   ```



       <div class="form-group">
           <label for="exampleInputFile">生活照</label>
           <input type="file" name="photos" multiple>
       </div>



## springboot整合mybatis

### 用法

1.需要在yml里面说明mybatis的配置(或者写mybatis-config,然后在yml说明mybatis-config的位置)
例如实现mapper的xml位置比如告诉.
2.需要一个mapper package放对应的mapper例如StudentMapper,然后在Service里面
才能写对应的service	

然后实际上,resources下面不需要放任何mybatis的文件.  
也就是mybatis-config和mapper.xml都可以不需要写,用别的代替
mybatis-config在yml里面写,然后mapper.xml直接在mapper接口里面@用注解写啊  

```
@Autowired
StudentMapper studentMapper;
```

而Mybatis-plus,只需要不导入mybatis导入mybatis-plus的dependency,然后@mapper即可,mybatis-plus是mybatis的进化

1. dependency

   ```md
   <dependency>
   <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>2.2.2</version>
   </dependency>
   ```

   If you want to change config,just use yml

2. yml

   重点是最下面5行,说明了mybatis-config的位置,还有实现mapper的xml

   ```yml
   server:
     port: 8080   #端口号
   spring:
     datasource:
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
       username: root
       password: 524118
       type: com.alibaba.druid.pool.DruidDataSource
       druid:
   #    配置监控页
         stat-view-servlet:
           enabled: true
           login-username: root
           login-password: 524118
           reset-enable: true
   #        Web应用和URL监控
         web-stat-filter:
           enabled: true
           url-pattern: /*
           exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
         filters: stat,wall,slf4j
   #      stat是sql监控,wall是防火墙
         aop-patterns: com.example.springbootwebadmin.*
         # aop监控切入点 监控spring的bean的
   #      对使用的filter进行配置
         filter:
           stat:
             enabled: true
             slow-sql-millis: 1000
             log-slow-sql: true
           wall:
             enabled: true
     servlet:
       multipart:
         max-file-size: 10MB
         max-request-size: 100MB
   mybatis:
     config-location: classpath:mybatis/mybatis-config.xml
     mapper-locations: classpath:mybatis/mapper/*.xml
   ```


### 最佳实战

1. 引入mybatis-starter
2. tell mapper.xml location in yml
3. Write mapper.interface and @Mapper or use @MapperScan
   1. easy sql use @Select,@Insert and so on
   2. complex sql use mapper.xml



## springboot整合mp(必会)

数据库对应表+Java对应实体类+mapper package+SpringBoot配置(即ORM object relationship mapping 配置)

注意OR的名字要一样,就是说数据库表名叫Dog,那么就有一个Dog类,一个DogMapper,DogService. 这样不仅自己看得懂,而且SpringBoot也是这么自动对应的.
要是表dog1,Dog类就需要@TableName("dog1")

### ***标准格式!!!***

这个东西必会

ORMCSC

Object Relationship mapper controller service config

![image-20221029191407170](D:\Users\Pictures\Saved Pictures\image-20221029191407170.png)

- controller

  @Controller

  用Service写Controller,记得Service@Autowired

  - UserController
  - DogController

- service

  @Service

  直接extends IService<User>即可

  有额外的需要,那就用Mapper写Service在imp里面实现,记得给mapper @Autowired

  - UserService.interface
  - UserServiceImplent
  - DogService.interface
  - DogServiceImplent

  ```java
  public interface UserService extends IService<User> {}
  ```

  ```md
  public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {}
  ```

  在下面可以通过override定制化功能,即通过override的方式改写方法

  也可以扩充功能:在UserService接口里面写新功能,用UserServiceImp Override实现

  不过一般也不需要写,不写的话User Service与 UserServiceImp就是一样的,用法也一样,一般用UserServiceImp更好

  

- mapper 

  @mapper

  - UserMapper.interface
  - DogMapper.interface

  直接extends BaseMapper即可,mp的基本够用了

  有额外的要求. 那么就在接口上面写个@Select,@Insert之类的进行实现,
  复杂的sql也可以写在资源文件resources下面的mapper的xml里面

- unity

  类名,属性名要与数据库的名称一模一样

  ```
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  ```

  - User
  - Dog
  
  mapper用来写service,service用来写controller

### 相关配置



1. dependency

   ```java
       <dependency> <groupId>com.baomidou</groupId>
           <artifactId>mybatis-plus-boot-starter</artifactId>
           <version>3.5.1</version>
       </dependency>
   ```
   

   
2. 配置

   SpringBoot通过yml整合
   端口配置+数据库配置+druid+mybatis-plus

```yml
server:
  port: 8080   #端口号
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    username: root
    password: 524118
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
#    配置监控页
      stat-view-servlet:
        enabled: true
        login-username: root
        login-password: 524118
        reset-enable: true
#        Web应用和URL监控
      web-stat-filter:
        enabled: true
        url-pattern: /*
        exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
      filters: stat,wall,slf4j
#      stat是sql监控,wall是防火墙
      aop-patterns: com.example.springbootwebadmin.*
      # aop监控切入点 监控spring的bean的
#      对使用的filter进行配置
      filter:
        stat:
          enabled: true
          slow-sql-millis: 1000
          log-slow-sql: true
        wall:
          enabled: true
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 100MB
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
#  现在不用写了,别人自动配置了,你写一个mapper,mapper包下的mapper会被自动扫描
#  mapper-locations: classpath:test/mapper/*.xml
#debug: true
```





## Jwt实现登录验证

1. 配置Maven

   ```java
   <dependency>
       <groupId>io.jsonwebtoken</groupId>
       <artifactId>jjwt</artifactId>
       <version>0.9.1</version>
   </dependency>
   ```

   

2. 做成JwtUtil工具类方便使用

   ```java
   public class JwtUtil {
   
       private static long time = 1000*5;
       private static String signature = "admin";
   
       public static String createToken(){
           JwtBuilder jwtBuilder = Jwts.builder();
           String jwtToken = jwtBuilder
                   //header
                   .setHeaderParam("typ", "JWT")
                   .setHeaderParam("alg", "HS256")
                   //payload
                   .claim("username", "admin")
                   .claim("role", "admin")
                   .setSubject("admin-test")
                   .setExpiration(new Date(System.currentTimeMillis()+time))
                   .setId(UUID.randomUUID().toString())
                   //signature
                   .signWith(SignatureAlgorithm.HS256, signature)
                   .compact();
           return jwtToken;
       }
   
       public static boolean checkToken(String token){
           if(token == null){
               return false;
           }
           try {
               Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
           } catch (Exception e) {
               return false;
           }
           return true;
       }
   }
   ```

   

3. Controller中login mapping的写法

   ```java
       private final String USERNAME="admin";
       private final String PASSWORD="524118";
       @GetMapping("/login")
       public User login(User user){
           if(USERNAME.equals(user.getUsername())&&PASSWORD.equals(user.getPassword())){
               user.setToken(JwtUtil.createToken());
               return user;
           }
           return null;
       }
   ```

   







## SpringBoot整合Redis

1. 导入Maven依赖

   ```xml
   <!--redis依赖-->
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
       <version>2.6.4</version>
   </dependency>
   
   <!-- pool2连接池依赖 -->
   <dependency>
       <groupId>org.apache.commons</groupId>
       <artifactId>commons-pool2</artifactId>
       <version>2.11.1</version>
   </dependency>
   ```

   

2. 让实体类implements Serializable

   ```java
   //因为内存里的对象必须要序列化才能存储
   //redis的值存的时候会被序列化,取的时候会被反序列化
   @Data
   public class Student implements Serializable {
       private Integer id;
       private String name;
       private Double score;
       private Date birthday;
   }
   ```

   

3. yml配置

   ```yml
   spring:
     redis:
       database: 0
       host: localhost
       port: 6379
   ```

   

4. 编写对应的Controller(整合Mp时不是这么写的,因为这样根本没用到egUserMapper)

   ```java
   package com.southwind.controller;
   
   import com.southwind.entity.Student;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.data.redis.core.RedisTemplate;
   import org.springframework.web.bind.annotation.*;
   
   @RestController
   public class StudentHandler {
   
       @Autowired
       private RedisTemplate redisTemplate;
   
       @PostMapping("/set")
       public void set(@RequestBody Student student){
           redisTemplate.opsForValue().set("student",student);
       }
   
       @GetMapping("/get/{key}")
       public Student get(@PathVariable("key") String key){
           return (Student) redisTemplate.opsForValue().get(key);
       }
   
       @DeleteMapping("/delete/{key}")
       public boolean delete(@PathVariable("key") String key){
           redisTemplate.delete(key);
           return redisTemplate.hasKey(key);
       }
   }
   ```



## 如何创建Vue工程并且后续不出Bug(举例说明)

```md
1.创建一个BookProject
2.在BookProject里面cmd,vue ui. 然后进入交互创建页面
3.选择工程名为vuebook,根据第二套配置(vue2+elementui),因为vue3 elementplus有时候有问题
4.点击Plugs,搜索添加axios插件和elementui插件
5.把vuebook拖到idea运行即可
6.如果需要添加新的插件,请直接在选择工程名为vuebook中cmd然后vue ui在图像界面添加插件
7.将app.vue中<template></template>的内容 变为<router-view></router-view>  vue是单页面应用,我们看到其他人写的所有页面其实都是在app.vue页面里面的.   我们<router-view></router-view> 页面才能在app.vue这个父页面中显示  也就是说,router-view其实就是返回与路由对应的页面加载在app.vue里面

```



## Echart的使用

tips:前端框架都是这么用的.  下插件,找效果对应的代码,复制粘贴即可

1. 下载Echart的插件(vue ui中)

2. 官网取option代替示例的option

   

   1. 官网取想要的类型 https://echarts.apache.org/examples/zh/index.html
   2. 将示例中的myChart.setOption里面的内容用复制的option代替

   ```vue
   <template>
       <div id="myChart" :style="{width: '800px', height: '600px'}"></div>
   </template>
   <script>
       export default {
           name: 'Echarts',
           mounted(){
               // 基于准备好的dom，初始化echarts实例
               let myChart = this.$echarts.init(document.getElementById('myChart'))
               // 绘制图表
               myChart.setOption({
                   title: {
                       text: '水果销量统计',
                       left: 'center',
                       top: 20,
                       textStyle: {
                           color: '#555555'
                       }
                   },
                   tooltip: {},
                   xAxis: {
                       data: [
                           "苹果",
                           "香蕉",
                           "橘子",
                           "火龙果",
                           "葡萄",
                           "西瓜"
                       ]
                   },
                   yAxis: {},
                   series: [{
                       name: '销量',
                       type: 'bar',
                       data: [
                           {
                               value: 333,
                               itemStyle: {
                                   color: "#3fb1e3"
                               }
                           },
                           {
                               value: 133,
                               itemStyle: {
                                   color: "#c4ebad"
                               }
                           },
                           {
                               value: 99,
                               itemStyle: {
                                   color: "#c4ebad"
                               }
                           },
                           {
                               value: 222,
                               itemStyle: {
                                   color: "#6be6c1"
                               }
                           },
                           {
                               value: 399,
                               itemStyle: {
                                   color: "#3fb1e3"
                               }
                           },
                           {
                               value: 123,
                               itemStyle: {
                                   color: "#c4ebad"
                               }
                           }
                       ]
                   }]
               });
           }
       }
   </script>
   ```

   