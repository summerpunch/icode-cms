package com.icode.cms.controller;


import com.icode.cms.common.constant.DbFinal;
import com.icode.cms.common.constant.PathFinal;
import com.icode.cms.common.response.ResponseData;
import com.icode.cms.common.response.ResponseDataTable;
import com.icode.cms.common.response.ResponseUtil;
import com.icode.cms.common.response.ResponseVerifyData;
import com.icode.cms.common.utils.LoadDataUtil;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.repository.qo.CmsDictionaryQo;
import com.icode.cms.repository.vo.CmsDictionaryVO;
import com.icode.cms.service.ICmsDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Title: 数据字典 前端控制器<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/6 9:41<br>
 */
@Controller
@RequestMapping("/cms/dictionary")
public class CmsDictionaryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsDictionaryController.class);

    @Autowired
    private ICmsDictionaryService service;

    /**
     * Title: 跳转首页<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 11:17<br>
     */
    @RequestMapping("/pop/idx")
    public String index(HttpServletRequest request) {
        request.setAttribute("DICT_KEY_DB_STATUS", LoadDataUtil.getDicChildByKey(DbFinal.DICT_KEY_DB_STATUS));
        return PathFinal.PAGE_DICTIONARIES_IDX;
    }

    /**
     * Title: 获取数据字典结构树<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 11:17<br>
     */
    @RequestMapping(value = "ajax/dictionary/tree", produces = "application/json")
    @ResponseBody
    public ResponseData getDictionaryTree() {
        return service.getDictionaryTree();
    }


    /**
     * Title: 按条件查询数据字典列表<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 11:17<br>
     */
    @RequestMapping(value = "/get/dictionaryList")
    @ResponseBody
    public ResponseDataTable getDictionaryList(CmsDictionaryQo qo) {
        return new ResponseDataTable(true, service.getDictionaryList(qo));
    }

    /**
     * Title: 根据id删除<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 19:55<br>
     */
    @RequestMapping("/remove/dictionaryById")
    @ResponseBody
    public ResponseData removeDictionaryById(@RequestParam(required = false, value = "ids[]") Integer[] ids) {
        return service.removeDictionaryById(ids);
    }


    /**
     * Title: 新增弹窗<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:01<br>
     */
    @RequestMapping("/pop/add_dictionary")
    public String popAddDictionary(HttpServletRequest request, Integer parentId) {
        CmsDictionary cmsDictionary = new CmsDictionary();
        cmsDictionary.setParentId(parentId);
        request.setAttribute("cmsDictionary", cmsDictionary);
        LoadDataUtil.initStatus(request);
        return PathFinal.PATH_PAGE_POP_ADD_V_EDIT_DICTIONARY;
    }

    /**
     * Title: 编辑弹窗<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:03<br>
     */
    @RequestMapping("/pop/edit_dictionary")
    public String popEditDictionary(HttpServletRequest request, Integer id) {
        CmsDictionary cmsDictionary = new CmsDictionary();
        if (id != null) {
            cmsDictionary = service.selectById(id);
        }
        request.setAttribute("cmsDictionary", cmsDictionary);
        LoadDataUtil.initStatus(request);
        return PathFinal.PATH_PAGE_POP_ADD_V_EDIT_DICTIONARY;
    }

    /**
     * Title: 信息弹窗<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:03<br>
     */
    @RequestMapping("/pop/info_dictionary")
    public String popInfoDictionary(HttpServletRequest request, Integer id) {
        CmsDictionary cmsDictionary = LoadDataUtil.getDicDataById(id);
        if (null == cmsDictionary) {
            cmsDictionary = new CmsDictionary();
        }
        request.setAttribute("cmsDictionary", cmsDictionary);
        LoadDataUtil.initStatus(request);
        return PathFinal.PATH_PAGE_POP_INFO_DICTIONARY;
    }


    /**
     * Title: 新增or编辑保存<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:07<br>
     */
    @RequestMapping("/do/save_v_update_dictionary")
    @ResponseBody
    public ResponseData doSaveOrUpdateDictionary(@Valid CmsDictionaryVO vo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseUtil.paramInvalidError(bindingResult.getFieldErrors());
        } else {
            return service.saveOrUpdateDictionary(vo);
        }
    }

    /**
     * Title: 校验数据唯一性<br>
     * Description: itemKey<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:31<br>
     */
    @ResponseBody
    @RequestMapping("/get/uniqueness_dictionary")
    public ResponseVerifyData uniquenessDictionary(Integer id, String itemKey) {
        return service.uniquenessDictionary(id, itemKey);
    }
}

