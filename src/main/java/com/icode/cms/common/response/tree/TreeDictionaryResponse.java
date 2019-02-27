package com.icode.cms.common.response.tree;

import java.io.Serializable;
import java.util.List;

/**
 * Tree Dictionary Response
 */
public class TreeDictionaryResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<TreeDictionaryNode> data;

    public TreeDictionaryResponse() {

    }

    public TreeDictionaryResponse(List<TreeDictionaryNode> data) {
        this.data = data;
    }

    public List<TreeDictionaryNode> getData() {
        return data;
    }

    public void setData(List<TreeDictionaryNode> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TreeDictionaryResponse{" +
                "data=" + data +
                '}';
    }
}
