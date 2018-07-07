package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户管理
 */
@Service
public class UserServiceImpl implements UserService {


    /*#SSO系统的url
    SSO_BASE_URL=http://localhost:8084
            #查询用户信息的服务
    SSO_USER_TOKEN_SERVICE=/user/token/
    */
    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_TOKEN_SERVICE}")
    private String SSO_USER_TOKEN_SERVICE;

    @Override
    public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 从cookie中获取token
            String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
            // 判断token是否存在值
            if (StringUtils.isBlank(token)) {
                return null;
            }
            // 调用sso服务查询用户信息
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
            // 将json转换成java对象
            TaotaoResult result = TaotaoResult.format(json);
            if (result.getStatus() != 200) {
                return null;
            }
            result = TaotaoResult.formatToPojo(json, TbUser.class);
            TbUser user = (TbUser) result.getData();
            return user;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
