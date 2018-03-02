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
     * @param catId             商品对应的商品类目ID
     * @return
     */
    TaotaoResult getItemParamById(Long catId);

    /**
     * 保存ItemParam数据信息
     * @param catId             商品对应的商品类目ID
     * @param paramData         商品的规格参数模板
     * @return
     */
    TaotaoResult saveItemParam(Long catId, String paramData);

    /**
     * 删除ItemParam
     * @param ids               商品类目ID集合
     * @return
     */
    TaotaoResult deleteItemParam(Long[] ids);
}
