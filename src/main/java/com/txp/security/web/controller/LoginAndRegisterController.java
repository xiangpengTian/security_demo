package com.txp.security.web.controller;

import com.txp.security.model.entity.User;
import com.txp.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class LoginAndRegisterController {

    @Autowired
    private UserService userService;

    @GetMapping({"/index","/home"})
    public String index(){
        return "/index";
    }

    @GetMapping("/login")
    public String loginPage(){
        log.info("登陆方法被调用！");
        return "/login";
    }

    @GetMapping("/regpage")
    public String regPage(){
        return "/register";
    }


    @PostMapping("/register")
    public String register(User user){
       log.info("注册用户");
        Assert.notNull(user,"注册用户参数为null");
     int result = userService.insertUser(user);
     if(result<=0){
         log.error("注册失败！");
     }
        return "/index";
    }
}
