package com.icode.cms.controller;


import com.icode.cms.common.constant.PathFinal;
import com.icode.cms.common.response.ResponseData;
import com.icode.cms.common.response.ResponseDataTable;
import com.icode.cms.repository.qo.CmsDictionaryQo;
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
     * Title: 跳转首页<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/1 11:17<br>
     */
    @RequestMapping("/pop/idx")
    public String index() {
        return PathFinal.PAGE_DICTIONARIES_IDX;
    }

    /**
     * Title: 获取数据字典结构树<br>
     * Description: <br>
     * Author: XiaChong<br>
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
     * Date: 2019/3/1 19:55<br>
     */
    @RequestMapping("/remove/dictionaryById")
    @ResponseBody
    public ResponseData removeDictionaryById(Integer id){
        return service.removeDictionaryById(id);
    }






}

