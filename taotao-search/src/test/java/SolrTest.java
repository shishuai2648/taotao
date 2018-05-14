/**
 * Created by lenovo on 2018/4/4.
 */

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author lenovo
 * @create 2018-04-04 16:51
 **/
public class SolrTest {

    @Test
    public void testSolrJ() throws Exception {
//        // 创建连接
//        SolrServer solrServer = new HttpSolrServer("http://39.107.113.43:8080/solr");
//        // 创建一个文档对象
//        SolrInputDocument document = new SolrInputDocument();
//        // 添加域
//        document.addField("id", "solrtest01");
//        document.addField("item_title", "测试商品");
//        document.addField("item_sell_point", "卖点");
//        // 添加到索引库
//        solrServer.add(document);
//        // 提交
//        solrServer.commit();
    }

    @Test
    public void testQuery() throws  Exception{
//        // 创建连接
//        SolrServer solrServer = new HttpSolrServer("http://39.107.113.43:8080/solr");
//        SolrQuery query = new SolrQuery();
//        query.setQuery("*:*");
//        QueryResponse response = solrServer.query(query);
//        SolrDocumentList results = response.getResults();
//        for(SolrDocument solrDocument : results){
//            System.out.println(solrDocument.get("id"));
//            System.out.println(solrDocument.get("item_title"));
//            System.out.println(solrDocument.get("item_sell_point"));
//        }
    }

    @Test
    public void testSolrClout() throws Exception{
        HttpSolrClient solr = new HttpSolrClient.Builder("http://192.168.31.145:9001/solr/newCollection").withConnectionTimeout(10000).withSocketTimeout(60000).build();

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test01");
        document.addField("item_title","长安新生二二三三");
        solr.add(document);
        solr.commit();
        solr.close();
    }
}
