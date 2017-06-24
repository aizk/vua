package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsUserMapper;
import com.vua.upms.dao.model.UpmsUser;
import com.vua.upms.dao.model.UpmsUserExample;
import com.vua.upms.rpc.api.UpmsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsUserService实现
* Created on 2017/6/24.
*/
@Service
@Transactional
@BaseService
public class UpmsUserServiceImpl extends BaseServiceImpl<UpmsUserMapper, UpmsUser, UpmsUserExample> implements UpmsUserService {

private static Logger _log = LoggerFactory.getLogger(UpmsUserServiceImpl.class);

@Autowired
UpmsUserMapper upmsUserMapper;

}