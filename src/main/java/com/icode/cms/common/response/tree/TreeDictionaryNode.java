package com.icode.cms.common.response.tree;

import java.io.Serializable;
import java.util.List;

public class TreeDictionaryNode implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String text;
    private List<TreeDictionaryNode> nodes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeDictionaryNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeDictionaryNode> nodes) {
        this.nodes = nodes;
    }
}
