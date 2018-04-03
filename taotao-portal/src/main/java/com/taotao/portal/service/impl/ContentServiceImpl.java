package com.taotao.portal.service.impl;/**
 * Created by lenovo on 2018/4/2.
 */

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容管理service
 * @author lenovo
 * @create 2018-04-02 16:47
 **/
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_BASE_URL}")
    private String REST_CONTENT_BASE_URL;
    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;



    /**
     * 获得大广告位内容
     * @return
     */
    @Override
    public String getContentList() {
        // 调用服务获得数据
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_BASE_URL + REST_CONTENT_AD1_CID);
        // 把json转换成java对象
        TaotaoResult result = TaotaoResult.formatToList(json, TbContent.class);
        // 取data属性
        List<TbContent> contentList = (List<TbContent>)result.getData();
        // 把内容列表转换成AdNode列表
        List<AdNode> resultList = new ArrayList<>();
        for(TbContent tbContent : contentList){
            AdNode node = new AdNode();
            node.setHeight(240);
            node.setWidth(670);
            node.setSrc(tbContent.getPic());

            node.setHeightB(240);
            node.setWidthB(550);
            node.setSrcB(tbContent.getPic2());

            node.setAlt(tbContent.getSubTitle());
            node.setHref(tbContent.getUrl());

            resultList.add(node);
        }
        // 将reslutList转换成json数据
        String resultJson = JsonUtils.objectToJson(resultList);
        return resultJson;
    }
}
