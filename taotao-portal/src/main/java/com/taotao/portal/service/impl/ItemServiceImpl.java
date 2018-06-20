package com.taotao.portal.service.impl;

import com.sun.javafx.collections.MappingChange;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {


    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;

    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;

    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;

    @Override
    public PortalItem getItemById(Long itemId) {
        // 根据商品id查询商品基本信息
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, PortalItem.class);
        PortalItem item = (PortalItem)taotaoResult.getData();
        return item;
    }

    @Override
    public String getItemDescById(Long itemId) {
        // 根据商品id调用taotao-rest的服务获取到数据
        // http://localhost:8081/rest/item/desc/1022302785
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
        // 将json转换成java对象
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
        // 获取商品描述信息
        TbItemDesc itemDesc = (TbItemDesc)taotaoResult.getData();
        String desc = itemDesc.getItemDesc();
        return desc;
    }

    @Override
    public String getItemParamById(Long itemId) {
        // 根据商品id获得对应的规格参数
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
        // 取规格参数
        TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
        String paramJson = itemParamItem.getParamData();
        // 将规格参数的json数据转换成java对象
        // 转换成java对象
        List<Map> mapList = JsonUtils.jsonToList(paramJson, Map.class);
        // 遍历list,生成html
        StringBuffer html = new StringBuffer();
        html.append("<div class=\"Ptable-item\">");
        for (Map map : mapList) {
            html.append("  <h3>"+map.get("group").toString()+"</h3>");
            List<Map> mapList2 = (List<Map>) map.get("params");
            for (Map map2 : mapList2) {
                html.append("  <dl>");
                html.append("    <dt>" + map2.get("k")+"\t"+map2.get("v")+"</dt>");
                html.append("  </dl>");
            }
            html.append("</div>");

        }
        return html.toString();
    }
}
