package com.vua.upms.rpc.api;

import com.vua.common.base.BaseServiceMock;
import com.vua.upms.dao.mapper.UpmsSystemMapper;
import com.vua.upms.dao.model.UpmsSystem;
import com.vua.upms.dao.model.UpmsSystemExample;

/**
* 降级实现UpmsSystemService接口
* Created on 2017/6/24.
*/
public class UpmsSystemServiceMock extends BaseServiceMock<UpmsSystemMapper, UpmsSystem, UpmsSystemExample> implements UpmsSystemService {

}
