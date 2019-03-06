package com.icode.cms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.icode.cms.common.response.ResponseData;
import com.icode.cms.common.response.ResponseVerifyData;
import com.icode.cms.repository.dto.CmsDictionaryDto;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.repository.qo.CmsDictionaryQo;
import com.icode.cms.repository.vo.CmsDictionaryVO;

/**
 * Title: 数据字典 服务类<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/6 9:42<br>
 */
public interface ICmsDictionaryService extends IService<CmsDictionary> {

    /**
     * Title: 获取数据字典结构树<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 11:18<br>
     */
    ResponseData getDictionaryTree();


    /**
     * Title: 按条件查询数据字典列表<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 19:57<br>
     */
    Page<CmsDictionaryDto> getDictionaryList(CmsDictionaryQo qo);

    /**
     * Title: 根据id删除<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 19:57<br>
     */
    ResponseData removeDictionaryById(Integer id);

    /**
     * Title: 新增or编辑保存<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:14<br>
     */
    ResponseData saveOrUpdateDictionary(CmsDictionaryVO vo);

    /**
     * Title: 校验数据唯一性<br>
     * Description: itemKey<br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/5 14:31<br>
     */
    ResponseVerifyData uniquenessDictionary(Integer id, String itemKey, String fields);
}
