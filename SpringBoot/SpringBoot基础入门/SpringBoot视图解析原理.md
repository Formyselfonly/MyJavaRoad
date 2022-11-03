# Dispatcher

### 请求转发与重定向的区别

- ***请求转发只有一次请求和一次响应，重定向有两次请求和两次响应。***
- ***请求转发地址不发生改变，重定向会跳转到后一个请求的地址。***
- 请求转发只能转发到本项目的其它Servlet，重定向不只能重定向到本项目的其它Servlet，还能重定向到其它项目。
- 请求转发是服务端行为，只需给出转发的Servlet路径，重定向需要给出requestURI，既包含项目名。

### 请求值传递

同一个请求范围内可以使用request.setAttribute()和request.getAttribute()来相互传值。前一个Servlet调用setAttribute()保存值，后一个Servlet调用getAttribute()获取值。

### Dispatcher实例

DispatcherServlet主要用作职责调度工作，本身主要用于控制流程

Dispatcher的含义就是派遣器

![image](D:\Users\Pictures\Saved Pictures\1742867-20191130185354847-1211678731.jpg)

eg RequestDispatcher,**RequestDispatcher**是一个接口,称为请求派遣器.主要方法是forward.  如下,通过request取得/login的RequestDispatcher,然后通过forward也就是转发获得request和response

```
request.getRequestDispatcher("/login").forward(request,response);
```

RequestDispatcher rd = request.getRequestDispatcher("/MyServlet"); rd.forward(request, response);  // 请求转发
rd.include(request, response);  // 请求包含

无论是请求转发还是请求包含，都在一个请求范围内！使用同一个request和response。

- 请求转发：当前Servlet可以设置响应头，**由下一个Servlet完成响应体**，下一个Servlet输出响应体后，**当前Servlet不可以继续输出**！（留头不留体）
- 请求包含：当前Servlet可以设置响应头，**由两个Servlet共同完成响应体**，下一个Servlet输出响应体后，**当前Servlet可以继续输出**！（留头又留体)

# 视图解析原理

![](D:\Users\Pictures\Saved Pictures\2314637-20210823194302763-1784767691.png)

## 请求进入DispatcherServlet类，执行doService方法，doService方法中执行doDispatch方法：

1. 通过request对象获得对应的handler对象(网址和方法的映射存在在HandlerMapping中，通过request对象获得请求的网址，然后循环所有的handlerMapping对象(在一个List集合中)，找到可以处理当前request的handler。如果网址啥也没传，也就是"/"，循环结束后就会到下一个控制器：WelcomePageHandlerMapping，他会将请求转发到index.html中）

2. 通过handler找到适合当前handler的HandlerAdapter(handler适配器，也是通过循环查找一个List列表)，找到后执行适配器的handle方法(这个方法执行request对应的handler对象的方法)
3.  获取方法的参数列表(存储在Object[])，然后依次调用对应的参数解析器(通过循环参数解析器列表找到可以处理改参数解析器)为其赋值 (参数解析器大部分对应的是标注了何种注解的参数，小部分对应的是何种“类型”(servlet API和复杂类型等等))。

### 3.1 普通类型

### 3.2 复杂类型(例如Model、Map...)

Map和Model找到对应的参数解析器，返回 BindingAwareModelMap对象(是Model也是Map)，方法中操作的Map和Model都是在操作这个对象。

3.3 自定义类型

ServletModelAttributeMethodProcessor参数解析器可以处理，先创建一个空的自定义类型对象，创建WebDataBinder(web数据绑定器)对象，给自定义类型对象的每个属性赋值(从request中获取，获取的都是字符串，调用不同的类型转换器(循环查找)把字符串转变为适合的类型)

## 4. 方法结束后数据会被放在ModelAndView(包含了要去往的页面和数据等等)中，并把所有的数据放入一个Map，再从Map放入request请求域中(通过循环)，之后render方法渲染对应的页面。

# 拦截器

[非常完美的拦截器讲解文章](https://www.cnblogs.com/codetiger/p/15000154.html)

总的原理:后续我们用render的参数有mv,要获取mv我们才能执行,然而获取之前如果没有preHandle,那么就return了,无法继续执行了

```java
if (!mappedHandler.applyPreHandle(processedRequest, response)) {
   return;
}

// Actually invoke the handler.
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
```



拦截器执行顺序如图![image-20221019174711461](D:\Users\Pictures\Saved Pictures\image-20221019174711461.png)





```java
//拦截器的方法返回值都是true或者false
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if(){
            return true
        }
    	else{
            return false;
        }
        
    }
```

```java
//拦截时还是顺序执行拦截器的,也就是说一个拦截器返回true,则执行下一个拦截器,如果都是true,那么放行.
boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		for (int i = 0; i < this.interceptorList.size(); i++) {
			HandlerInterceptor interceptor = this.interceptorList.get(i);
			if (!interceptor.preHandle(request, response, this.handler)) {
				triggerAfterCompletion(request, response, null);
				return false;
			}
			this.interceptorIndex = i;
		}
		return true;
	}
```



