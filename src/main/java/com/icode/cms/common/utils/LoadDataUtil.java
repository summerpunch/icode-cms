package com.icode.cms.common.utils;


import com.icode.cms.repository.entity.CmsDictionary;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 加载内存数据字典
 *
 * @author pengren
 */
public class LoadDataUtil {

    private static Logger log = LoggerFactory.getLogger(LoadDataUtil.class);

    private static final Map<String, CmsDictionary> KEY_MAP = new HashMap<>();

    private static final Map<Integer, CmsDictionary> ID_MAP = new HashMap<>();

    private static final List<CmsDictionary> LIST_NODES = new ArrayList<>();

    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private static Lock r = rwl.readLock();

    private static Lock w = rwl.writeLock();


    /**
     * Title: 加载字典进内存<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 15:50<br>
     */
    public static void buildLocalCache(List<CmsDictionary> listDictionary) {
        w.lock();
        try {
            clearMap();
            for (CmsDictionary cmsDictionary : listDictionary) {
                KEY_MAP.put(cmsDictionary.getItemKey(), cmsDictionary);
                ID_MAP.put(cmsDictionary.getId(), cmsDictionary);
            }
            LIST_NODES.addAll(listDictionary);
        } finally {
            w.unlock();
        }
    }

    /**
     * Title: 清空数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 15:52<br>
     */
    private static void clearMap() {
        if (!KEY_MAP.isEmpty()) {
            KEY_MAP.clear();
        }
        if (!ID_MAP.isEmpty()) {
            ID_MAP.clear();
        }
    }

    /**
     * Title: 获取所有字典数据<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 16:42<br>
     */
    public static List<CmsDictionary> getAllDictionary() {
        return LIST_NODES;
    }

    /**
     * Title: 根据key获取字典id<br>
     * Description: <br>
     * Author: XiaChong<br>
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
     * Date: 2019/2/28 16:42<br>
     */
    public static CmsDictionary getDicDataByKey(String key) throws NullPointerException {
        return getDicData(key, null);
    }

    /**
     * Title: 根据id获取字典数据<br>
     * Description: <br>
     * Author: XiaChong<br>
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
                log.error("--------------no dictionary-----------{}", key);
            }
        }
        if (null != id) {
            vo = ID_MAP.get(id);
            if (null == vo) {
                log.error("--------------no dictionary-----------{}", id);
            }
        }
        return vo;
    }

    /**
     * Title: 根据key获取所有子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 16:52<br>
     * Param: <br>
     * Return:
     */
    public static List<CmsDictionary> getDicChildByKey(String key) throws NullPointerException {
        r.lock();
        try {
            CmsDictionary dictionary = getDicDataByKey(key);
            if (dictionary != null) {
                return getDicChild(dictionary.getId());
            }
            return null;
        } finally {
            r.unlock();
        }
    }

    /**
     * Title: 根据Id获取所有子节点<br>
     * Description: <br>
     * Author: XiaChong<br>
     * Date: 2019/2/28 16:52<br>
     * Param: <br>
     * Return:
     */
    public static List<CmsDictionary> getDicChildById(Integer id) throws NullPointerException {
        r.lock();
        try {
            return getDicChild(id);
        } finally {
            r.unlock();
        }
    }

    private static List<CmsDictionary> getDicChild(Integer id) {
        List<CmsDictionary> lists = new ArrayList<>();
        LIST_NODES.parallelStream().filter(cd -> cd.getParentId().equals(id)).forEachOrdered(cd -> lists.add(cd));
        return lists;
    }


}
