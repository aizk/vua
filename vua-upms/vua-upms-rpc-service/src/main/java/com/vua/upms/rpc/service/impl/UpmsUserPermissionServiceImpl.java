package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsUserPermissionMapper;
import com.vua.upms.dao.model.UpmsUserPermission;
import com.vua.upms.dao.model.UpmsUserPermissionExample;
import com.vua.upms.rpc.api.UpmsUserPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsUserPermissionService实现
* Created on 2017/6/24.
*/
@Service
@Transactional
@BaseService
public class UpmsUserPermissionServiceImpl extends BaseServiceImpl<UpmsUserPermissionMapper, UpmsUserPermission, UpmsUserPermissionExample> implements UpmsUserPermissionService {

private static Logger _log = LoggerFactory.getLogger(UpmsUserPermissionServiceImpl.class);

@Autowired
UpmsUserPermissionMapper upmsUserPermissionMapper;

}