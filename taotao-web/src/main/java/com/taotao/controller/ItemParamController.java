package com.taotao.controller;/**
 * Created by lenovo on 2018/2/28.
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.service.impl.ItemParamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lenovo
 * @create 2018-02-28 11:46
 **/
@Controller
public class ItemParamController {

    @Autowired
    ItemParamService itemParamService;

    @RequestMapping("/rest/item/param/list")
    @ResponseBody
    public EasyUIDataGridResult getItemParamList(int page, int rows){
        return itemParamService.getItemParamList(page,rows);
    }

    @RequestMapping("/rest/item/param/{id}")
    @ResponseBody
    public TaotaoResult addItemParamList(@PathVariable Long id, String paramData){
        if(paramData == null || paramData.isEmpty()) {
            return itemParamService.getItemParamById(id);
        }else{
            return itemParamService.saveItemParam(id,paramData);
        }
    }

    @RequestMapping("/rest/item/param/delete")
    @ResponseBody
    public TaotaoResult removeItemParam(Long[] ids){
        return itemParamService.deleteItemParam(ids);
    }
}
