package com.vua.upms.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.vua.common.base.BaseServiceMock;
import com.vua.upms.dao.mapper.UpmsPermissionMapper;
import com.vua.upms.dao.model.UpmsPermission;
import com.vua.upms.dao.model.UpmsPermissionExample;

/**
* 降级实现UpmsPermissionService接口
* Created on 2017/6/24.
*/
public class UpmsPermissionServiceMock extends BaseServiceMock<UpmsPermissionMapper, UpmsPermission, UpmsPermissionExample> implements UpmsPermissionService {

    @Override
    public JSONArray getTreeByRoleId(Integer id) {
        return null;
    }

    @Override
    public JSONArray getTreeByUserId(Integer userId, Byte type) {
        return null;
    }
}
