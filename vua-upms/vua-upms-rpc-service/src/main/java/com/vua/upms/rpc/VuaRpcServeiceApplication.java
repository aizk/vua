package com.vua.upms.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by admin on 2017/6/27.
 */
public class VuaRpcServeiceApplication {

    private static Logger _log = LoggerFactory.getLogger(VuaRpcServeiceApplication.class);

    public static void main(String[] args) {
        _log.info("Rpc Service Start ...");
        new ClassPathXmlApplicationContext("classpath:META-INF/spring/*.xml");
        _log.info("Rpc Service Start Finish ...");
    }
}
