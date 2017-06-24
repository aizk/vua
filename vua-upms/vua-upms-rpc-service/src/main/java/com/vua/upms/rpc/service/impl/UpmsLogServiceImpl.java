package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsLogMapper;
import com.vua.upms.dao.model.UpmsLog;
import com.vua.upms.dao.model.UpmsLogExample;
import com.vua.upms.rpc.api.UpmsLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsLogService实现
* Created on 2017/6/24.
*/
@Service
@Transactional
@BaseService
public class UpmsLogServiceImpl extends BaseServiceImpl<UpmsLogMapper, UpmsLog, UpmsLogExample> implements UpmsLogService {

private static Logger _log = LoggerFactory.getLogger(UpmsLogServiceImpl.class);

@Autowired
UpmsLogMapper upmsLogMapper;

}