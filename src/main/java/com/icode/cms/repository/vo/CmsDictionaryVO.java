package com.icode.cms.repository.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CmsDictionaryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull(message = "父节点不能为空")
    private Integer parentId;

    @NotBlank(message = "字典Key不能为空")
    private String itemKey;

    @NotBlank(message = "字典Value不能为空")
    private String itemValue;

    @NotBlank(message = "字典名称不能为空")
    private String itemNamecn;

    @NotNull(message = "顺序不能为空")
    private Integer sort;

    @NotNull(message = "字典状态不能为空")
    private Integer status;

    @NotNull(message = "字典描述不能为空")
    private String description;

}
