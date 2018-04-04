package com.taotao.portal.controller;/**
 * Created by lenovo on 2018/3/6.
 */

import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页访问Controller
 * @author lenovo
 * @create 2018-03-06 9:02
 **/
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;


    @RequestMapping("/index")
    public String showIndex(Model model){
        // 取大广告位内容
        String json = contentService.getContentList();
        // 传递给页面
        model.addAttribute("ad1",json);
        return "index";
    }

    @RequestMapping(value = "/posttest",method = RequestMethod.POST)
    @ResponseBody
    public String postTest(String name,String pass){
        System.out.println(name+"---"+pass);
        return "OK";
    }
}
