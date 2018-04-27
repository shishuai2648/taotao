package com.taotao.search.controller;/**
 * Created by lenovo on 2018/4/27.
 */

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * 发布服务
 * @author lenovo
 * @create 2018-04-27 11:16
 **/
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/q")
    @ResponseBody
    public TaotaoResult search(@RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "30") Integer rows){


        try {
            // 转换字符集
            keyword = new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
            SearchResult result = searchService.search(keyword, page, rows);
            return TaotaoResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
