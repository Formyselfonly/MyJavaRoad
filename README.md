# MyJavaRoad
 杰哥的Java学习之路,学会 就业没问题.

这个主要还是自用,多做笔记,多敲代码,以后东西忘记了自己也方便查阅.  通过这些文档构成自己的能力树,一天一颗树枝,一天一片树叶地长到这棵能力树上,通过时间和努力就能长成一棵大树.

作为一个研究生,如果只是看书,听老师讲课,虽然相关的理论知识不少,但是还是做不出比较好的项目,我觉得还是要从网络上体系化地学习更实用的技能,从研一开始就自学Java,学一门技术,方便以后找工作.  纸上得来终觉浅,绝知此事要躬行,代码敲出来才是自己的,加油杰哥!

目前先定下的要求就是能用SpringBoot和Mybatis进行增删改查,然后每天刷一题剑指Offer,到时候实习前再背背面经和八股文,基本应该没问题.

## JavaSE

## SpringBoot

### SpringBoot核心技术

#### 基础入门

1. [SpringBoot概述](SpringBoot\SpringBoot基础入门\SpringBoot概述.md)

2. [HelloWorld](SpringBoot\SpringBoot基础入门\HelloWorld.md)

3. [路由&映射](SpringBoot\SpringBoot基础入门\路由&映射.md)

4. [常用注解](SpringBoot\SpringBoot基础入门\常用注解.md)

5. [开发小技巧](SpringBoot\SpringBoot基础入门\开发小技巧.md)

6. [SpringBootWeb项目开发&配置指南](SpringBoot\SpringBoot基础入门\SpringBootWeb项目开发&配置指南.md)

7. [Restful](SpringBoot\SpringBoot基础入门\Restful.md)

8. [thymeleaf](SpringBoot\SpringBoot基础入门\thymeleaf.md)

9. [SpringBoot视图解析原理](SpringBoot\SpringBoot基础入门\SpringBoot视图解析原理.md)

10. [实战干货!!!.md](SpringBoot\SpringBoot基础入门\实战干货.md)

  

  ​    

#### 核心功能

#### 场景整合 

### SpringBoot响应式编程



## Mybatis&Mybatis-Plus

现在这些东西基本玩明白了,官网一查就会用了,现在是看了就会用了,感觉应该是内力提升的原因.  就算看不懂,基本看看cnblogs就没问题了

1. Mybatis-Plus插件

   需要的插件直接add就行

```
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
}
```

