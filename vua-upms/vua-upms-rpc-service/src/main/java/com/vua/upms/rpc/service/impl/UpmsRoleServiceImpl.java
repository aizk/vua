package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsRoleMapper;
import com.vua.upms.dao.model.UpmsRole;
import com.vua.upms.dao.model.UpmsRoleExample;
import com.vua.upms.rpc.api.UpmsRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UpmsRoleService实现
 * Created on 2017/6/24.
 */
@Service
@Transactional
@BaseService
public class UpmsRoleServiceImpl extends BaseServiceImpl<UpmsRoleMapper, UpmsRole, UpmsRoleExample> implements UpmsRoleService {

    private static Logger _log = LoggerFactory.getLogger(UpmsRoleServiceImpl.class);

    @Autowired
    UpmsRoleMapper upmsRoleMapper;

}