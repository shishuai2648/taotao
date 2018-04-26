/**
 * Created by lenovo on 2018/4/4.
 */

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.http.entity.mime.FormBodyPart;
import org.junit.Test;

/**
 * @author lenovo
 * @create 2018-04-04 16:51
 **/
public class SolrTest {

    @Test
    public void testSolrJ() throws Exception {
        // 创建连接
        SolrServer solrServer = new HttpSolrServer("http://39.107.113.43:8080/solr");
        // 创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 添加域
        document.addField("id", "solrtest01");
        document.addField("item_title", "测试商品");
        document.addField("item_sell_point", "卖点");
        // 添加到索引库
        solrServer.add(document);
        // 提交
        solrServer.commit();
    }

    @Test
    public void testQuery() throws  Exception{
        // 创建连接
        SolrServer solrServer = new HttpSolrServer("http://39.107.113.43:8080/solr");
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        for(SolrDocument solrDocument : results){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
        }
    }

}
