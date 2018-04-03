package com.taotao.httpclient;/**
 * Created by lenovo on 2018/4/3.
 */

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lenovo
 * @create 2018-04-03 12:43
 **/
public class JedisTest {

    @Test
    public void testJedisSingle() throws Exception{
        // 创建一个jedis对象
        Jedis jedis = new Jedis("39.107.113.43",7002);
        jedis.set("test","hello redis");
        String test = jedis.get("test");
        System.out.println(test);
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception{
        // 创建一个连接池对象
        // 系统中应该是单例的。
        JedisPool jedisPool = new JedisPool("39.107.113.43",7001);
        // 从连接池中获得一个连接
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("test");
        System.out.println(result);
        // jedis必须要关闭
        jedis.close();
        // 当系统关闭时关闭连接池
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception{
        // 创建一个JedisCluster对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("39.107.113.43",7001));
        nodes.add(new HostAndPort("39.107.113.43",7002));
        nodes.add(new HostAndPort("39.107.113.43",7003));
        nodes.add(new HostAndPort("39.107.113.43",7004));
        nodes.add(new HostAndPort("39.107.113.43",7005));
        nodes.add(new HostAndPort("39.107.113.43",7006));
        // 在nodes中指定每个节点的地址
        // jedisCluster在系统中是单例的
        JedisCluster cluster = new JedisCluster(nodes);
        String set = cluster.set("name", "zhangsan");
        String name = cluster.get("name");
        System.out.println(name);
        // 系统关闭时关闭连接池
        cluster.close();
    }
}