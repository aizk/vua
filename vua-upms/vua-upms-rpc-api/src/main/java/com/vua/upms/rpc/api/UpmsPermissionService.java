package com.vua.upms.rpc.api;

import com.alibaba.fastjson.JSONArray;
import com.vua.common.base.BaseService;
import com.vua.upms.dao.model.UpmsPermission;
import com.vua.upms.dao.model.UpmsPermissionExample;

/**
 * UpmsPermissionService接口
 * Created on 2017/6/24.
 */
public interface UpmsPermissionService extends BaseService<UpmsPermission, UpmsPermissionExample> {

    JSONArray getTreeByRoleId(Integer id);

    JSONArray getTreeByUserId(Integer userId, Byte type);
}