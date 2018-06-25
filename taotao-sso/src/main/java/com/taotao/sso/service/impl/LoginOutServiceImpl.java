package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.sso.compoent.JedisClient;
import com.taotao.sso.service.LoginOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 安全退出
 */
@Service
public class LoginOutServiceImpl implements LoginOutService {


    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_SSION_KEY}")
    private String REDIS_SSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult loginOut(String token) {
        // 根据token值设置对应用户数据过期
        jedisClient.expire(REDIS_SSION_KEY+":"+token,-2);
        return TaotaoResult.ok();
    }
}
