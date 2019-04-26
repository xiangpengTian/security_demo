package com.txp.security.web.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author txp
 * @since 2019-04-25
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/index")
    public String index(@AuthenticationPrincipal Principal principal, Model model){
        model.addAttribute("username", principal.getName());
        return "/user/user";
    }
}

