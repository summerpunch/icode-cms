package com.icode.cms.common.utils;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Title: 事物控制<br>
 * Description: <br>
 * Author: XiaChong<br>
 */
public class TransactionUtil {

    /**
     * Title: 手动设置事物回滚<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/2 16:09<br>
     */
    public static void rollbackOnly(){
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
