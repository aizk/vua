package com.vua.upms.server.controller.manage;


import com.vua.common.base.BaseController;
import com.vua.upms.rpc.api.UpmsLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Api(value = "日志管理", description = "日志管理")
@RequestMapping("/manage/log")
public class UpmsLogController extends BaseController {

    @Autowired
    private UpmsLogService upmsLogService;
}
