package com.taotao.controller;/**
 * Created by lenovo on 2018/3/30.
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lenovo
 * @create 2018-03-30 10:00
 **/
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult selectCatgoryList(int page, int rows, Long categoryId){
        return contentService.selectCatgroryDet(page, rows, categoryId);
    }

//    @RequestMapping("/save")
//    @ResponseBody
//

}
