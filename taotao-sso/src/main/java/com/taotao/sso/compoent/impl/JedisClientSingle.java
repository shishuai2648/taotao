package com.taotao.sso.compoent.impl;
/**
 * Created by lenovo on 2018/4/3.
 */

import com.taotao.sso.compoent.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis单机版实现类
 * @author lenovo
 * @create 2018-04-03 13:21
 **/
public class JedisClientSingle implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 设置值
     * @param key       键
     * @param value     值
     * @return
     */
    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }

    /**
     * 根据键获取值
     * @param key   键
     * @return
     */
    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    /**
     * 向map中存放数据Map<StringKey,StringValue>
     * @param key       键      Map
     * @param item      键      StringKey
     * @param value     值      StringValue
     * @return
     */
    @Override
    public Long hset(String key, String item, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(key,item,value);
        jedis.close();
        return result;
    }

    /**
     * 从map中获取键值
     * @param key       键
     * @param item      键
     * @return
     */
    @Override
    public String hget(String key, String item) {
        Jedis jedis = jedisPool.getResource();
        String result = jedis.hget(key,item);
        jedis.close();
        return result;
    }

    /**
     * 数值自增1
     * @param key   键
     * @return
     */
    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    /**
     * 数值自减1
     * @param key   键
     * @return
     */
    @Override
    public Long decr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.decr(key);
        jedis.close();
        return result;
    }

    /**
     * 设置键的过期时间
     * @param key       键
     * @param second    过期时间    整数为在指定second时间后过期，-1为永久保留，-2位已过期
     * @return
     */
    @Override
    public Long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key,second);
        jedis.close();
        return result;
    }

    /**
     * 查看当前键的过期时间
     * @param key       键
     * @return
     */
    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public Long hdel(String key, String item) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(key);
        jedis.close();
        return result;
    }
}
