package com.taotao.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by lenovo on 2018/2/1.
 */
public class TestPageHelper {

    public void testPageHelper() throws Exception{
        // 1.获取mapper代理对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        // 2.设置分页
        PageHelper.startPage(1,30);
        // 3.执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> tbItems = itemMapper.selectByExample(example);

        // 4.取分页之后的结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);

        long total = pageInfo.getTotal();
        System.out.println();
        pageInfo.get

    }
}
