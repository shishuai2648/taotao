package com.taotao.portal;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

   @Autowired
   private UserService userService;
   @Value("${SSO_LOGIN_URL}")
   private String SSO_LOGIN_URL;

    // 执行之前,false
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.拦截请求的url
        //2.从Cookie中获取token值
        //3.如果未获得token跳转到登录页面
        //4.取到token，调用sso系统的服务查询用户信息。
        TbUser user = userService.getUserByToken(request, response);
        //5.若是用户Redis中模拟的session已经过期，跳转到登录界面
        if(user == null){
            // URL跳转
            response.sendRedirect(SSO_LOGIN_URL + "?redirectUrl=" +request.getRequestURL());
            return false;
        }
        //6.若是没有过期，放行，并将过期时间刷新为最大
        // 将用户对象放入request中
        request.setAttribute("user",user);
        return true;
    }

    // 执行完成之后，返回视图之前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // 完成之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
