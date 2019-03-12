package com.icode.cms.common.constant;

/**
 * Title: 数据库字段查询相关常量<br>
 * Description: <br>
 * Author: XiaChong<br>
 * Mail: summerpunch@163.com<br>
 * Date: 2019/3/6 9:47<br>
 */
public class DBFinal {


    //字典key
    public static final String DICT_KEY_ROOT = "root";
    public static final String DICT_KEY_ENUM = "enum";
    public static final String DICT_KEY_DB = "db";

    /**
     * 状态
     */
    public static final String DICT_KEY_DB_STATUS = "db.status";
    /**
     * 启用
     */
    public static final String DICT_KEY_DB_STATUS_OFF = "db.status.off";
    /**
     * 禁用
     */
    public static final String DICT_KEY_DB_STATUS_ON = "db.status.on";

    /**
     * 默认缓存时间
     */
    public static final String DICT_KEY_ENUM_CACHE_COMMON_DEFAULT = "enum.cache.common.default";
    /**
     * 缓存超时随机数开始
     */
    public static final String DICT_KEY_ENUM_CACHE_COMMON_RANGE_BEGIN = "enum.cache.common.range.begin";
    /**
     * 缓存超时随机数结束
     */
    public static final String DICT_KEY_ENUM_CACHE_COMMON_RANGE_END = "enum.cache.common.range.end";
    /**
     * 缓存过期
     */
    public static final String DICT_KEY_ENUM_CACHE_COMMON_FROMCACHEEXPIRATION = "enum.cache.common.fromcacheexpiration";
    /**
     * 新请求停顿时间
     */
    public static final String DICT_KEY_ENUM_CACHE_COMMON_NEWREQUESTSLEEP = "enum.cache.common.newrequestsleep";
    /**
     * 新请求锁超时时间
     */
    public static final String DICT_KEY_ENUM_CACHE_COMMON_NEWREQUESTLOCKTIMEOUT = "enum.cache.common.newrequestlocktimeout";
    /**
     * 异常超时时间
     */
    public static final String DICT_KEY_ENUM_CACHE_COMMON_EXCEPTIONTIMEOUT = "enum.cache.common.exceptiontimeout";



    //dict字段
    public static final String DICT_COLUMN_ID = "id";
    public static final String DICT_COLUMN_ITEM_LEVEL = "item_level";
    public static final String DICT_COLUMN_ITEM_KEY = "item_key";
    public static final String DICT_COLUMN_PARENT_ID = "parent_id";
    public static final String DICT_COLUMN_STATUS = "status";
    public static final String DICT_COLUMN_ITEM_NAMECN = "item_namecn";
    public static final String DICT_COLUMN_UPDATE_TIME = "update_time";
    public static final String DICT_COLUMN_SORT = "sort";



}
