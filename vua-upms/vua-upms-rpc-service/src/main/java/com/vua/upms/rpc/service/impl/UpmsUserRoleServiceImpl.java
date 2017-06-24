package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsUserRoleMapper;
import com.vua.upms.dao.model.UpmsUserRole;
import com.vua.upms.dao.model.UpmsUserRoleExample;
import com.vua.upms.rpc.api.UpmsUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsUserRoleService实现
* Created on 2017/6/24.
*/
@Service
@Transactional
@BaseService
public class UpmsUserRoleServiceImpl extends BaseServiceImpl<UpmsUserRoleMapper, UpmsUserRole, UpmsUserRoleExample> implements UpmsUserRoleService {

private static Logger _log = LoggerFactory.getLogger(UpmsUserRoleServiceImpl.class);

@Autowired
UpmsUserRoleMapper upmsUserRoleMapper;

}