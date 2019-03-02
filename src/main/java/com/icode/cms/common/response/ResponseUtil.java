package com.icode.cms.common.response;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Title: 请求响应工具类<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 19:18<br>
 */
public class ResponseUtil {

    /**
     * Title: 请求成功，并定制返回消息<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 18:43<br>
     */
    public static ResponseData success(Object data, String msg) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseData.setMsg(msg);
        responseData.setSuccess(true);
        responseData.setData(data);
        responseData.setStatus("success");
        return responseData;
    }

    /**
     * Title: 请求成功,默认返回消息为 ok<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 18:43<br>
     */
    public static ResponseData success(Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseData.setMsg(ResponseCodeEnum.SUCCESS.getDescribe());
        responseData.setSuccess(true);
        responseData.setData(data);
        responseData.setStatus("success");
        return responseData;
    }

    /**
     * Title: 参数校验异常<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 18:43<br>
     */
    public static ResponseData paramInvalidError(String message) {
        ResponseData resultData = new ResponseData();
        resultData.setSuccess(false);
        resultData.setCode(ResponseCodeEnum.PARAM_INVALID_ERROR.getCode());
        resultData.setMsg(message);
        resultData.setStatus("warning");
        return resultData;
    }

    /**
     * Title: 参数校验异常<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 18:43<br>
     */
    public static ResponseData paramInvalidError(List<FieldError> errors) {
        ResponseData resultData = new ResponseData();
        StringBuilder stringBuilder = new StringBuilder();
        if (errors != null) {
            for (FieldError error : errors) {
                stringBuilder.append(error.getField());
                stringBuilder.append(":");
                stringBuilder.append(error.getDefaultMessage());
                stringBuilder.append(" ");
            }
            resultData.setMsg(stringBuilder.toString());
        } else {
            resultData.setMsg(ResponseCodeEnum.PARAM_INVALID_ERROR.getDescribe());
        }
        resultData.setSuccess(false);
        resultData.setStatus("warning");
        resultData.setCode(ResponseCodeEnum.PARAM_INVALID_ERROR.getCode());
        return resultData;
    }

    /**
     * Title: 业务异常<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 18:43<br>
     */
    public static ResponseData businessError(String message) {
        ResponseData resultData = new ResponseData();
        resultData.setSuccess(false);
        resultData.setCode(ResponseCodeEnum.SERVICE_BUSINESS_ERROR.getCode());
        resultData.setMsg(message);
        resultData.setStatus("error");
        return resultData;
    }

    /**
     * Title: 唯一校验<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 18:43<br>
     */
    public static ResponseData uniquenessError(String message) {
        ResponseData resultData = new ResponseData();
        resultData.setSuccess(false);
        resultData.setCode(ResponseCodeEnum.UNIQUENESS_EXCEPTION.getCode());
        resultData.setMsg(message);
        resultData.setStatus("info");
        return resultData;
    }
}
