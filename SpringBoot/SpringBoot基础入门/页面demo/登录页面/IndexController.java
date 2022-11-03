package com.example.springbootwebadmin.controller;


import com.example.springbootwebadmin.Unity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
//We use thymeleaf,so we need view instead of json
public class IndexController {

    @GetMapping(value = {"/","/login"})
    public String login(){
        return "login";
    }
//  虽然都是/login,但是只有以Post方式发送请求时才会映射到它
    //显然这个对应提交账号密码时跳转的页面
    @PostMapping(value = {"/","/login"})
    public String main(User user,HttpSession httpSession,Model model){
//      要用.equals而不是==,因为==比较的是地址. 我们sout时他会自动.tostring所以我们发现不了
        if ((user.getUserName()).equals("root")&&(user.getPassword().equals("123456"))){
            httpSession.setAttribute("user",user);
            return "redirect:/pretendmain.html";
        }

        else {
            model.addAttribute("msg","login failed!");
            return "login";
        }

        //注意,这个只是到对应的映射,而不是直接到我们资源文件夹下面的文件
        //只有直接return page 才可以到对应的资源文件夹下面的页面
        //redirect的只能是映射不会是资源文件的网页
        //而且,就算我们不加html也是一样.
        // 就跟@Controller可以用@Component差不多,为了可读性improve
    }
//redirect can avoid form repeated submission because you will in a new session
//    这个结合重定向可以避免表单重复提交的问题
    //以后看到xxx.html就知道这是为了跳转页面用的
    //如果不这样那么return test就会直接return模板引擎里面的数据,
// 还是同一个页面只不过页面内容了
    // 有了映射的才能被称为页面
    //即,直接访问只能访问静态的页面.  而动态页面即thymeleaf文件需要映射
// eg@GetMapping("/test.html")才能作为一个新页面访问.
    @GetMapping("/pretendmain.html")
    public String mainPage(HttpSession httpSession,Model model){
        if(httpSession.getAttribute("user")!=null){
            return "pretendmain";//这个方法就是用来避免表单重复提交的
        }
        else {
            model.addAttribute("msg","please login!");
            return "login";
        }



    }
}
