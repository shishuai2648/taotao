package com.taotao.service;/**
 * Created by lenovo on 2018/3/30.
 */

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * @author lenovo
 * @create 2018-03-30 9:53
 **/
public interface ContentService {

    EasyUIDataGridResult selectCatgroryDet(int page, int rows, Long catgoryId);

    TaotaoResult saveContent(TbContent content);
}
