package com.vua.upms.client.shiro.realm;

import com.vua.common.util.MD5Util;
import com.vua.common.util.PropertiesFileUtil;
import com.vua.upms.dao.model.UpmsPermission;
import com.vua.upms.dao.model.UpmsRole;
import com.vua.upms.dao.model.UpmsUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.vua.upms.rpc.api.UpmsApiService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证 授权
 * Created by liunian on 6/8/17.
 */
public class UpmsRealm extends AuthorizingRealm {

    private static Logger _log = LoggerFactory.getLogger(UpmsRealm.class);

    @Autowired
    private UpmsApiService upmsApiService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.getPrimaryPrincipal();
        UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
        List<UpmsRole> upmsRoles = upmsApiService.selectUpmsRoleByUpmsUserId(upmsUser.getUserId());
        List<UpmsPermission> upmsPermissions = upmsApiService.selectUpmsPermissionByUpmsUserId(upmsUser.getUserId());

        Set<String> roles = new HashSet<>();
        for (UpmsRole upmsRole : upmsRoles) {
            if (StringUtils.isNotBlank(upmsRole.getName())) {
                roles.add(upmsRole.getName());
            }
        }

        Set<String> permissions = new HashSet<>();
        for (UpmsPermission upmsPermission : upmsPermissions) {
            if (StringUtils.isNotBlank(upmsPermission.getPermissionValue())) {
                permissions.add(upmsPermission.getPermissionValue());
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        String password = new String((char[])authenticationToken.getPrincipal());

        //单点登陆认证 client无密认证
        String upmsType = PropertiesFileUtil.getInstance("vua-upms-client").get("vua.upms.type");
        if ("client".equals(upmsType)) {
            return new SimpleAuthenticationInfo(username, password, getName());
        }

        UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);

        if (null == upmsUser) {
            throw new UnknownAccountException();
        }
        if (!upmsUser.getPassword().equals(MD5Util.MD5( password + upmsUser.getSalt()))) {
            throw new IncorrectCredentialsException();
        }
        if (upmsUser.getLocked() == 1) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
