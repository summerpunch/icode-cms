package com.icode.cms.common.utils;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.icode.cms.common.constant.DbFinal;
import com.icode.cms.repository.entity.CmsDictionary;
import com.icode.cms.service.ICmsDictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Title: 加载内存数据字典<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/2/28 17:25<br>
 */
public class LoadDataUtil {

    private static final Logger Logger = LoggerFactory.getLogger(LoadDataUtil.class);

    private static final Map<String, CmsDictionary> KEY_MAP = new HashMap<>();

    private static final Map<Integer, CmsDictionary> ID_MAP = new HashMap<>();

    private static final List<CmsDictionary> LIST_NODES = new ArrayList<>();

    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private static Supplier<Stream<CmsDictionary>> PARALLEL_STREAM;

    private static Lock r = rwl.readLock();

    private static Lock w = rwl.writeLock();


    /**
     * Title: 加载字典进内存<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 15:50<br>
     */
    public static void buildLocalCache(List<CmsDictionary> listDictionary) {
        w.lock();
        try {
            if (!listDictionary.isEmpty()) {
                clearMap();
                listDictionary.parallelStream().forEachOrdered(cd -> {
                            KEY_MAP.put(cd.getItemKey(), cd);
                            ID_MAP.put(cd.getId(), cd);
                        }
                );
                LIST_NODES.addAll(listDictionary);
                PARALLEL_STREAM = () -> listDictionary.parallelStream();
            }
        } finally {
            w.unlock();
        }
    }

    /**
     * Title: 清空数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 15:52<br>
     */
    private static void clearMap() {
        if (!KEY_MAP.isEmpty()) {
            KEY_MAP.clear();
        }
        if (!ID_MAP.isEmpty()) {
            ID_MAP.clear();
        }
        if (!LIST_NODES.isEmpty()) {
            LIST_NODES.clear();
        }
    }

    /**
     * Title: 获取所有字典数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 16:42<br>
     */
    public static List<CmsDictionary> getAllDictionary() {
        return LIST_NODES;
    }

    /**
     * Title: 根据key获取字典id<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 16:42<br>
     */
    public static Integer getDicIdByKey(String key) throws NullPointerException {
        r.lock();
        try {
            CmsDictionary dictionary = getDicDataByKey(key);
            if (dictionary == null) {
                return null;
            }
            return dictionary.getId();
        } finally {
            r.unlock();
        }
    }

    /**
     * Title: 根据key获取字典数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 16:42<br>
     */
    public static CmsDictionary getDicDataByKey(String key) throws NullPointerException {
        return getDicData(key, null);
    }

    /**
     * Title: 根据id获取字典数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 16:42<br>
     */
    public static CmsDictionary getDicDataById(Integer id) throws NullPointerException {
        return getDicData(null, id);
    }

    private static CmsDictionary getDicData(String key, Integer id) throws NullPointerException {
        CmsDictionary vo = null;
        if (StringUtils.isNotBlank(key)) {
            vo = KEY_MAP.get(key);
            if (null == vo) {
                Logger.error("--------------no dictionary-----------{}", key);
            }
        }
        if (null != id) {
            vo = ID_MAP.get(id);
            if (null == vo) {
                Logger.error("--------------no dictionary-----------{}", id);
            }
        }
        return vo;
    }

    /**
     * Title: 根据key获取子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 16:52<br>
     */
    public static List<CmsDictionary> getDicChildByKey(String key) throws NullPointerException {
        r.lock();
        try {
            CmsDictionary dicDataByKey = getDicDataByKey(key);
            if (dicDataByKey == null) {
                return null;
            }
            return getDicChild(dicDataByKey);
        } finally {
            r.unlock();
        }
    }

    /**
     * Title: 根据Id获取子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 16:52<br>
     */
    public static List<CmsDictionary> getDicChildById(Integer id) throws NullPointerException {
        r.lock();
        try {
            CmsDictionary cmsDictionary = getDicDataById(id);
            if (cmsDictionary == null) {
                return null;
            }
            return getDicChild(cmsDictionary);
        } finally {
            r.unlock();
        }
    }

    /**
     * Title: 查找子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/1 10:18<br>
     */
    public static List<CmsDictionary> getDicChild(CmsDictionary cmsDictionary) {
        return PARALLEL_STREAM.get().filter(cd -> cd.getParentId().equals(cmsDictionary.getId())).collect(Collectors.toList());
    }

    /**
     * Title: 初始化字典数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/2/28 16:20<br>
     */
    public static List<CmsDictionary> initDictionary(ICmsDictionaryService cmsDictionaryService) {
        EntityWrapper<CmsDictionary> wrapper = new EntityWrapper<>();
        wrapper.orderBy(DbFinal.DICT_COLUMN_ITEM_LEVEL);
        wrapper.orderBy(DbFinal.DICT_COLUMN_SORT);
        List<CmsDictionary> listNodes = cmsDictionaryService.selectList(wrapper);
        if (!listNodes.isEmpty()) {
            LoadDataUtil.buildLocalCache(listNodes);
            return listNodes;
        }
        return null;
    }

    /**
     * Title: 获取并行流<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Mail: summerpunch@163.com<br>
     * Date: 2019/3/8 13:19<br>
     */
    public static Supplier<Stream<CmsDictionary>> getParallelStream() {
        return PARALLEL_STREAM;
    }


    /********
     *
     * 数据字典存入request域
     *
     * *******/
    public static void initStatus(HttpServletRequest request) {
        request.setAttribute("DICT_KEY_DB_STATUS", LoadDataUtil.getDicChildByKey(DbFinal.DICT_KEY_DB_STATUS));
    }

}
