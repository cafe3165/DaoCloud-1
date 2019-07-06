/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : example

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 06/07/2019 17:19:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '文章内容',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效  1.有效  2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发布号作者表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (5, '自然辩证法', '2019-06-20 09:08:45', '2019-07-05 23:20:04', '1');
INSERT INTO `article` VALUES (6, '工程实践', '2019-06-18 10:49:28', '2019-07-05 23:20:10', '1');
INSERT INTO `article` VALUES (10, '数值分析', '2019-06-27 14:57:45', '2019-07-05 23:20:17', '1');
INSERT INTO `article` VALUES (11, '智能技术', '2019-06-17 15:23:42', '2019-07-05 23:20:25', '1');
INSERT INTO `article` VALUES (19, '计算机网络', '2019-06-10 13:37:07', '2019-07-05 23:20:31', '1');
INSERT INTO `article` VALUES (20, '数据结构', '2019-06-03 22:47:41', '2019-07-05 23:20:40', '1');
INSERT INTO `article` VALUES (21, '数值分析', '2019-06-04 22:48:58', '2019-07-05 23:20:45', '1');
INSERT INTO `article` VALUES (22, '计算机网络', '2019-06-25 22:49:07', '2019-07-05 23:20:49', '1');
INSERT INTO `article` VALUES (23, '智能技术', '2019-06-27 22:49:11', '2019-07-05 23:20:52', '1');
INSERT INTO `article` VALUES (24, '工程训练', '2019-07-05 22:49:17', '2019-07-05 22:49:17', '1');

-- ----------------------------
-- Table structure for stu_class
-- ----------------------------
DROP TABLE IF EXISTS `stu_class`;
CREATE TABLE `stu_class`  (
  `id` int(11) NOT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_class
-- ----------------------------
INSERT INTO `stu_class` VALUES (1, 2);
INSERT INTO `stu_class` VALUES (2, 2);
INSERT INTO `stu_class` VALUES (3, 3);
INSERT INTO `stu_class` VALUES (4, 3);
INSERT INTO `stu_class` VALUES (5, 1);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL DEFAULT 0 COMMENT '自定id,主要供前端展示权限列表分类排序使用.',
  `menu_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '菜单的中文释义',
  `permission_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
  `permission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '本权限的中文释义',
  `required_permission` tinyint(1) DEFAULT 2 COMMENT '是否本菜单必选权限, 1.必选 2非必选 通常是\"列表\"权限是必选',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (101, 'article', '课程管理', 'article:list', '列表', 1);
INSERT INTO `sys_permission` VALUES (102, 'article', '课程管理', 'article:add', '新增', 2);
INSERT INTO `sys_permission` VALUES (103, 'article', '课程管理', 'article:update', '修改', 2);
INSERT INTO `sys_permission` VALUES (601, 'user', '用户', 'user:list', '列表', 1);
INSERT INTO `sys_permission` VALUES (602, 'user', '用户', 'user:add', '新增', 2);
INSERT INTO `sys_permission` VALUES (603, 'user', '用户', 'user:update', '修改', 2);
INSERT INTO `sys_permission` VALUES (701, 'role', '角色权限', 'role:list', '列表', 1);
INSERT INTO `sys_permission` VALUES (702, 'role', '角色权限', 'role:add', '新增', 2);
INSERT INTO `sys_permission` VALUES (703, 'role', '角色权限', 'role:update', '修改', 2);
INSERT INTO `sys_permission` VALUES (704, 'role', '角色权限', 'role:delete', '删除', 2);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '2017-11-22 16:24:34', '2017-11-22 16:24:52', '1');
INSERT INTO `sys_role` VALUES (2, '老师', '2017-11-22 16:24:34', '2019-07-05 22:43:21', '1');
INSERT INTO `sys_role` VALUES (4, '学生', '2019-05-11 09:41:58', '2019-05-11 09:41:58', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否有效 1有效     2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 2, 101, '2017-11-22 16:26:21', '2017-11-22 16:26:32', '1');
INSERT INTO `sys_role_permission` VALUES (2, 2, 102, '2017-11-22 16:26:21', '2017-11-22 16:26:32', '1');
INSERT INTO `sys_role_permission` VALUES (5, 2, 602, '2017-11-22 16:28:28', '2017-11-22 16:28:28', '1');
INSERT INTO `sys_role_permission` VALUES (6, 2, 601, '2017-11-22 16:28:28', '2017-11-22 16:28:28', '1');
INSERT INTO `sys_role_permission` VALUES (7, 2, 603, '2017-11-22 16:28:28', '2017-11-22 16:28:28', '1');
INSERT INTO `sys_role_permission` VALUES (8, 2, 703, '2017-11-22 16:28:28', '2017-11-22 16:28:28', '1');
INSERT INTO `sys_role_permission` VALUES (9, 2, 701, '2017-11-22 16:28:28', '2017-11-22 16:28:28', '1');
INSERT INTO `sys_role_permission` VALUES (10, 2, 702, '2017-11-22 16:28:28', '2017-11-22 16:28:28', '1');
INSERT INTO `sys_role_permission` VALUES (11, 2, 704, '2017-11-22 16:28:31', '2017-11-22 16:28:31', '1');
INSERT INTO `sys_role_permission` VALUES (12, 2, 103, '2017-11-22 16:28:31', '2017-11-22 16:28:31', '1');
INSERT INTO `sys_role_permission` VALUES (13, 3, 601, '2017-11-22 16:28:47', '2019-07-05 22:43:24', '2');
INSERT INTO `sys_role_permission` VALUES (14, 3, 701, '2017-11-22 16:28:47', '2019-07-05 22:43:24', '2');
INSERT INTO `sys_role_permission` VALUES (15, 3, 702, '2017-11-22 16:35:01', '2019-07-05 22:43:24', '2');
INSERT INTO `sys_role_permission` VALUES (16, 3, 704, '2017-11-22 16:35:01', '2019-07-05 22:43:24', '2');
INSERT INTO `sys_role_permission` VALUES (17, 3, 102, '2017-11-22 16:35:01', '2019-07-05 22:43:24', '2');
INSERT INTO `sys_role_permission` VALUES (18, 3, 101, '2017-11-22 16:35:01', '2019-07-05 22:43:24', '2');
INSERT INTO `sys_role_permission` VALUES (19, 3, 603, '2017-11-22 16:35:01', '2019-07-05 22:43:24', '2');
INSERT INTO `sys_role_permission` VALUES (20, 4, 601, '2019-05-11 09:41:58', '2019-05-11 09:41:58', '1');
INSERT INTO `sys_role_permission` VALUES (21, 4, 602, '2019-05-11 09:41:58', '2019-05-11 09:41:58', '1');
INSERT INTO `sys_role_permission` VALUES (22, 4, 603, '2019-05-11 09:41:58', '2019-05-11 09:41:58', '1');
INSERT INTO `sys_role_permission` VALUES (23, 4, 703, '2019-07-05 22:44:34', '2019-07-05 22:44:34', '1');
INSERT INTO `sys_role_permission` VALUES (24, 4, 701, '2019-07-05 22:44:34', '2019-07-05 22:44:34', '1');
INSERT INTO `sys_role_permission` VALUES (25, 4, 704, '2019-07-05 22:44:34', '2019-07-05 22:44:34', '1');
INSERT INTO `sys_role_permission` VALUES (26, 4, 702, '2019-07-05 22:44:34', '2019-07-05 22:44:34', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `role_id` int(11) DEFAULT 0 COMMENT '角色ID',
  `class_id` int(11) DEFAULT NULL,
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `delete_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10011 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '运营后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10003, 'admin', '123456', 'admin', 1, 1, '2019-06-11 11:52:38', '2019-07-05 23:19:02', '1');
INSERT INTO `sys_user` VALUES (10009, 'teacher', '123456', '标哥', 2, 1, '2019-06-13 22:43:10', '2019-07-05 23:19:11', '1');
INSERT INTO `sys_user` VALUES (10010, 'student', '123456', '小标', 4, 1, '2019-07-05 22:43:49', '2019-07-05 22:45:09', '1');

SET FOREIGN_KEY_CHECKS = 1;
