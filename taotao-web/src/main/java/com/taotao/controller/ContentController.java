package com.taotao.controller;/**
 * Created by lenovo on 2018/3/30.
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult selectCatgoryList(int page, int rows, Long categoryId){
        return contentService.selectCatgroryDet(page, rows, categoryId);
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult saveContent(TbContent content){
        TaotaoResult result = contentService.saveContent(content);
        HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
        return result;
    }

}
