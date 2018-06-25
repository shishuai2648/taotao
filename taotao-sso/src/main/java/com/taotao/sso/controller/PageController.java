package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展示注册和登录页面
 */
@Controller
public class PageController {

    /**
     * 展示登录页面
     */
    @RequestMapping("/page/login")
    public String showLogin(){
        return "login";
    }

    /**
     * 展示注册页面
     */
    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }
}
