package com.taotao.service.impl;/**
 * Created by lenovo on 2018/3/30.
 */

import com.github.pagehelper.PageHelper;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lenovo
 * @create 2018-03-30 9:56
 **/
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    /**
     * 根据内容目录ID查询对应列表信息
     * @param page          当前页
     * @param rows          每页行数
     * @param catgoryId     内容类目ID
     * @return
     */
    @Override
    public EasyUIDataGridResult selectCatgroryDet(int page, int rows, Long catgoryId) {
        EasyUIDataGridResult result = new EasyUIDataGridResult();

        PageHelper.startPage(page,rows);
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(catgoryId);
        List<TbContent> tbContents = contentMapper.selectByExample(example);
        result.setRows(tbContents);

        return result;
    }
}
