package com.taotao.search.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.ItemService;
//import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品导入Service
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpSolrClient solrServer;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public TaotaoResult importItems() throws Exception {
        // 查询数据库获得商品列表
        List<SearchItem> itemList = itemMapper.getItemList();
        for(SearchItem searchItem : itemList){
            // 创建文档对象
            SolrInputDocument document = new SolrInputDocument();
            // 添加域
            document.addField("id",searchItem.getId());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSellPoint());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",searchItem.getImage());
            document.addField("item_category_name",searchItem.getCategoryName());
            document.addField("item_desc",searchItem.getItemDesc());
            // 写入索引库
            solrServer.add(document);
        }
        // 提交
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult addOrUpdateItem(Long id) throws Exception {
        // 根据Id查询对应信息
        SearchItem item = itemMapper.getItem(id);
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",item.getId());
        document.addField("item_title",item.getTitle());
        document.addField("item_sell_point",item.getSellPoint());
        document.addField("item_price",item.getPrice());
        document.addField("item_image",item.getImage());
        document.addField("item_category_name",item.getCategoryName());
        document.addField("item_desc",item.getItemDesc());
        // 写入索引库
        solrServer.add(document);
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult delete(Long id) throws Exception {
        solrServer.deleteById(id+"");
        solrServer.commit();
        return TaotaoResult.ok();
    }
}
