package com.vua.upms.client.shiro.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by liunian on 6/8/17.
 */
public class UpmsSessionForceLogout extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Session session = this.getSubject(servletRequest, servletResponse).getSession(false);
        if (null == session) {
            return true;
        }
        //true 继续访问
        return (session.getAttribute("FORCE_LOGOUT") == null);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //上面false 到这里处理退出
        getSubject(servletRequest, servletResponse).logout();
        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
        WebUtils.issueRedirect(servletRequest, servletResponse, loginUrl);
        return false;
    }
}
