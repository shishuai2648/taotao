package com.taotao.rest.controller;/**
 * Created by lenovo on 2018/3/26.
 */

import com.alibaba.druid.support.json.JSONUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品分类查询服务
 * @author lenovo
 * @create 2018-03-26 11:45
 **/
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    public Object getItemCatList(String callback){
        ItemCatResult result = itemCatService.getItemCatList();
        if(StringUtils.isBlank(callback)){
            // 将result转换成字符串
            return result;
        }
        // 如果字符串不为空，需要直接jsonp调用
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    /*
    第一张方式，直接返回字符串
    @RequestMapping(value="/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        ItemCatResult result = itemCatService.getItemCatList();
        if(StringUtils.isBlank(callback)){
            // 将result转换成字符串
            String json = JsonUtils.objectToJson(result);
            return json;
        }
        // 如果字符串不为空，需要直接jsonp调用
        String json = JsonUtils.objectToJson(result);
        return "coallback"+"("+json+");";
    }*/

}
