package com.icode.cms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.icode.cms.common.constant.DbFinal;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Title: 数据字典 服务实现类<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/6 9:53<br>
 */
@Service
public class CmsDictionaryServiceImpl extends ServiceImpl<CmsDictionaryMapper, CmsDictionary> implements ICmsDictionaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsDictionaryServiceImpl.class);

    @Autowired
    private ICmsDictionaryService service;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData removeDictionaryById(Integer[] ids) {
        try {
            boolean flag = deleteBatchIds(Arrays.asList(ids));
            if (flag) {
                LoadDataUtil.initDictionary(service);
                return ResponseUtil.success(null, ResponseFinal.DELETE_OK);
            } else {
                return ResponseUtil.success(null, ResponseFinal.DELETE_COME_TO_NOTHING);
            }
        } catch (Exception e) {
            LOGGER.error("removeDictionaryById,err--{}", e);
            return ResponseUtil.businessError(ResponseFinal.ERROR);
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
        return ResponseUtil.success(null, ResponseFinal.SAVE_COME_TO_NOTHING);
    }

    @Override
    public ResponseVerifyData uniquenessDictionary(Integer id, String itemKey) {
        ResponseVerifyData responseVerifyData = new ResponseVerifyData();
        EntityWrapper<CmsDictionary> wrapper = new EntityWrapper();
        wrapper.eq(DbFinal.DICT_COLUMN_ITEM_KEY, itemKey.trim());
        if (id != null) {
            wrapper.notIn(DbFinal.DICT_COLUMN_ID, id);
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
            entityWrapper.eq(DbFinal.DICT_COLUMN_PARENT_ID, qo.getId());
        }

        if (qo.getStatus() != null) {
            entityWrapper.eq(DbFinal.DICT_COLUMN_STATUS, qo.getStatus());
        }

        if (StringUtils.isNotBlank(qo.getItemNamecn())) {
            entityWrapper.like(DbFinal.DICT_COLUMN_ITEM_NAMECN, qo.getItemNamecn());
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
            List<CmsDictionary> list = LoadDataUtil.initDictionary(service);
            return ResponseUtil.success(getTree(list));
        }
        return ResponseUtil.success(getTree(listNodes));
    }

    private List<DictionaryTreeNode> getTree(List<CmsDictionary> listNodes) {
        List<DictionaryTreeNode> rootList = new ArrayList<>();
        DictionaryTreeNode root = new DictionaryTreeNode();
        if (!listNodes.isEmpty()) {
            listNodes.parallelStream().forEachOrdered(n -> root.setTreeNode(n));
        }
        rootList.add(root);
        return rootList;
    }

}
