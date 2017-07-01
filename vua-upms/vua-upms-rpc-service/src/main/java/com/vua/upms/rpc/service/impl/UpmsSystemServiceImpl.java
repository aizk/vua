package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsSystemMapper;
import com.vua.upms.dao.model.UpmsSystem;
import com.vua.upms.dao.model.UpmsSystemExample;
import com.vua.upms.rpc.api.UpmsSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UpmsSystemService实现
 * Created on 2017/6/24.
 */
@Service
@Transactional
@BaseService
public class UpmsSystemServiceImpl extends BaseServiceImpl<UpmsSystemMapper, UpmsSystem, UpmsSystemExample> implements UpmsSystemService {

    private static Logger _log = LoggerFactory.getLogger(UpmsSystemServiceImpl.class);

    @Autowired
    UpmsSystemMapper upmsSystemMapper;

}