package com.vua.upms.client.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * request参数工具类
 * Created by liunian on 6/8/17.
 */
public class RequestParameterUtil {
    public static String getParameterWithOutCode(HttpServletRequest request) {
        StringBuffer backUrl = request.getRequestURL();
        String params = "";
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (!entry.getKey().equals("upms_code") && !entry.getKey().equals("upms_username")) {
                if (params.equals("")) {
                    params = entry.getKey() + "=" + entry.getValue()[0];
                } else {
                    params += "&" + entry.getKey() + "=" + entry.getValue()[0];
                }
            }
        }
        if (StringUtils.isNotBlank(params)) {
            backUrl = backUrl.append("?").append(params);
        }
        return backUrl.toString();
    }

}
