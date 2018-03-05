package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.math.IEEE754rUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

/**
 * 商品查询Controller
 *
 * @author lenovo
 * @date 2018/1/30
 */
@Controller
public class ItemController {
    @Autowired
    ItemService itemService;
    @RequestMapping("/selectOne/{itemId}")
    @ResponseBody
    public TbItem selectOne(@PathVariable Long itemId){
        return itemService.getItemById(itemId);
    }

    @RequestMapping("/rest/item")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        return itemService.getItemList(page,rows);
    }

    @RequestMapping(value="/rest/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem item,String desc,String itemParams){
        return itemService.createItem(item,desc,itemParams);
    }

    @RequestMapping("/item/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model){
        String html = itemService.getItemParamHtml(itemId);
        model.addAttribute("myhtml",html);
        return "itemparam";
    }

}
