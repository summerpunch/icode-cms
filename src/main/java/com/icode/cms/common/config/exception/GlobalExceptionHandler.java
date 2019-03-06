package com.icode.cms.common.config.exception;

import com.icode.cms.common.response.ResponseCodeEnum;
import com.icode.cms.common.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * Title: 全局异常捕获<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/2/27 17:59<br>
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Title: 空指针异常<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/27 17:44<br>
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseData nullPointerExceptionHandler(NullPointerException e) {
        LOGGER.error("nullPointerExceptionHandler", e);
        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseCodeEnum.NULL_EXCEOTION.getCode());
        responseData.setMsg(e.getMessage());
        responseData.setSuccess(false);
        return responseData;
    }

    /**
     * Title: 方法参数校验异常<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/27 17:44<br>
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseData methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        LOGGER.error("methodArgumentTypeMismatchException", e);
        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseCodeEnum.TYPE_MISMATCH_EXCEPTION.getCode());
        responseData.setMsg(e.getMessage());
        responseData.setSuccess(false);
        return responseData;
    }

    /**
     * Title: 其他异常<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/27 17:45<br>
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseData allExceptionHandler(Exception e) {
        LOGGER.error("allExceptionHandler", e);
        e.printStackTrace();
        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseCodeEnum.GLOBAL_EXCEPTION.getCode());
        responseData.setMsg("GlobalExceptionHandler.Exception");
        responseData.setSuccess(false);
        return responseData;
    }
}
