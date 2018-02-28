package com.taotao.service;/**
 * Created by lenovo on 2018/2/28.
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * @author lenovo
 * @create 2018-02-28 9:05
 **/
public interface ItemParamService {

    /**
     * 获取全部的ItemParam集合数据
     */
    EasyUIDataGridResult getItemParamList(int page,int row);

    /**
     * 根据商品类目id查询商品类目信息
     */
    TaotaoResult getItemParamById(Long id);

    TaotaoResult saveItemParam(Long id, String paramData);

}
