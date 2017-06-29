package com.vua.common.base;

/**
 * Created by admin on 2017/6/29.
 */
public class BaseResult {

    //success is 1
    public int code;
    //message
    public String message;
    //data
    public Object data;

    public BaseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
