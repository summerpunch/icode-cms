package com.icode.cms.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseData implements Serializable {

    private boolean success;
    private int code;
    private String msg;
    private String status;
    private Object data;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ret", success);
        result.put("msg", msg);
        result.put("code", code);
        result.put("status", status);
        return result;
    }

    public ResponseData() {
    }

    public ResponseData(boolean success, int code, String msg, String status) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.status = status;
    }

}
