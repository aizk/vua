package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsOrganizationMapper;
import com.vua.upms.dao.model.UpmsOrganization;
import com.vua.upms.dao.model.UpmsOrganizationExample;
import com.vua.upms.rpc.api.UpmsOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UpmsOrganizationService实现
 * Created on 2017/6/24.
 */
@Service
@Transactional
@BaseService
public class UpmsOrganizationServiceImpl extends BaseServiceImpl<UpmsOrganizationMapper, UpmsOrganization, UpmsOrganizationExample> implements UpmsOrganizationService {

    private static Logger _log = LoggerFactory.getLogger(UpmsOrganizationServiceImpl.class);

    @Autowired
    UpmsOrganizationMapper upmsOrganizationMapper;

}