package com.taotao.portal.service;

import com.taotao.portal.pojo.PortalItem;

public interface ItemService {

    PortalItem getItemById(Long itemId);

    String getItemDescById(Long itemId);

    String getItemParamById(Long itemId);
}
