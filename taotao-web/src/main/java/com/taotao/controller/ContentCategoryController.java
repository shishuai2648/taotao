package com.taotao.controller;/**
 * Created by lenovo on 2018/3/29.
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCatgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类管理Controller
 * @author lenovo
 * @create 2018-03-29 10:40
 **/
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCatgoryService contentCatgoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id",defaultValue = "0") Long parentId){
        return contentCatgoryService.getContentCatList(parentId);
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult createNode(Long parentId,String name,Long nullId){
        if(parentId == null){
            parentId = nullId;
        }
        return contentCatgoryService.insertCatgory(parentId, name);
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateNode(Long id,String name){
        return contentCatgoryService.updateCatgory(id,name);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteNode(Long id,Long nullId){
        if(id == null){
            id = nullId;
        }
        return contentCatgoryService.deleteCatgory(id);
    }
}
