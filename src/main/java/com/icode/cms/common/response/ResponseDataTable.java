package com.icode.cms.common.response;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

/**
 * Title: Table响应实体类<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/6 10:04<br>
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
