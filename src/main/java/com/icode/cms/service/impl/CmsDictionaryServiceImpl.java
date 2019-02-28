package com.icode.cms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.icode.cms.common.response.tree.TreeDictionaryNode;
import com.icode.cms.common.response.tree.TreeDictionaryResponse;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.repository.mapper.CmsDictionaryMapper;
import com.icode.cms.service.ICmsDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger LOGGER = LoggerFactory.getLogger(CmsDictionaryServiceImpl.class);

    private List<CmsDictionary> removeDictionaryList = new ArrayList<>();

    @Override
    public TreeDictionaryResponse getDictionaryTree() {
        TreeDictionaryResponse treeDictionaryResponse = new TreeDictionaryResponse();
        EntityWrapper<CmsDictionary> wrapper = new EntityWrapper<>();
        wrapper.orderBy("item_level", true);
        wrapper.orderBy("update_time", false);
        List<CmsDictionary> listNodes = selectList(wrapper);
        List<TreeDictionaryNode> nodes = new ArrayList<>();
        List<TreeDictionaryNode> rootList = new ArrayList<>();
        TreeDictionaryNode root = new TreeDictionaryNode();
        if (!listNodes.isEmpty()) {
            if (!removeDictionaryList.isEmpty()) {
                removeDictionaryList.clear();
            }
            listNodes.stream().filter(cd -> !removeDictionaryList.contains(cd)).forEach(cd -> {
                if (cd.getParentId() == 0) {
                    root.setId(cd.getId());
                    root.setParentId(cd.getParentId());
                    root.setText(cd.getItemNamecn());
                } else {
                    nodes.add(facadeTree(listNodes, cd));
                }
            });
            root.setNodes(nodes);
        }
        rootList.add(root);
        treeDictionaryResponse.setData(rootList);
        return treeDictionaryResponse;
    }

    /**
     * Title: 递归查询子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 10:53<br>
     */
    private TreeDictionaryNode facadeTree(List<CmsDictionary> listNodes, CmsDictionary cmsDictionary) {
        TreeDictionaryNode node = new TreeDictionaryNode();
        List<TreeDictionaryNode> nodeList = new ArrayList<>();
        if (!listNodes.isEmpty() && cmsDictionary != null) {
            List<CmsDictionary> lists = getNextNode(listNodes, cmsDictionary.getId());
            if (!lists.isEmpty()) {
                for (CmsDictionary data : lists) {
                    nodeList.add(facadeTree(listNodes, data));
                    node.setNodes(nodeList);
                    removeDictionaryList.add(data);
                }
            }
            node.setId(cmsDictionary.getId());
            node.setParentId(cmsDictionary.getParentId());
            node.setText(cmsDictionary.getItemNamecn());
        }
        return node;
    }

    /**
     * Title: 获取子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 10:53<br>
     */
    private List<CmsDictionary> getNextNode(List<CmsDictionary> listNodes, Integer id) {
        List<CmsDictionary> lists = new ArrayList<>();
        listNodes.parallelStream().filter(cd -> !removeDictionaryList.contains(cd) && cd.getParentId().equals(id)).forEachOrdered(datas -> lists.add(datas));
        return lists;
    }
}
