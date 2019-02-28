package com.icode.cms.common.response.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseDictionaryTree implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<DictionaryTreeNode> data;


}
