package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

public interface LoginOutService {

    TaotaoResult loginOut(String token);
}
