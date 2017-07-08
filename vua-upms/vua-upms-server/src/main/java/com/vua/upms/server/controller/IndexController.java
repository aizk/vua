package com.vua.upms.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by admin on 2017/7/5.
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {

        return "/manage/index";
    }

}
