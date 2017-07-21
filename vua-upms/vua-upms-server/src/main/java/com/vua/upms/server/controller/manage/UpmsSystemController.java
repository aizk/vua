package com.vua.upms.server.controller.manage;

import com.vua.common.base.BaseController;
import com.vua.upms.dao.model.UpmsSystem;
import com.vua.upms.dao.model.UpmsSystemExample;
import com.vua.upms.rpc.api.UpmsSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


@Controller
@Api(value = "系统管理")
@RequestMapping("/manage/system")
public class UpmsSystemController extends BaseController {

    private static Logger _log = LoggerFactory.getLogger(UpmsSystemController.class);

    @Autowired
    private UpmsSystemService upmsSystemService;

    @ApiOperation("系统首页")
    @RequiresPermissions("upms:system:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/system/index";
    }

    @ApiOperation("系统列表")
    @RequiresPermissions("upms:system:list")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order) {
        UpmsSystemExample upmsSystemExample = new UpmsSystemExample();
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            upmsSystemExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            upmsSystemExample.or().andTitleLike("%" + search + "%");
        }
        List<UpmsSystem> upmsSystems = upmsSystemService.selectByExampleForOffsetPage(upmsSystemExample, offset, limit);
        int total = upmsSystemService.countByExample(upmsSystemExample);
        Map<String, Object> result = new HashMap<>();
        result.put("rows", upmsSystems);
        result.put("total", total);
        return result;

    }
}
