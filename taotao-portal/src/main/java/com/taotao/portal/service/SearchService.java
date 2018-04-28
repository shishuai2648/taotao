package com.taotao.portal.service;/**
 * Created by lenovo on 2018/4/28.
 */

import com.taotao.common.pojo.SearchResult;

/**
 * @author lenovo
 * @create 2018-04-28 9:30
 **/
public interface SearchService {

    SearchResult search(String keyword,int page,int rows);
}
