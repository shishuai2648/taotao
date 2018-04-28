package com.taotao.search.service;/**
 * Created by lenovo on 2018/4/27.
 */

import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * @author lenovo
 * @create 2018-04-27 10:57
 **/
public interface SearchService {

    SearchResult search(String queryString,int page,int rows) throws SolrServerException;
}
