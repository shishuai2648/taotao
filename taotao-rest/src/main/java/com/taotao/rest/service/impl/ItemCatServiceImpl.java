package com.taotao.rest.service.impl;/**
 * Created by lenovo on 2018/3/22.
 */

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类列表查询
 * @author lenovo
 * @create 2018-03-22 14:30
 **/
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;


    @Override
    public ItemCatResult getItemCatList() {
        // 嗲用递归方法查询商品分类列表
        List itemCatList = getItemCatList(0L);
        // 返回ItemCatResult
        ItemCatResult result = new ItemCatResult();
        result.setData(itemCatList);
        return result;
    }

    /**
     * 递归方法
     * @param parentId
     * @return
     */
    private List getItemCatList(Long parentId){
        // 根据parentId查询列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        List resultList = new ArrayList();
        int i = 0;
        for(TbItemCat tbItemCat : tbItemCats){
            if(i >= 14){
                break;
            }
            // 如果是父节点
            if(tbItemCat.getIsParent()) {
                CatNode node = new CatNode();
                node.setUrl("/products/" + tbItemCat.getId() + ".html");
                // 如果当前节点为第一级节点
                if (tbItemCat.getParentId() == 0) {
                    node.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                } else {
                    node.setName(tbItemCat.getName());
                }
                node.setItems(getItemCatList(tbItemCat.getId()));
                // 将node添加进入列表
                resultList.add(node);
            }else{
                // 如果是叶子节点
                String item = "/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName();
                resultList.add(item);
            }
            i++;
        }

        return resultList;
    }
}
