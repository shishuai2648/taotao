package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lenovo on 2018/2/2.
 */
@Controller
@RequestMapping("/rest/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping("/list")
    @ResponseBody
    private List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        return itemCatService.getItemCatList(parentId);
    }

}
