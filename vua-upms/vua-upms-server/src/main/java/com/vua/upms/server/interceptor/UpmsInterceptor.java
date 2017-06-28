package com.vua.upms.server.interceptor;

import com.vua.upms.dao.model.UpmsUser;
import com.vua.upms.rpc.api.UpmsApiService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2017/6/28.
 */
public class UpmsInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UpmsApiService upmsApiService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // filter ajax
        if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requestd-With").equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        // User
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
        request.setAttribute("upmsUser", upmsUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
