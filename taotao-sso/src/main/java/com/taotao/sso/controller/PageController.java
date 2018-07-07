package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

/**
 * 展示注册和登录页面
 */
@Controller
public class PageController {

    /**
     * 展示登录页面
     */
    @RequestMapping("/page/login")
    public String showLogin(String redirectURL,Model model){
        model.addAttribute("redirect",redirectURL);
        // 将参数传递给jsp
        return "login";
    }

    /**
     * 展示注册页面
     */
    @RequestMapping("/page/register")
    public String showRegister(String redirectURL,Model model){
        model.addAttribute("redirect",redirectURL);
        return "register";
    }
}
