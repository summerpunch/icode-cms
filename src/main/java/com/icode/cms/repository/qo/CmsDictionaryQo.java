package com.icode.cms.repository.qo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CmsDictionaryQo extends BaseQo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String itemNamecn;

    private String itemKey;

    private Integer status;

}