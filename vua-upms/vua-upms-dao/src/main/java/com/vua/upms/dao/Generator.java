package com.vua.upms.dao;

import com.vua.common.util.MybatisGeneratorUtil;
import com.vua.common.util.PropertiesFileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liunian on 6/17/17.
 */
public class Generator {
    // 根据命名规范，只修改此常量值即可
    private static String MODULE = "vua-upms";
    private static String DATABASE = "vua";
    private static String TABLE_PREFIX = "upms_";
    private static String PACKAGE_NAME = "com.vua.upms";
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");
    // 需要insert后返回主键的表配置，key:表名,value:主键名
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<>();
    static {
        LAST_INSERT_ID_TABLES.put("upms_user", "user_id");
    }

    public static void main(String[] args) throws Exception {
        MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, TABLE_PREFIX, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }
}
