package com.taotao.rest.service;/**
 * Created by lenovo on 2018/3/22.
 */

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.pojo.ItemCatResult;

/**
 * @author lenovo
 * @create 2018-03-22 14:29
 **/
public interface ItemCatService {

    ItemCatResult getItemCatList();

    TbItem getItemById(Long id);

    TbItemDesc getItemDescById(Long id);

    TbItemParamItem getItemParamItemById(Long id);
}
