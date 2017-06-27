package com.vua.upms.dao.mapper;

import java.util.List;
import com.vua.upms.dao.model.UpmsPermission;
import com.vua.upms.dao.model.UpmsRole;

/**
 * Created by liunian on 6/24/17.
 */
public interface UpmsApiMapper {
    // 根据用户id获取所拥有的权限
    List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId);

    // 根据用户id获取所属的角色
    List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId);
}
