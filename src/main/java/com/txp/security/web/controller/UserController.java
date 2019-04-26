package com.txp.security.web.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author txp
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/index")
    public String index(){
        return "/user/user";
    }
}

