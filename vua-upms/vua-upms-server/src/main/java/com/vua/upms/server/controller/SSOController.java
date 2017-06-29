package com.vua.upms.server.controller;

import com.vua.common.base.BaseController;
import com.vua.common.util.RedisUtil;
import com.vua.upms.client.shiro.session.UpmsSession;
import com.vua.upms.client.shiro.session.UpmsSessionDao;
import com.vua.upms.common.constant.UpmsResult;
import com.vua.upms.common.constant.UpmsResultConstant;
import com.vua.upms.dao.model.UpmsSystemExample;
import com.vua.upms.rpc.api.UpmsSystemService;
import com.vua.upms.rpc.api.UpmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by admin on 2017/6/29.
 */
@Controller
@RequestMapping("/sso")
@Api(value = "单点登陆管理", description = "单点登陆管理")
public class SSOController extends BaseController {

    private final static Logger _log = LoggerFactory.getLogger(SSOController.class);
    // global session key
    private final static String VUA_UPMS_SERVER_SESSION_ID = "vua-upms-server-session-id";
    // global session list key 会话Id列表
    private final static String VUA_UPMS_SERVER_SESSION_IDS = "vua-upms-server-session-ids";
    // code key
    private final static String VUA_UPMS_SERVER_CODE = "vua-upms-server-code";

    @Autowired
    UpmsSystemService upmsSystemService;

    @Autowired
    UpmsUserService upmsUserService;

    @Autowired
    UpmsSessionDao upmsSessionDao;

    @ApiOperation(value = "认证中心首页")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) throws Exception {
        String appId = request.getParameter("appid");
        //返回的Url
        String backUrl = request.getParameter("backurl");
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("无效访问");
        }
        //判断认证系统是否注册
        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        upmsSystemExample.createCriteria().andNameEqualTo(appId);
        int count = upmsSystemService.countByExample(upmsSystemExample);
        if (0 == count) {
            throw new RuntimeException(String.format("未注册的系统:%s", appId));
        }
        return "redirect:/sso/login?backurl=" + URLEncoder.encode(backUrl, "utf-8");
    }

    @ApiOperation(value = "登陆-GET")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();
        //判断是否登陆，已经登陆则回跳
        String code = RedisUtil.get(VUA_UPMS_SERVER_SESSION_ID + "_" + serverSessionId);

        if (StringUtils.isNotBlank(code)) {
            String backUrl = request.getParameter("backurl");
            String username = (String)subject.getPrincipal();
            if (StringUtils.isBlank(backUrl)) {
                backUrl = "/";
            } else {
                if (backUrl.contains("?")) {
                    backUrl += "&upms_code=" + code + "&upms_username=" + username;
                } else {
                    backUrl += "&upms_code=" + code + "&upms_username=" + username;
                }
            }
            _log.debug("认证中心账号通过，带code回跳:{}", backUrl);
            return "redirect:" + backUrl;
        }
        return "sso/login.jsp";
    }

    @ApiOperation(value = "登陆-POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        if (StringUtils.isBlank(username)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_PASSWORD, "账号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_PASSWORD, "密码不能为空");
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        // 判断是否登陆，如果已经登陆，则回跳，防止重复登陆
        String hasCode = RedisUtil.get(VUA_UPMS_SERVER_SESSION_ID + "_" + sessionId);
        if (StringUtils.isBlank(hasCode)) {
            //shiro 认证
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                if (BooleanUtils.toBoolean(rememberMe)) {
                    usernamePasswordToken.setRememberMe(true);
                } else {
                    usernamePasswordToken.setRememberMe(false);
                }
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_USERNAME, "账号不存在!");
            } catch (IncorrectCredentialsException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_PASSWORD, "密码错误!");
            } catch (LockedAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVILID_ACCOUNT, "账号已经锁定");
            }
            //更新session状态
            upmsSessionDao.updateStatus(sessionId, UpmsSession.OnlineStatus.on_line);
            //Global sessions list
            RedisUtil.lpush(VUA_UPMS_SERVER_SESSION_IDS, sessionId.toString());
            //默认验证账号密码正确 创建code
            String code = UUID.randomUUID().toString();
            //Global session code
            RedisUtil.set(VUA_UPMS_SERVER_SESSION_ID + "_" + sessionId, code, (int)subject.getSession().getTimeout() / 1000);
            //code
            RedisUtil.set(VUA_UPMS_SERVER_CODE + "_" + code, code, (int) subject.getSession().getTimeout() / 1000);
        }
        String backUrl = request.getParameter("backurl");
        if (StringUtils.isBlank(backUrl)) {
            return new UpmsResult(UpmsResultConstant.SUCCESS, "/");
        } else {
            return new UpmsResult(UpmsResultConstant.SUCCESS, backUrl);
        }
    }

    @ApiOperation(value = "校验code")
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ResponseBody
    public Object code(HttpServletRequest request) {
        String codeParam = request.getParameter("code");
        String code = RedisUtil.get(VUA_UPMS_SERVER_CODE + "_" + codeParam);
        if (StringUtils.isBlank(codeParam) || !codeParam.equals(code)) {
            new UpmsResult(UpmsResultConstant.FAILED, "无效code");
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS, code);
    }

    @ApiOperation(value = "退出登陆")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        //shiro退出
        SecurityUtils.getSubject().logout();
        //redirect back
        String redirectUrl = request.getHeader("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        return "redirect:" + redirectUrl;
    }
}
