package com.vua.upms.rpc.api;

import com.vua.upms.dao.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by admin on 2017/6/29.
 */
public class UpmsApiServiceMock implements UpmsApiService {

    private static Logger _log = LoggerFactory.getLogger(UpmsApiServiceMock.class);

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserIdByCache(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsRole> selectUpmsRoleByUpmsUserIdByCache(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsRolePermission> selectUpmsRolePermisstionByUpmsRoleId(Integer upmsRoleId) {
        return null;
    }

    @Override
    public List<UpmsUserPermission> selectUpmsUserPermissionByUpmsUserId(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsSystem> selectUpmsSystemByExample(UpmsSystemExample upmsSystemExample) {
        return null;
    }

    @Override
    public List<UpmsOrganization> selectUpmsOrganizationByExample(UpmsOrganizationExample upmsOrganizationExample) {
        return null;
    }

    @Override
    public UpmsUser selectUpmsUserByUsername(String username) {
        return null;
    }

    @Override
    public int insertUpmsLogSelective(UpmsLog record) {
        return 0;
    }
}
