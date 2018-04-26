package com.taotao.search.dao.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Solr查询dao
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery solrQuery) throws SolrServerException {
        // 执行查询
        QueryResponse response = solrServer.query(solrQuery);
        // 获取查询结果列表
        SolrDocumentList solrDocuments = response.getResults();
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocuments){
            SearchItem item = new SearchItem();
            item.setCategoryName(solrDocument.get("item_category_name").toString());
            item.setId(solrDocument.get("id").toString());
            item.setImage(solrDocument.get("item_image").toString());
            item.setPrice(Long.parseLong(solrDocument.get("item_price").toString()));
            item.setSellPoint(solrDocument.get("item_sell_point").toString());
            // 获取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");

            String itemTitle = "";
            if(list != null && list.size() > 0){
                // 获取高亮后的结果
                itemTitle = list.get(0);
            }else{
                itemTitle = solrDocument.get("item_title").toString();
            }
            item.setTitle(itemTitle);
            // 添加到列表
            itemList.add(item);
        }
        SearchResult result = new SearchResult();
        result.setItemList(itemList);
        // 查询结果总数数量
        result.setRecordCount(solrDocuments.getNumFound());
        return result;
    }
}
