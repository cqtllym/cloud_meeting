package com.rjxy.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Joheey
 * Date: 2021/7/7
 * Time: 10:36
 * Description: 权限页面的后端拦截
 */
public class SessionInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
//        return false;
//    }
/*        //普通路径放行
        if ("/user/register".equals(arg0.getRequestURI()) || "/user/login".equals(arg0.getRequestURI())) {
            return true;
        }
        //权限路径拦截
        Object object = arg0.getSession().getAttribute("sessionId");
        return object != null;
    }*/

}
