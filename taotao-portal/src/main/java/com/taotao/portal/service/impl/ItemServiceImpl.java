package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {


    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;


    @Override
    public PortalItem getItemById(Long itemId) {
        // 根据商品id查询商品基本信息
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
        TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, PortalItem.class);
        PortalItem item = (PortalItem)taotaoResult.getData();
        return item;
    }
}
