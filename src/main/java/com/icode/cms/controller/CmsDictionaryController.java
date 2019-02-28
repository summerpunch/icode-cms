package com.icode.cms.controller;


import com.icode.cms.common.response.tree.ResponseDictionaryTree;
import com.icode.cms.service.ICmsDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author xiachong
 * @since 2018-07-08
 */
@Controller
@RequestMapping("/cms/dictionary")
public class CmsDictionaryController {

    private static Logger LOGGER = LoggerFactory.getLogger(CmsDictionaryController.class);

    @Autowired
    private ICmsDictionaryService service;


    /**
     * Title: 获取数据字典结构树<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 18:40<br>
     */
    @RequestMapping(value = "ajax/dictionary/tree", produces = "application/json")
    @ResponseBody
    public ResponseDictionaryTree getDictionaryTree() {
        return service.getDictionaryTree();
    }


}

