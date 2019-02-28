package com.icode.cms.service;

import com.baomidou.mybatisplus.service.IService;
import com.icode.cms.common.response.tree.ResponseDictionaryTree;
import com.icode.cms.repository.entity.CmsDictionary;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author xiachong
 * @since 2018-07-08
 */
public interface ICmsDictionaryService extends IService<CmsDictionary> {

    /**
     * Title: 获取数据字典结构树<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2018/8/13 18:36<br>
     */
    ResponseDictionaryTree getDictionaryTree();


}
