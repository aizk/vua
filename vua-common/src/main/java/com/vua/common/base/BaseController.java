package com.vua.common.base;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2017/6/29.
 */
public class BaseController {

    private final static Logger _log = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        _log.error("统一异常处理：", exception);
        request.setAttribute("ex", exception);
        if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
            request.setAttribute("requestHeader", "ajax");
        }

        //无权限
        if (exception instanceof UnauthorizedException) {
            return "/403.jsp";
        }
        //shiro session 过期
        if (exception instanceof InvalidSessionException) {
            return "/error.jsp";
        }
        return "/error.jsp";
    }
}
