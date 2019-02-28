package com.icode.cms.common.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.icode.cms.common.utils.LoadDataUtil;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.service.ICmsDictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryClientHandle {

    private final static Logger LOGGER = LoggerFactory.getLogger(DictionaryClientHandle.class);


    public void loadLocalData(ConfigurableApplicationContext applicationContext, String profile) {
        String data = loadDictionaryFromDB(applicationContext);
        System.out.println(data);
    }


    /**
     * Title: 加载字典进内存<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 15:54<br>
     */
    public String loadDictionaryFromDB(ConfigurableApplicationContext applicationContext) {
        ICmsDictionaryService cmsDictionaryService = applicationContext.getBean(ICmsDictionaryService.class);
        EntityWrapper<CmsDictionary> wrapper = new EntityWrapper<>();
        wrapper.orderBy("item_level", true);
        wrapper.orderBy("update_time", false);

        List<CmsDictionary> list = cmsDictionaryService.selectList(wrapper);
        if(!list.isEmpty()){
            LoadDataUtil.buildLocalCache(list);
        }
        return JSON.toJSONString(list);
    }






}
