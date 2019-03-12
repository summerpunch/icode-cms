package com.icode.cms.common.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Title: 请求响应工具类<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2018/8/13 19:18<br>
 */
public class ResponseUtil {

    /**
     * Title: 输出为JSON<br>
     * Description:
     * <p>
     * 自定义格式化输出
     * WriteMapNullValue：是否输出值为null的字段,默认为false
     * WriteNullListAsEmpty：List字段如果为null,输出为[],而非null
     * WriteNullStringAsEmpty：字符类型字段如果为null,输出为"",而非null
     * DisableCircularReferenceDetect：消除对同一对象循环引用的问题，默认为false
     * <p>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/11 10:35<br>
     */
    private static String toJSON(Object obj) {
        return JSON.toJSONString(obj, ResponseUtil.nameFilterFormit(), SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * Title: 按规则转换<br>
     * Description: 带下划线<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/11 10:48<br>
     */
    public static NameFilter nameFilterFormit() {
        return (Object object, String name, Object value) -> {
            //按下划线拆分
            return Character.isLowerCase(name.charAt(0)) ? StringUtils.camelToUnderline(name) : name;
        };
    }

    public static String toSuccessJSON(Object obj) {
        return toJSON(success(obj));
    }

    public static String toBusinessErrorJSON(String obj) {
        return toJSON(businessError(obj));
    }


    /**
     * Title: 请求成功，并定制返回消息<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
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
     * Mail: summerpunch@163.com<br>
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
     * Mail: summerpunch@163.com<br>
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
     * Mail: summerpunch@163.com<br>
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
     * Mail: summerpunch@163.com<br>
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
     * Mail: summerpunch@163.com<br>
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
