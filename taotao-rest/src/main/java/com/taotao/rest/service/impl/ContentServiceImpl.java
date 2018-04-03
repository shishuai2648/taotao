package com.taotao.rest.service.impl;/**
 * Created by lenovo on 2018/4/2.
 */

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.compoent.JedisClient;
import com.taotao.rest.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 *
 * 内容查询服务
 * @author lenovo
 * @create 2018-04-02 16:08
 **/

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Override
    public List<TbContent> getContentList(Long cid) {
        try {
            // 添加缓存
            // 查询数据库之前先查询缓存，如果有直接返回
            String json = jedisClient.hget(REDIS_CONTENT_KEY, cid + "");
            if(!StringUtils.isBlank(json)){
                List<TbContent> contentList = JsonUtils.jsonToList(json, TbContent.class);
                return contentList;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        // 根据cid查询内容列表
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);
        // 返回结果之前，向缓存中添加数据
        try{
            // 向缓存中添加数据
            // 为了规范key,可以使用hash
            // 定义一个保存内容的key，hash中每个项就是cid
            // value是list，需要把list转换成json数据
            jedisClient.hset(REDIS_CONTENT_KEY,cid+"", JsonUtils.objectToJson(contentList));
        }catch (Exception e){
            e.printStackTrace();
        }
        return contentList;
    }

    @Override
    public TaotaoResult syncContent(Long cid) {
        jedisClient.hdel(REDIS_CONTENT_KEY,cid+"");
        return TaotaoResult.ok();
    }
}
