package com.icode.cms.common.response.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeDictionaryNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String text;
    private Integer parentId;
    private List<TreeDictionaryNode> nodes;


}
