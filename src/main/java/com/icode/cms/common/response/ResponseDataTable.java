package com.icode.cms.common.response;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

/**
 * Title: Table响应实体类<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Date: 2018/8/13 19:12<br>
 */
@Data
public class ResponseDataTable {

    private boolean success;
    private int total;
    private Object data;


    public ResponseDataTable(boolean success, Page page) {
        this.success = success;
        this.total = page.getTotal();
        this.data = page.getRecords();
    }

}
