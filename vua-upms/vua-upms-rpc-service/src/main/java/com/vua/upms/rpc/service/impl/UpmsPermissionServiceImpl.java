package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsPermissionMapper;
import com.vua.upms.dao.model.UpmsPermission;
import com.vua.upms.dao.model.UpmsPermissionExample;
import com.vua.upms.rpc.api.UpmsPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsPermissionService实现
* Created on 2017/6/24.
*/
@Service
@Transactional
@BaseService
public class UpmsPermissionServiceImpl extends BaseServiceImpl<UpmsPermissionMapper, UpmsPermission, UpmsPermissionExample> implements UpmsPermissionService {

private static Logger _log = LoggerFactory.getLogger(UpmsPermissionServiceImpl.class);

@Autowired
UpmsPermissionMapper upmsPermissionMapper;

}