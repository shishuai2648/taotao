package com.taotao.portal.controller;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 订单处理
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request,Model model){
        List<CartItem> cartItems = cartService.getCartItems(request);
        model.addAttribute("cartList",cartItems);
        return "order-cart";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,Model model,HttpServletRequest request){
        // 获取用户信息
        TbUser user = (TbUser)request.getAttribute("user");
        // 补全OrderInfo属性
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        // 调用服务
        String orderId = orderService.createOrder(orderInfo);
        // 将订单号传递给页面
        model.addAttribute("orderId",orderId);
        model.addAttribute("payment",orderInfo.getPayment());
        model.addAttribute("date",new DateTime().plusDays(3).toString("yyyy-MM-dd"));
        // 返回逻辑视图
        return "success";
    }
}
