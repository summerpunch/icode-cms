package com.icode.cms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.icode.cms.common.constant.ResponseFinal;
import com.icode.cms.common.response.ResponseData;
import com.icode.cms.common.response.ResponseUtil;
import com.icode.cms.common.response.tree.DictionaryTreeNode;
import com.icode.cms.common.utils.LoadDataUtil;
import com.icode.cms.repository.dto.CmsDictionaryDto;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.repository.mapper.CmsDictionaryMapper;
import com.icode.cms.repository.qo.CmsDictionaryQo;
import com.icode.cms.service.ICmsDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional(rollbackFor = Exception.class)
    public ResponseData removeDictionaryById(Integer id) {
        CmsDictionary dictionary = selectById(id);
        if (dictionary != null) {
            if (deleteById(id)) {
                initDictionary();
                return ResponseUtil.success(null, ResponseFinal.DELETE_OK);
            } else {
                return ResponseUtil.success(null, ResponseFinal.DELETE_COME_TO_NOTHING);
            }
        } else {
            return ResponseUtil.success(null, ResponseFinal.DATA_DOES_NOT_EXIST);
        }
    }

    @Override
    public Page<CmsDictionaryDto> getDictionaryList(CmsDictionaryQo qo) {
        //搜索条件
        Map<String, Object> condition = new HashMap<>();
        EntityWrapper<CmsDictionary> entityWrapper = new EntityWrapper<>();

        if (qo.getId() != null) {
            entityWrapper.eq("parent_id", qo.getId());
        }

        if (qo.getStatus() != null) {
            entityWrapper.eq("status", qo.getStatus());
        }

        if (StringUtils.isNotBlank(qo.getItemNamecn())) {
            entityWrapper.like("item_namecn", qo.getItemNamecn());
        }

        List<CmsDictionaryDto> resourceDTO = new ArrayList<>();
        Page<CmsDictionary> page = new Page<>(qo.getPageIndex(), qo.getPageSize());

        page.setCondition(condition);
        page = selectPage(page, entityWrapper);

        for (CmsDictionary cd : page.getRecords()) {
            CmsDictionaryDto dto = new CmsDictionaryDto();
            BeanUtils.copyProperties(cd, dto);
            resourceDTO.add(dto);
        }

        Page<CmsDictionaryDto> pageDto = new Page<>();
        BeanUtils.copyProperties(page, pageDto);
        pageDto.setRecords(resourceDTO);
        return pageDto;
    }

    @Override
    public ResponseData getDictionaryTree() {
        List<CmsDictionary> listNodes = LoadDataUtil.getAllDictionary();
        if (listNodes.isEmpty()) {
            return ResponseUtil.success(getTree(initDictionary()));
        }
        return ResponseUtil.success(getTree(listNodes));
    }

    private List<DictionaryTreeNode> getTree(List<CmsDictionary> listNodes) {
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
        return rootList;
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
            List<CmsDictionary> lists = getNextNode(cmsDictionary);
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
    private List<CmsDictionary> getNextNode(CmsDictionary cmsDictionary) {
        return LoadDataUtil.getDicChild(cmsDictionary);
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
