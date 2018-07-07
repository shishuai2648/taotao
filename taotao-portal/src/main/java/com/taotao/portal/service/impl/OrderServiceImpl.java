package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 订单处理
 */
@Service
public class OrderServiceImpl implements OrderService {


    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;
    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    @Override
    public String createOrder(OrderInfo orderInfo) {
        // 调用服务
        // 将orderInfo转换成json
        String json = JsonUtils.objectToJson(orderInfo);
        // 提交订单数据
        String result = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, json);
        // 转换成java对象
        TaotaoResult taotaoResult = TaotaoResult.format(result);
        String orderId = taotaoResult.getData().toString();
        return orderId;
    }



}
