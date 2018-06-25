package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.compoent.JedisClient;
import com.taotao.sso.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * 用户登录功能
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_SSION_KEY}")
    private String REDIS_SSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;


    @Override
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        // 判断用户密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        // 获取对应的用户信息
        if(tbUsers == null || tbUsers.isEmpty()){
            return TaotaoResult.build(400,"用户名不存在");
        }
        TbUser user = tbUsers.get(0);
        // 校验密码
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            return TaotaoResult.build(400,"密码错误");
        }
        // 登录成功
        // 生成token
        String token = UUID.randomUUID().toString();
        // 将用户信息写入redis
        // key: REDIS_SESSION:{TOKEN}
        // value: user(JSON格式)
        // 将密码清空
        user.setPassword(null);
        jedisClient.set(REDIS_SSION_KEY+":"+token,JsonUtils.objectToJson(user));
        // 设置redis模拟的session的过期时间
        jedisClient.expire(REDIS_SSION_KEY+":"+token,SESSION_EXPIRE);
        // 写入cookie
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);

        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        // 根据token获取用户信息
        String json = jedisClient.get(REDIS_SSION_KEY + ":" + token);
        // 判断是否查询到结果
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户session已经过期");
        }
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        // 更新redis中对应数据的过期时间
        jedisClient.expire(REDIS_SSION_KEY + ":" + token,SESSION_EXPIRE);
        return TaotaoResult.ok(user);
    }
}
