package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;


public interface ItemService {

    TaotaoResult importItems() throws Exception;

    TaotaoResult addOrUpdateItem(Long id) throws Exception;

    TaotaoResult delete(Long id) throws Exception;
}
