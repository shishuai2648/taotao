package com.taotao.controller;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品查询Controller
 *
 * @author lenovo
 * @date 2018/1/30
 */
@RequestMapping("/item")
@Controller
public class ItemController {
    @Autowired
    ItemService itemService;
    @RequestMapping("/selectOne/{itemId}")
    @ResponseBody
    public TbItem selectOne(@PathVariable Long itemId){
        return itemService.getItemById(itemId);
    }
}
