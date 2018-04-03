package com.taotao.rest.controller;/**
 * Created by lenovo on 2018/4/2.
 */

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容管理controller
 * @author lenovo
 * @create 2018-04-02 16:12
 **/
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/{cid}")
    @ResponseBody
    public TaotaoResult getContentList(@PathVariable Long cid){
        List<TbContent> list = contentService.getContentList(cid);
        TaotaoResult result = TaotaoResult.ok(list);
        try{
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/sync/content/{cid}")
    @ResponseBody
    public TaotaoResult syncContent(@PathVariable Long cid){
        try {
            return contentService.syncContent(cid);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }
}
