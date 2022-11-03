Tips:在通过教学视频入门SpringBoot后,比如能够完成基本的图书管理系统后,我们可以自己去SpringBoot官网学习,看reference和guide,更加熟练使用SpringBoot

1. 思考需要哪些依赖,然后创建SpringBoot工程并勾选相应依赖(pom文件),设置相关配置(yml文件)

   ![image-20221006141038920](D:\Users\Pictures\Saved Pictures\image-20221006141038920.png)

   1. DemoApplication是用于启动SpringBoot的,启动会进入一个页面

   2. resources下面的application.properties是配置文件,所有配置都写在这里.  可以把后缀名改为**application.yml**方便配置.  最开始的是空白的,会使用约定配置,SpringBoot的特点是约定大于配置,有些配置是规定好的不需要配.  这个就是SpringBoot的**自动配置**,它会根据约定给你进行配置.  再比如约定就是按照那个项目文件格式写,也就是controller包,service包这种包都要跟SpringBoot主启动程序同级.   然后那些Controller或者Service就都能被扫描,不需要自己配置路径.然后具体的东西用@注解一下就OK了,十分方便.我们只要遵守这一套约定写就OK了

      [可以在官网这里查看相关配置,对应的配置ctrl+F查询即可. 比如server.port](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties)

   3. 一般web项目是勾选mysql,mybatis,springweb,lombok. 引入的依赖会放在,**pom**文件,是存放**dependencies**的. 最开始勾选的依赖会显示在这里,那些有starter的就是场景启动器,非常方便,引入一个场景**starter**依赖就等价于引入很多该场景需要的依赖了,比如引入spring-boot-starter-web就会给你引入tomcat等各种web需要的依赖.   如果要额外引入依赖,直接到https://mvnrepository.com/ maven仓库找引入就行. 　　这就是SpringBoot的**依赖管理**

      记住依赖一定是starter才行,他才会帮我们导入对应的xxxAutoConfiguration.java

   

2. 编写业务

   真正写业务其实就是写一些增删改查.

   非常方便,比如你有一个User类,你用Spring的时候可以@Configuration出一个配置类,然后到里面@Bean进行依赖注入到IOC.   或者直接在xml里面写然后@ImportResource("beans.xml").  但是SpringBoot呢? 没这么麻烦了,我们采用单例模式,直接给一个类@Component就可以了,然后属性的值@Value就行了

   ![image-20221006144106510](D:\Users\Pictures\Saved Pictures\image-20221006144106510.png)

3. 测试

   1. 如何查看有哪些组件在IOC容器里?

      只需把主程序里面的psvm改为如下即可,直接在打印的结果中搜索即可看看是否在容器中了,并且可以查看对应bean的状况

      ```java
      	public static void main(String[] args) {
      
      		ConfigurableApplicationContext run = SpringApplication.run(DemoApplication.class, args);
      		String[] beanDefinitionNames = run.getBeanDefinitionNames();
      		for(String name:beanDefinitionNames){
      			System.out.println(name);
      		}
      	}
      
      		User user=  run.getBean("user",User.class);
      		System.out.println(user.toString()+"`````````");
      ```

      

   2. 

4. 打成jar包

   使用最右侧的maven工具

   ![image-20221006142937615](D:\Users\Pictures\Saved Pictures\image-20221006142937615.png)

   打成的jar包就可以直接运行了

   ![image-20221006142801513](D:\Users\Pictures\Saved Pictures\image-20221006142801513.png)

   