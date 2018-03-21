package com.taotao.portal.controller;/**
 * Created by lenovo on 2018/3/6.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页访问Controller
 * @author lenovo
 * @create 2018-03-06 9:02
 **/
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
}
