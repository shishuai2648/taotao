package com.taotao.service.impl;/**
 * Created by lenovo on 2018/3/29.
 */

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentCatgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类管理Service
 * @author lenovo
 * @create 2018-03-29 10:31
 **/
@Service
public class ContentCatgoryServiceImpl implements ContentCatgoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentgId) {
        // 根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentgId);
        // 执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed":"open");
            resultList.add(node);
        }

        return resultList;
    }

    @Override
    public TaotaoResult insertCatgory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        // 1(正常)2(删除)
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        // 排列序号，表示同级类目的展现次序，如果数值相等则按照名称次序排列。取值范围：大于0的整数
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());

        // 插入数据
        contentCategoryMapper.insert(contentCategory);
        // 取返回的主键
        Long id = contentCategory.getId();

        // 判断父节点的isParent属性
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(parentId);
        tbContentCategory.setIsParent(true);
        contentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);

        return TaotaoResult.ok(id);
    }

    @Override
    public TaotaoResult updateCatgory(Long id, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(id);
        tbContentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteCatgory(Long id) {
        Long parentId = deleteNode(id);
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        if(list == null || list.size() < 1){
            TbContentCategory contentCategory = new TbContentCategory();
            contentCategory.setId(parentId);
            contentCategory.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        }
        return TaotaoResult.ok();
    }

    public Long deleteNode(Long id){
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        Boolean isParent = contentCategory.getIsParent();

        if(isParent){
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
            for (TbContentCategory category : list){
                deleteCatgory(category.getId());
            }
            contentCategoryMapper.deleteByPrimaryKey(id);
        }else{
            contentCategoryMapper.deleteByPrimaryKey(id);
        }

        return contentCategory.getParentId();

    }


}
