package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsRolePermissionMapper;
import com.vua.upms.dao.model.UpmsRolePermission;
import com.vua.upms.dao.model.UpmsRolePermissionExample;
import com.vua.upms.rpc.api.UpmsRolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsRolePermissionService实现
* Created on 2017/6/24.
*/
@Service
@Transactional
@BaseService
public class UpmsRolePermissionServiceImpl extends BaseServiceImpl<UpmsRolePermissionMapper, UpmsRolePermission, UpmsRolePermissionExample> implements UpmsRolePermissionService {

private static Logger _log = LoggerFactory.getLogger(UpmsRolePermissionServiceImpl.class);

@Autowired
UpmsRolePermissionMapper upmsRolePermissionMapper;

}