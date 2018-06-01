package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.rest.compoent.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;

    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;

    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;

    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;

    @Override
    public TbItem getItemById(Long id) {
        // 查询缓存，如果有缓存，直接返回
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+id+":"+ITEM_BASE_INFO_KEY);
            // 判断数据是否存在
            if(StringUtils.isNotBlank(json)){
                // 把json数据转换成java对象
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        // 根据商品id查询商品基本信息
        TbItem item = itemMapper.selectByPrimaryKey(id);

        // 向redis中添加缓存。添加缓存的原则是不能影响正常的业务逻辑
        try {
            // 向Redis添加缓存
            jedisClient.set(REDIS_ITEM_KEY+":"+id+":"+ITEM_BASE_INFO_KEY,
                    JsonUtils.objectToJson(item));
            // 设置key的过期时间
            jedisClient.expire(REDIS_ITEM_KEY+":"+id+":"+ITEM_BASE_INFO_KEY,ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }


    @Override
    public TbItemDesc getItemDescById(Long id) {
        // 查询缓存
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+id+":"+ITEM_DESC_KEY);
            // 判断数据是否存在
            if(StringUtils.isNotBlank(json)){
                // 把json数据转换成java对象
                TbItemDesc tbItem = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemDescExample tbItemDescExample = new TbItemDescExample();
        TbItemDescExample.Criteria criteria = tbItemDescExample.createCriteria();
        criteria.andItemIdEqualTo(id);
        List<TbItemDesc> tbItemDescs = itemDescMapper.selectByExampleWithBLOBs(tbItemDescExample);
        if(tbItemDescs != null && tbItemDescs.size() > 0) {
            TbItemDesc tbItemDesc = tbItemDescs.get(0);
            try {
                // 向Redis添加缓存
                jedisClient.set(REDIS_ITEM_KEY+":"+id+":"+ITEM_DESC_KEY,
                        JsonUtils.objectToJson(tbItemDesc));
                // 设置key的过期时间
                jedisClient.expire(REDIS_ITEM_KEY+":"+id+":"+ITEM_DESC_KEY,ITEM_EXPIRE_SECOND);
            }catch (Exception e){
                e.printStackTrace();
            }
            return tbItemDesc;
        }
        return null;
    }

    @Override
    public TbItemParamItem getItemParamItemById(Long id) {
        // 查询缓存
        try{
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+id+":"+ITEM_PARAM_KEY);
            // 判断数据是否存在
            if(StringUtils.isNotBlank(json)){
                // 把json数据转换成java对象
                TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return itemParamItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItemParamItem> itemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (itemParamItems != null && itemParamItems.size() > 0){
            TbItemParamItem itemParamItem = itemParamItems.get(0);
            try {
                // 向Redis添加缓存
                jedisClient.set(REDIS_ITEM_KEY+":"+id+":"+ITEM_PARAM_KEY,
                        JsonUtils.objectToJson(itemParamItem));
                // 设置key的过期时间
                jedisClient.expire(REDIS_ITEM_KEY+":"+id+":"+ITEM_PARAM_KEY,ITEM_EXPIRE_SECOND);
            }catch (Exception e){
                e.printStackTrace();
            }
            return itemParamItem;
        }
        return null;
    }


}
