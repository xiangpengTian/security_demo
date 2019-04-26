package com.txp.security.config.sercurity;

import com.txp.security.service.impl.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserService securityUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasAuthority("USER")
                .and()
                //登陆地址，当页面访问该地址，进入controller,寻找该值的mapping，返回登陆页面
                //登陆页面form提交至该地址，被security拦截器拦截验证用户是否存在，密码是否正确
                .formLogin().loginPage("/login").defaultSuccessUrl("/user/index")
                .and()
                //security拦截器拦截/logout，直接登出
                .logout().logoutUrl("/logout").logoutSuccessUrl("/index");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中添加用户
        //auth.inMemoryAuthentication().withUser("UserName").password("password").roles("USER");

        //数据库用户校验
        auth.userDetailsService(securityUserService)
        //添加密码加密
            .passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 密码加密
     * BCryptPasswordEncoder相关知识：
     *
     * 用户表的密码通常使用MD5等不可逆算法加密后存储，为防止彩虹表破解更会先使用一个特定的字符串（如域名）加密，
     * 然后再使用一个随机的salt（盐值）加密。
     *
     * 特定字符串是程序代码中固定的，salt是每个密码单独随机，一般给用户表加一个字段单独存储，比较麻烦。
     *
     * BCrypt算法将salt随机并混入最终加密后的密码，验证时也无需单独提供之前的salt，从而无需单独处理salt问题。
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
