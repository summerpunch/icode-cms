/*
Navicat MySQL Data Transfer

Source Server         : localhostSQL
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : icode-cms

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2019-03-10 13:20:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cms_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `cms_dictionary`;
CREATE TABLE `cms_dictionary` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `parent_id` int(255) NOT NULL COMMENT '父节点 id',
  `item_key` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '字典key：枚举形式的唯一key',
  `item_level` int(255) NOT NULL COMMENT '层次',
  `item_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字典值：枚举key对应的value，此值并不能作为外键用，一般用于配置项对应的值，而表关联则用字典主键id，',
  `item_namecn` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '字典中文名称',
  `sort` int(11) DEFAULT NULL COMMENT '顺序',
  `status` int(255) DEFAULT NULL COMMENT '状态：启用、禁用',
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注：具体用途细节等',
  `create_time` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `admin_create` int(255) DEFAULT NULL COMMENT '创建人员',
  `admin_update` int(255) DEFAULT NULL COMMENT '更新人员',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_item_key` (`item_key`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE,
  KEY `idx_fr_state` (`status`) USING BTREE,
  KEY `idx_sort` (`sort`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=589 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='数据字典';

-- ----------------------------
-- Records of cms_dictionary
-- ----------------------------
INSERT INTO `cms_dictionary` VALUES ('1', '0', 'root', '0', '', '根节点', null, '5', null, '2018-07-11 19:23:41.000000', '2018-07-11 19:23:44.000000', '1', '1');
INSERT INTO `cms_dictionary` VALUES ('2', '1', 'enum', '1', '', '枚举相关', '1', '5', null, '2018-07-02 19:26:46.000000', '2018-08-14 17:52:57.441000', '1', '1');
INSERT INTO `cms_dictionary` VALUES ('3', '1', 'db', '1', '', '数据库相关', '2', '5', null, '2019-03-30 21:14:19.000000', '2019-03-14 21:14:16.000000', '1', '1');
INSERT INTO `cms_dictionary` VALUES ('4', '3', 'db.status', '2', '状态', '状态', '1', '5', null, '2019-03-06 10:11:09.499000', '2019-03-06 10:11:09.499000', '1', '1');
INSERT INTO `cms_dictionary` VALUES ('5', '4', 'db.status.on', '3', '启用', '启用', '1', '5', null, '2019-03-06 10:11:29.770000', '2019-03-06 10:11:29.770000', '1', '1');
INSERT INTO `cms_dictionary` VALUES ('6', '4', 'db.status.off', '3', '禁用', '禁用', '2', '5', null, '2019-03-06 10:11:50.289000', '2019-03-06 10:11:50.289000', '1', '1');
INSERT INTO `cms_dictionary` VALUES ('7', '3', 'db.sex', '2', '性别', '性别', '2', '5', null, '2019-03-06 10:09:05.635000', '2019-03-06 10:09:05.635000', '1', '1');
INSERT INTO `cms_dictionary` VALUES ('8', '7', 'db.sex.woman', '3', '女', '女', '1', '5', null, '2019-03-06 10:09:44.073000', '2019-03-06 10:10:29.880000', '1', '1');
INSERT INTO `cms_dictionary` VALUES ('9', '7', 'db.sex.man', '3', '男', '男', '2', '5', null, '2019-03-06 10:10:15.974000', '2019-03-06 10:10:15.974000', '1', '1');
