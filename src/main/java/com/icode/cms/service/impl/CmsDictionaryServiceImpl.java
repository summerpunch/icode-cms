package com.icode.cms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.icode.cms.common.response.tree.TreeDictionaryNode;
import com.icode.cms.common.response.tree.TreeDictionaryResponse;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.repository.mapper.CmsDictionaryMapper;
import com.icode.cms.service.ICmsDictionaryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author xiachong
 * @since 2018-07-08
 */
@Service
public class CmsDictionaryServiceImpl extends ServiceImpl<CmsDictionaryMapper, CmsDictionary> implements ICmsDictionaryService {

    private List<CmsDictionary> dictionaryList = null;
    private List<CmsDictionary> removeDictionaryList = new ArrayList<>();

    @Override
    public TreeDictionaryResponse getDictionaryTree() {
        TreeDictionaryResponse treeDictionaryResponse = new TreeDictionaryResponse();
        EntityWrapper<CmsDictionary> wrapper = new EntityWrapper<>();
       // TODO wrapper.eq(DatabaseFinal.CAS_SYS_DICTIONARY_V_STATUS,34);
        dictionaryList = selectList(wrapper);

        List<TreeDictionaryNode> nodes = new ArrayList<>();
        dictionaryList.stream().filter(cd -> !removeDictionaryList.contains(cd)).forEach(cd -> {
            nodes.add(facadeTree(cd));
        });

        treeDictionaryResponse.setData(nodes);
        return treeDictionaryResponse;
    }

    private TreeDictionaryNode facadeTree(CmsDictionary dictionary) {
        TreeDictionaryNode node = new TreeDictionaryNode();
        List<TreeDictionaryNode> nodeList = new ArrayList<>();

        dictionaryList.stream().filter(cd -> dictionary.getId().equals(cd.getParentId())).forEach(cd -> {
            nodeList.add(facadeTree(cd));
            node.setNodes(nodeList);
            removeDictionaryList.add(cd);
        });

        node.setId(dictionary.getId());
        node.setText(dictionary.getItemNamecn());
        return node;
    }




}
