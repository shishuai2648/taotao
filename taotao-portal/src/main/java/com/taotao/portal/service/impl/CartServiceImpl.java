package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.PortalItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.jasperreports.JasperReportsCsvView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车实现
 */
@Service
public class CartServiceImpl implements CartService {


    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;

    @Autowired
    private ItemService itemService;

    @Override
    public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //1. 接收商品id
        //2. 从购物车获取商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        //3. 从商品列表中查询列表中是否存在此商品
        boolean haveFlg = false;
        for(CartItem cartItem : cartItemList){
            //4. 如果存在商品，则在原有数量上加上当期增加的数量
            if(cartItem.getId().equals(itemId)){
                cartItem.setNum(cartItem.getNum()+num);
                haveFlg = true;
                break;
            }
        }
        //5. 如果不存在，调用rest，根据商品id获得商品数据
        if(!haveFlg){
            CartItem cartItem = new CartItem();
            PortalItem item = itemService.getItemById(itemId);
            // 转换后才能CartItem
            cartItem.setId(itemId);
            cartItem.setNum(num);
            cartItem.setPrice(item.getPrice());
            cartItem.setTitle(item.getTitle());
            if(StringUtils.isNotBlank(item.getImage())) {
                cartItem.setImage(item.getImage());
            }
            //6. 将商品数据添加到列表中
            cartItemList.add(cartItem);
        }
        //7. 将购物车列表写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(cartItemList),COOKIE_EXPIRE,true);
        //8. 返回taotaoResult
        return TaotaoResult.ok();
    }

    @Override
    public List<CartItem> getCartItems(HttpServletRequest request) {
        List<CartItem> list = getCartItemList(request);
        return list;
    }

    @Override
    public TaotaoResult updateCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        // 从Cookie中获取购物车商品列表
        List<CartItem> itemList = getCartItemList(request);
        // 根据商品id查询商品
        for(CartItem cartItem : itemList){
            if(cartItem.getId().equals(itemId)){
                // 更新数量
                cartItem.setNum(num);
                break;
            }
        }
        // 将数据写回Cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList),COOKIE_EXPIRE,true);

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteCartItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        // 从Cookie中获取对应的商品列表信息
        List<CartItem> itemList = getCartItemList(request);
        // 遍历itemList并删除对应信息
        for(CartItem item : itemList){
            if(item.getId().equals(itemId)){
                itemList.remove(item);
                break;
            }
        }
        // 将数据写回到Cookie
        CookieUtils.setCookie(request,response,"TT_CART",JsonUtils.objectToJson(itemList),COOKIE_EXPIRE,true);
        return TaotaoResult.ok();
    }

    /**
     * 获取购物车商品列表
     * @param request
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request){
        try {
            // 从cookie中获取商品列表
            String json = CookieUtils.getCookieValue(request, "TT_CART", true);
            // 将json转换成java对象
            List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
            return list == null ? new ArrayList<CartItem>() : list;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<CartItem>();
        }
    }
}
