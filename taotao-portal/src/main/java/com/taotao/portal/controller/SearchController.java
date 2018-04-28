package com.taotao.portal.controller;/**
 * Created by lenovo on 2018/4/28.
 */

import com.taotao.common.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * 实现商品查询
 * @author lenovo
 * @create 2018-04-28 9:42
 **/
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String keywords,
                         @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "60") int rows,
                         Model model){
        // get乱码处理
        try {
            keywords = new String(keywords.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            keywords = "";
            e.printStackTrace();
        }
        SearchResult searchResult = searchService.search(keywords, page, rows);
        // 参数传递给页面
        model.addAttribute("query",keywords);
        model.addAttribute("totaoPages",searchResult.getPageCount());
        model.addAttribute("itemList",searchResult.getItemList());
        model.addAttribute("page",page);

        // 返回逻辑视图
        return "search";
    }

}
