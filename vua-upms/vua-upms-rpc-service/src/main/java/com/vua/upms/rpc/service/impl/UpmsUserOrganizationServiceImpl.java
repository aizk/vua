package com.vua.upms.rpc.service.impl;

import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsUserOrganizationMapper;
import com.vua.upms.dao.model.UpmsUserOrganization;
import com.vua.upms.dao.model.UpmsUserOrganizationExample;
import com.vua.upms.rpc.api.UpmsUserOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsUserOrganizationService实现
* Created on 2017/6/24.
*/
@Service
@Transactional
@BaseService
public class UpmsUserOrganizationServiceImpl extends BaseServiceImpl<UpmsUserOrganizationMapper, UpmsUserOrganization, UpmsUserOrganizationExample> implements UpmsUserOrganizationService {

private static Logger _log = LoggerFactory.getLogger(UpmsUserOrganizationServiceImpl.class);

@Autowired
UpmsUserOrganizationMapper upmsUserOrganizationMapper;

}