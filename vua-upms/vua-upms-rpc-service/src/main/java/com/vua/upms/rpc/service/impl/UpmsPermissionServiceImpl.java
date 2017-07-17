package com.vua.upms.rpc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vua.common.annotation.BaseService;
import com.vua.common.base.BaseServiceImpl;
import com.vua.upms.dao.mapper.UpmsPermissionMapper;
import com.vua.upms.dao.mapper.UpmsSystemMapper;
import com.vua.upms.dao.mapper.UpmsUserPermissionMapper;
import com.vua.upms.dao.model.*;
import com.vua.upms.rpc.api.UpmsApiService;
import com.vua.upms.rpc.api.UpmsPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    UpmsSystemMapper upmsSystemMapper;

    @Autowired
    UpmsUserPermissionMapper upmsUserPermissionMapper;

    @Autowired
    UpmsApiService upmsApiService;

    @Autowired
    UpmsPermissionMapper upmsPermissionMapper;


    @Override
    public JSONArray getTreeByRoleId(Integer id) {
        //角色已有权限
        List<UpmsRolePermission> rolePermissions = upmsApiService.selectUpmsRolePermisstionByUpmsRoleId(id);

        //system
        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        upmsSystemExample.createCriteria().andStatusEqualTo((byte)1);
        upmsSystemExample.setOrderByClause("orders asc");
        List<UpmsSystem> upmsSystems = upmsSystemMapper.selectByExample(upmsSystemExample);

        JSONArray systems = upmsSystems.parallelStream().map(x -> {
            JSONObject node = new JSONObject();
            node.put("id", x.getSystemId());
            node.put("name", x.getTitle());
            node.put("nocheck", true);
            node.put("open", true);
            return node;
        }).collect(JSONArray::new,
                JSONArray::add,
                JSONArray::addAll);

//        if (systems.size() > 0) {
//            systems.stream().map( x -> {
//                UpmsPermissionExample upmsPermissionExample = new UpmsPermissionExample();
//                upmsPermissionExample.createCriteria()
//                        .andStatusEqualTo((byte)1)
//                        .andSystemIdEqualTo(((JSONObject)x).getIntValue("id"));
//                upmsPermissionExample.setOrderByClause("orders asc");
//                List<UpmsPermission> upmsPermissions = upmsPermissionMapper.selectByExample(upmsPermissionExample);
//
//            })
//        }
        return systems;
    }

    @Override
    public JSONArray getTreeByUserId(Integer userId, Byte type) {
        return null;
    }
}