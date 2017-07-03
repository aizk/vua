package com.vua.upms.rpc.service.impl;

import com.vua.upms.dao.mapper.*;
import com.vua.upms.dao.model.*;
import com.vua.upms.rpc.api.UpmsApiService;
import com.vua.upms.dao.mapper.UpmsApiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by liunian on 6/24/17.
 */

public class UpmsApiServiceImpl implements UpmsApiService{

    private static Logger _log = LoggerFactory.getLogger(UpmsApiServiceImpl.class);

    @Autowired
    UpmsUserMapper upmsUserMapper;

    @Autowired
    UpmsApiMapper upmsApiMapper;

    @Autowired
    UpmsRolePermissionMapper upmsRolePermissionMapper;

    @Autowired
    UpmsUserPermissionMapper upmsUserPermissionMapper;

    @Autowired
    UpmsSystemMapper upmsSystemMapper;

    @Autowired
    UpmsOrganizationMapper upmsOrganizationMapper;

    @Autowired
    UpmsLogMapper upmsLogMapper;

    //获取用户id所拥有的权限
    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser && 1 == upmsUser.getLocked()) {
            _log.info("selectUpmsPermissionByUpmsUserId : upmsUserId = {}", upmsUserId);
        }
        List<UpmsPermission> upmsPermissions = upmsApiMapper.selectUpmsPermissionByUpmsUserId(upmsUserId);
        return upmsPermissions;
    }

    @Override
    @Cacheable(value = "vua-upms-rpc-service-encache", key = "'selectUpmsPermissionByUpmsUserId_' + #upmsUserId")
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserIdByCache(Integer upmsUserId) {
        return selectUpmsPermissionByUpmsUserId(upmsUserId);
    }

    //获取用户id拥有的角色
    @Override
    public List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        UpmsUser upmsUser = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        if (null == upmsUser && 1 == upmsUser.getLocked()) {
            _log.info("selectUpmsPermissionByUpmsUserId : upmsUserId = {}", upmsUserId);
        }
        List<UpmsRole> upmsRoles = upmsApiMapper.selectUpmsRoleByUpmsUserId(upmsUserId);
        return upmsRoles;
    }

    @Override
    @Cacheable(value = "vua-upms-rpc-service-encache", key = "'selectUpmsRoleByUpmsUserId_' + #upmsUserId")
    public List<UpmsRole> selectUpmsRoleByUpmsUserIdByCache(Integer upmsUserId) {
        return selectUpmsRoleByUpmsUserId(upmsUserId);
    }

    //根据角色Id获取所拥有的权限
    @Override
    public List<UpmsRolePermission> selectUpmsRolePermisstionByUpmsRoleId(Integer upmsRoleId) {
        UpmsRolePermissionExample upmsRolePermissionExample = new UpmsRolePermissionExample();
        upmsRolePermissionExample.createCriteria().andRoleIdEqualTo(upmsRoleId);
        List<UpmsRolePermission> upmsRolePermissions = upmsRolePermissionMapper.selectByExample(upmsRolePermissionExample);
        return upmsRolePermissions;
    }

    @Override
    public List<UpmsUserPermission> selectUpmsUserPermissionByUpmsUserId(Integer upmsUserId) {
        UpmsUserPermissionExample upmsUserPermissionExample = new UpmsUserPermissionExample();
        upmsUserPermissionExample.createCriteria().andUserIdEqualTo(upmsUserId);
        List<UpmsUserPermission> upmsUserPermissions = upmsUserPermissionMapper.selectByExample(upmsUserPermissionExample);
        return upmsUserPermissions;
    }

    //根据条件获取系统数据
    @Override
    public List<UpmsSystem> selectUpmsSystemByExample(UpmsSystemExample upmsSystemExample) {
        return upmsSystemMapper.selectByExample(upmsSystemExample);
    }

    @Override
    public List<UpmsOrganization> selectUpmsOrganizationByExample(UpmsOrganizationExample upmsOrganizationExample) {
        return upmsOrganizationMapper.selectByExample(upmsOrganizationExample);
    }

    //根据username获取user
    @Override
    public UpmsUser selectUpmsUserByUsername(String username) {
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        upmsUserExample.createCriteria().andUsernameEqualTo(username);
        List<UpmsUser> upmsUsers = upmsUserMapper.selectByExample(upmsUserExample);
        if (null != upmsUsers && upmsUsers.size() > 0) {
            return upmsUsers.get(0);
        }
        return null;
    }

    @Override
    public int insertUpmsLogSelective(UpmsLog record) {
        return upmsLogMapper.insertSelective(record);
    }
}
