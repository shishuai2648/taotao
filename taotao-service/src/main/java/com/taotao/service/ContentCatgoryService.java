package com.taotao.service;/**
 * Created by lenovo on 2018/3/29.
 */

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

/**
 * @author lenovo
 * @create 2018-03-29 10:31
 **/
public interface ContentCatgoryService {

    List<EasyUITreeNode> getContentCatList(Long parentgId);

    TaotaoResult insertCatgory(Long parentId,String name);

    TaotaoResult updateCatgory(Long id,String name);

    TaotaoResult deleteCatgory(Long id);
}
