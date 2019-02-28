package com.icode.cms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.icode.cms.common.response.tree.DictionaryTreeNode;
import com.icode.cms.common.response.tree.ResponseDictionaryTree;
import com.icode.cms.common.utils.LoadDataUtil;
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
    public ResponseDictionaryTree getDictionaryTree() {
        List<CmsDictionary> listNodes = LoadDataUtil.getAllDictionary();
        if (listNodes.isEmpty()) {
            return getTree(initDictionary());
        }
        return getTree(listNodes);
    }

    private ResponseDictionaryTree getTree(List<CmsDictionary> listNodes) {
        ResponseDictionaryTree ResponseDictionaryTree = new ResponseDictionaryTree();
        List<DictionaryTreeNode> rootList = new ArrayList<>();
        List<DictionaryTreeNode> nodes = new ArrayList<>();
        DictionaryTreeNode root = new DictionaryTreeNode();
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
                    nodes.add(facadeTree(cd));
                }
            });
            root.setNodes(nodes);
        }
        rootList.add(root);
        ResponseDictionaryTree.setData(rootList);
        return ResponseDictionaryTree;
    }


    /**
     * Title: 递归查询子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 10:53<br>
     */
    private DictionaryTreeNode facadeTree(CmsDictionary cmsDictionary) {
        DictionaryTreeNode node = new DictionaryTreeNode();
        List<DictionaryTreeNode> nodeList = new ArrayList<>();
        if (cmsDictionary != null) {
            List<CmsDictionary> lists = getNextNode(cmsDictionary.getId());
            if (!lists.isEmpty()) {
                for (CmsDictionary data : lists) {
                    nodeList.add(facadeTree(data));
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
    private List<CmsDictionary> getNextNode(Integer id) {
        return LoadDataUtil.getDicChildById(id);
    }

    /**
     * Title: 重新加载字典数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 16:20<br>
     */
    private List<CmsDictionary> initDictionary() {
        EntityWrapper<CmsDictionary> wrapper = new EntityWrapper<>();
        wrapper.orderBy("item_level", true);
        wrapper.orderBy("update_time", false);
        List<CmsDictionary> listNodes = selectList(wrapper);
        if (!listNodes.isEmpty()) {
            LoadDataUtil.buildLocalCache(listNodes);
            return listNodes;
        }
        return null;
    }
}
