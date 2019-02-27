package com.icode.cms.repository.entity;


import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建人员
     */
    private Integer adminCreate;
    /**
     * 更新人员
     */
    private Integer adminUpdate;
}
