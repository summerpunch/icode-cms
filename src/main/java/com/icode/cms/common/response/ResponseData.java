package com.icode.cms.common.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ResponseData implements Serializable {

    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("ret", success);
        result.put("msg", msg);
        result.put("code", code);
        return result;
    }

    public ResponseData() {
    }

    public ResponseData(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "success=" + success +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
