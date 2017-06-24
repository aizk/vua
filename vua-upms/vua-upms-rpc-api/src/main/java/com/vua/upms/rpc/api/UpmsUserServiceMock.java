package com.vua.upms.rpc.api;

import com.vua.common.base.BaseServiceMock;
import com.vua.upms.dao.mapper.UpmsUserMapper;
import com.vua.upms.dao.model.UpmsUser;
import com.vua.upms.dao.model.UpmsUserExample;

/**
* 降级实现UpmsUserService接口
* Created on 2017/6/24.
*/
public class UpmsUserServiceMock extends BaseServiceMock<UpmsUserMapper, UpmsUser, UpmsUserExample> implements UpmsUserService {

}
