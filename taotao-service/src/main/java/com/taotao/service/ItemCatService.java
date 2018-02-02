package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by lenovo on 2018/2/2.
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(long parentId);
}
