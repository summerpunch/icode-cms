package com.icode.cms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.icode.cms.common.constant.ResponseFinal;
import com.icode.cms.common.response.ResponseData;
import com.icode.cms.common.response.ResponseUtil;
import com.icode.cms.common.response.ResponseVerifyData;
import com.icode.cms.common.response.tree.DictionaryTreeNode;
import com.icode.cms.common.utils.LoadDataUtil;
import com.icode.cms.repository.dto.CmsDictionaryDto;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.repository.mapper.CmsDictionaryMapper;
import com.icode.cms.repository.qo.CmsDictionaryQo;
import com.icode.cms.repository.vo.CmsDictionaryVO;
import com.icode.cms.service.ICmsDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsDictionaryServiceImpl.class);

    private List<CmsDictionary> removeDictionaryList = new ArrayList<>();

    @Autowired
    private ICmsDictionaryService service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData removeDictionaryById(Integer id) {
        CmsDictionary dictionary = selectById(id);
        if (dictionary != null) {
            if (deleteById(id)) {
                LoadDataUtil.initDictionary(service);
                return ResponseUtil.success(null, ResponseFinal.DELETE_OK);
            } else {
                return ResponseUtil.success(null, ResponseFinal.DELETE_COME_TO_NOTHING);
            }
        } else {
            return ResponseUtil.success(null, ResponseFinal.DATA_DOES_NOT_EXIST);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData saveOrUpdateDictionary(CmsDictionaryVO vo) {
        try {
            CmsDictionary casSysDictionary = new CmsDictionary();
            BeanUtils.copyProperties(vo, casSysDictionary);
            if (casSysDictionary.getId() != null) {
                casSysDictionary.setAdminCreate(1);
                casSysDictionary.setUpdateTime(new Date());
            } else {
                CmsDictionary parent = selectById(casSysDictionary.getParentId());
                casSysDictionary.setAdminUpdate(1);
                casSysDictionary.setAdminCreate(1);
                casSysDictionary.setCreateTime(new Date());
                casSysDictionary.setItemLevel(parent.getItemLevel() + 1);
                casSysDictionary.setUpdateTime(casSysDictionary.getCreateTime());
            }
            if (insertOrUpdate(casSysDictionary)) {
                LoadDataUtil.initDictionary(service);
                return ResponseUtil.success(casSysDictionary, ResponseFinal.SAVE_OK);
            }
        } catch (Exception e) {
            LOGGER.error("saveOrUpdateDictionary,err--{}", e);
            return ResponseUtil.businessError(ResponseFinal.SAVE_COME_TO_NOTHING);
        }
        return null;
    }

    @Override
    public ResponseVerifyData uniquenessDictionary(Integer id, String itemKey, String fields) {
        ResponseVerifyData responseVerifyData = new ResponseVerifyData();
        EntityWrapper<CmsDictionary> wrapper = new EntityWrapper();
        wrapper.eq("item_key", itemKey.trim());
        if (id != null) {
            wrapper.notIn("id", id);
        }
        int i = selectCount(wrapper);
        if (i > 0) {
            responseVerifyData.setValid(false);
        } else {
            responseVerifyData.setValid(true);
        }
        return responseVerifyData;
    }

    @Override
    public Page<CmsDictionaryDto> getDictionaryList(CmsDictionaryQo qo) {
        //搜索条件
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
            return ResponseUtil.success(getTree(LoadDataUtil.initDictionary(service)));
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
            List<CmsDictionary> lists = LoadDataUtil.getDicChild(cmsDictionary);
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
}
