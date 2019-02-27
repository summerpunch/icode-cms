package com.icode.cms.common.response;

import java.io.Serializable;

public class ResponseVerifyData implements Serializable {

    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "ResponseVerifyData{" +
                "valid=" + valid +
                '}';
    }
}
