package com.taotao.search.dao;

import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

public interface SearchDao {

    SearchResult search(SolrQuery solrQuery) throws Exception;


}
