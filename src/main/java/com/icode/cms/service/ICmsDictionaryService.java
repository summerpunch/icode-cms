package com.icode.cms.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.icode.cms.common.response.ResponseData;
import com.icode.cms.repository.dto.CmsDictionaryDto;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.repository.qo.CmsDictionaryQo;

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
     * Date: 2019/3/1 11:18<br>
     */
    ResponseData getDictionaryTree();


    /**
     * Title: 按条件查询数据字典列表<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/1 19:57<br>
     */
    Page<CmsDictionaryDto> getDictionaryList(CmsDictionaryQo qo);

    /**
     * Title: 根据id删除<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/3/1 19:57<br>
     */
    ResponseData removeDictionaryById(Integer id);
}
