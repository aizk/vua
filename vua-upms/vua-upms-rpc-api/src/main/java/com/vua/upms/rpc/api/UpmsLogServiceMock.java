package com.vua.upms.rpc.api;

import com.vua.common.base.BaseServiceMock;
import com.vua.upms.dao.mapper.UpmsLogMapper;
import com.vua.upms.dao.model.UpmsLog;
import com.vua.upms.dao.model.UpmsLogExample;

/**
* 降级实现UpmsLogService接口
* Created on 2017/6/24.
*/
public class UpmsLogServiceMock extends BaseServiceMock<UpmsLogMapper, UpmsLog, UpmsLogExample> implements UpmsLogService {

}
