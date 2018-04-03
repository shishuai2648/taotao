package com.taotao.rest.service;/**
 * Created by lenovo on 2018/4/2.
 */

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * @author lenovo
 * @create 2018-04-02 16:07
 **/
public interface ContentService {

    List<TbContent> getContentList(Long cid);

    TaotaoResult syncContent(Long cid);
}
