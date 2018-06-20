package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.ItemService;
import com.taotao.portal.service.StaticPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.aspectj.weaver.ast.ITestVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成商品详情页面静态网页
 */
@Service
public class StaticPageServiceImpl implements StaticPageService {


    @Value("${STATIC_PAGE_PATH}")
    private String STATIC_PAGE_PATH;

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public TaotaoResult getItemHtml(Long itemId) throws IOException, TemplateException {
        // 根据商品id获得商品数据
        // 商品基本信息
        TbItem item = itemService.getItemById(itemId);
        // 商品详细信息
        String itemDesc = itemService.getItemDescById(itemId);
        // 商品规格参数
        String itemParam = itemService.getItemParamById(itemId);

        // 生成静态文件
        Configuration configuration = freeMarkerConfigurer.getConfiguration();

        Template template = configuration.getTemplate("item.ftl");
        // 创建一个数据集
        Map root = new HashMap();
        // 向数据集中添加数据
        root.put("item",item);
        root.put("itemDesc",itemDesc);
        root.put("itemParam",itemParam);

        // 创建Writer对象
        Writer out = new FileWriter(new File(STATIC_PAGE_PATH+ itemId + ".html"));
        // 生成静态文件
        template.process(root,out);
        out.flush();
        out.close();
        return TaotaoResult.ok();
    }
}
