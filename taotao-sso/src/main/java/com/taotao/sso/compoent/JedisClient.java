package com.taotao.sso.compoent;/**
 * Created by lenovo on 2018/4/3.
 */

/**
 * @author lenovo
 * @create 2018-04-03 13:13
 **/
public interface JedisClient {

    String set(String key, String value);

    String get(String key);

    Long hset(String key, String item, String value);

    String hget(String key, String item);

    Long incr(String key);  // 加一

    Long decr(String key);  // 减一

    Long expire(String key, int second); // 设置键过期时间

    Long ttl(String key);   // 键过期数 整数为倒计时  -1为永久保存 -2为已过期

    Long hdel(String key, String item); // 删除map中的key值

}
