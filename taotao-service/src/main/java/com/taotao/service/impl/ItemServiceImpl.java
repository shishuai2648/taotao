package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品查询Service
 * Created by lenovo on 2018/1/30.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getItemById(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        // 分页处理
        PageHelper.startPage(page,rows);
        // 执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> itemList = itemMapper.selectByExample(example);
        // 取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
        // 返回处理结果
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(itemList);
        return result;
    }

    @Override
    public TaotaoResult createItem(TbItem item, String desc,String itemParams) {
        // 生成商品ID
        long itemId = IDUtils.genItemId();
        // 不全TbItem属性
        item.setId(itemId);
        // 商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        // 创建时间和更新时间
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);

        // 插入商品表
        itemMapper.insert(item);
        // 商品描述
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(date);
        itemDesc.setCreated(date);
        // 插入商品描述信息
        itemDescMapper.insert(itemDesc);

        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);
        itemParamItem.setParamData(itemParams);
        itemParamItem.setItemId(itemId);
        itemParamItemMapper.insert(itemParamItem);

        return TaotaoResult.ok();
    }

    /**
     * 根据商品id查询规格参数
     * @param itemId        商品ID
     * @return
     */
    @Override
    public String getItemParamHtml(Long itemId) {
        TbItemParamItemExample itemParamExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = itemParamExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> itemParamItems = itemParamItemMapper.selectByExample(itemParamExample);

        if(itemParamItems == null || itemParamItems.isEmpty()){
            return "";
        }
        // 获取规格参数
        TbItemParamItem itemParamItem = itemParamItems.get(0);
        // 获取json数据
        String paramData = itemParamItem.getParamData();
        // 转换成java对象
        List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
        // 遍历list,生成html

        return null;
    }
}
