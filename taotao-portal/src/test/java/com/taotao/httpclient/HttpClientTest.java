package com.taotao.httpclient;/**
 * Created by lenovo on 2018/3/30.
 */

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * @author lenovo
 * @create 2018-03-30 17:34
 **/
public class HttpClientTest {

    @Test
    public void testHttpGet() throws Exception {
        // 第一步：把HttpClient使用的jar包添加到工程中。
        // 第二步：创建一个HttpClient的测试类。
        // 第三步：创建测试方法。
        // 第四步：创建一个HttpClient对象。
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 第五步：创建一个HttpGet对象，需要指定一个请求的url。
        HttpGet get = new HttpGet("http://www.baidu.com");
        // 第六步：执行请求。
        CloseableHttpResponse response = httpClient.execute(get);
        // 第七步：接受返回结果。HttpEntity对象。
        HttpEntity entity = response.getEntity();
        // 第八步：获取的响应的内容。
        String html = EntityUtils.toString(entity);
        System.out.println(html);
        // 第九步：关闭HttpGet、HttpClient
        response.close();
        httpClient.close();
    }

    @Test
    public void testHttpPost() throws Exception{
        //第一步：创建一个httpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //第二步：创建一个HttpPost对象。需要指定一个URL
        HttpPost post = new HttpPost("http://localhost:8082/posttest.html");
        //第三步：创建一个list模拟表单，list中每个元素都是一个NameValuePair对象
        List<NameValuePair> formList = new ArrayList<>();
        formList.add(new BasicNameValuePair("name","张三"));
        formList.add(new BasicNameValuePair("pass","123"));
        //第四步：需要把表单包装到Entity对象中。StringEntity
        StringEntity entity = new UrlEncodedFormEntity(formList,"UTF-8");
        post.setEntity(entity);
        //第五步：执行请求。
        CloseableHttpResponse response = client.execute(post);
        //第六步：接受返回结果
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        System.out.println(result);
        //第七步：关闭流
        response.close();
        client.close();
    }
}
