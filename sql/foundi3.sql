/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : foundi3

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2022-01-12 00:36:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `id` bigint(20) NOT NULL,
  `table_name` varchar(255) NOT NULL COMMENT '表名',
  `table_comment` varchar(255) DEFAULT NULL COMMENT '表中文名',
  `table_create_time` datetime DEFAULT NULL COMMENT '表创建时间',
  `table_engine` varchar(255) DEFAULT NULL COMMENT '数据库引擎',
  `table_encoding` varchar(255) DEFAULT NULL COMMENT '表编码集',
  `entity_name` varchar(255) DEFAULT NULL COMMENT '实体名',
  `module` varchar(255) DEFAULT NULL COMMENT '模块名',
  `pack` varchar(255) DEFAULT NULL COMMENT '包名',
  `front_path` varchar(255) DEFAULT NULL COMMENT '前端代码路径',
  `is_sub` bit(1) DEFAULT b'0' COMMENT '是否是子表',
  `is_tree` bit(1) DEFAULT b'0' COMMENT '是否是树表',
  `tree_id` varchar(255) DEFAULT NULL COMMENT '树编码字段',
  `tree_name` varchar(255) DEFAULT NULL COMMENT '树名称字段',
  `tree_parent_id` varchar(255) DEFAULT NULL COMMENT '树父编码字段',
  `tree_sort` varchar(255) DEFAULT NULL COMMENT '树排序字段',
  `is_front_edit` bit(1) DEFAULT b'1' COMMENT '前端编辑（新增）页面',
  `is_front_detail` bit(1) DEFAULT b'0' COMMENT '前端详细页面',
  `menu_title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `menu_parent_id` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
  `author` varchar(255) DEFAULT NULL COMMENT '作者',
  PRIMARY KEY (`id`),
  UNIQUE KEY `table_name` (`table_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES ('1480801820992376834', 'sys_config', '系统配置', '2021-10-28 00:17:17', 'InnoDB', 'utf8mb4_general_ci', 'SysConfig', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统配置', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801821193703425', 'sys_user', '系统用户', '2021-10-27 22:56:49', 'InnoDB', 'utf8mb4_general_ci', 'SysUser', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统用户', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801821327921153', 'gen_table_column', '代码生成业务表字段', '2021-08-29 20:38:30', 'InnoDB', 'utf8mb4_bin', 'GenTableColumn', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '代码生成业务表字段', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801821457944577', 'sys_oauth_user', 'OAuth用户', '2021-08-07 18:15:20', 'InnoDB', 'utf8_general_ci', 'SysOauthUser', 'system', 'net.foundi.admin', null, '\0', '', null, null, null, null, '', '\0', 'OAuth用户', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801821592162305', 'gen_table', '代码生成表', '2021-05-12 23:00:45', 'InnoDB', 'utf8mb4_general_ci', 'GenTable', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '代码生成表', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801821726380034', 'sys_oper_log', '系统操作日志', '2021-02-28 00:05:18', 'InnoDB', 'utf8mb4_general_ci', 'SysOperLog', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统操作日志', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801821793488902', 'sys_dict_item', '系统字典条目', '2021-01-27 23:08:48', 'InnoDB', 'utf8mb4_general_ci', 'SysDictItem', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统字典条目', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801821919318017', 'sys_message', '系统消息', '2021-01-27 23:07:02', 'InnoDB', 'utf8mb4_general_ci', 'SysMessage', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统消息', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801821986426882', 'sys_menu', '系统菜单', '2021-01-27 23:07:02', 'InnoDB', 'utf8mb4_general_ci', 'SysMenu', 'system', 'net.foundi.admin', null, '\0', '', 'id', 'name', 'parentId', 'sort', '', '\0', '系统菜单', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822053535747', 'sys_user_role', '系统用户与角色对应关系', '2021-01-27 23:07:02', 'InnoDB', 'utf8mb4_general_ci', 'SysUserRole', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统用户与角色对应关系', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822116450306', 'sys_task', '系统任务', '2021-01-27 23:07:02', 'InnoDB', 'utf8mb4_general_ci', 'SysTask', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统任务', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822183559172', 'sys_role_menu', '系统角色与菜单对应关系', '2021-01-27 23:07:02', 'InnoDB', 'utf8mb4_general_ci', 'SysRoleMenu', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统角色与菜单对应关系', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822250668033', 'sys_role', '系统角色', '2021-01-27 23:07:02', 'InnoDB', 'utf8mb4_general_ci', 'SysRole', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统角色', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822317776898', 'sys_role_group', '系统角色与用户组对应关系', '2021-01-27 23:07:02', 'InnoDB', 'utf8mb4_general_ci', 'SysRoleGroup', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统角色与用户组对应关系', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822317776903', 'sys_message_history', '系统消息历史', '2021-01-27 23:07:02', 'InnoDB', 'utf8mb4_general_ci', 'SysMessageHistory', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统消息历史', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822447800322', 'sys_login_log', '系统访问日志', '2021-01-27 23:07:01', 'InnoDB', 'utf8mb4_general_ci', 'SysLoginLog', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统访问日志', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822447800328', 'sys_group', '系统用户组', '2021-01-27 23:07:01', 'InnoDB', 'utf8mb4_general_ci', 'SysGroup', 'system', 'net.foundi.admin', null, '\0', '', 'id', 'name', 'parentId', 'sort', '', '\0', '系统用户组', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822514909192', 'sys_file', '文件上传', '2021-01-27 23:07:01', 'InnoDB', 'utf8mb4_general_ci', 'SysFile', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '文件上传', '20', 'admin');
INSERT INTO `gen_table` VALUES ('1480801822657515522', 'sys_dict', '系统字典', '2021-01-27 23:07:01', 'InnoDB', 'utf8mb4_bin', 'SysDict', 'system', 'net.foundi.admin', null, '\0', '\0', null, null, null, null, '', '\0', '系统字典', '20', 'admin');

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '表名称',
  `column_name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '列类型',
  `column_key` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '列键类型',
  `column_extra` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '列额外参数',
  `field_type` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'JAVA类型',
  `field_name` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字段名称',
  `is_required` bit(1) DEFAULT b'0' COMMENT '是否必填',
  `is_insert` bit(1) DEFAULT b'0' COMMENT '是否为插入字段',
  `is_edit` bit(1) DEFAULT b'0' COMMENT '是否编辑字段',
  `is_list` bit(1) DEFAULT b'0' COMMENT '是否列表字段',
  `is_query` bit(1) DEFAULT b'0' COMMENT '是否查询字段',
  `is_order` bit(1) DEFAULT b'0' COMMENT '是否排序字段',
  `query_type` varchar(255) COLLATE utf8mb4_bin DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件、日期范围）',
  `dict_type` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1480801822582018059 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='代码生成业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES ('1480801820925267969', 'sys_config', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801820925267970', 'sys_config', 'config_type_dict', '配置分类（字典：SysConfigType)', 'varchar', '', '', 'String', 'configTypeDict', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801820925267971', 'sys_config', 'config_key', '键', 'varchar', '', '', 'String', 'configKey', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801820925267972', 'sys_config', 'config_value', '值', 'varchar', '', '', 'String', 'configValue', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801820925267973', 'sys_config', 'enabled', '是否启用', 'bit', '', '', 'Boolean', 'enabled', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801820925267974', 'sys_config', 'remark', '备注', 'varchar', '', '', 'String', 'remark', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801820925267975', 'sys_config', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821059485697', 'sys_user', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801821059485698', 'sys_user', 'username', '用户名', 'varchar', 'UNI', '', 'String', 'username', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801821059485699', 'sys_user', 'password', '密码', 'varchar', '', '', 'String', 'password', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801821059485700', 'sys_user', 'mobile', '手机号', 'varchar', 'UNI', '', 'String', 'mobile', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801821059485701', 'sys_user', 'group_id', '用户组', 'bigint', '', '', 'Long', 'groupId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801821059485702', 'sys_user', 'name', '姓名', 'varchar', '', '', 'String', 'name', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801821059485703', 'sys_user', 'avatar', '头像', 'varchar', '', '', 'String', 'avatar', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821059485704', 'sys_user', 'status_dict', '状态（字典：SysUserStatus，0：正常，1：禁用）', 'varchar', '', '', 'String', 'statusDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801821059485705', 'sys_user', 'email', '邮箱', 'varchar', 'UNI', '', 'String', 'email', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801821059485706', 'sys_user', 'gender_dict', '性别（字典：Gender，0：未知，1：男，2：女）', 'varchar', '', '', 'String', 'genderDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801821059485707', 'sys_user', 'birthday', '出生日期', 'datetime', '', '', 'LocalDateTime', 'birthday', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '11');
INSERT INTO `gen_table_column` VALUES ('1480801821059485708', 'sys_user', 'address', '住址', 'varchar', '', '', 'String', 'address', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '12');
INSERT INTO `gen_table_column` VALUES ('1480801821059485709', 'sys_user', 'province', '省份', 'varchar', '', '', 'String', 'province', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '13');
INSERT INTO `gen_table_column` VALUES ('1480801821059485710', 'sys_user', 'city', '所在城市', 'varchar', '', '', 'String', 'city', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '14');
INSERT INTO `gen_table_column` VALUES ('1480801821059485711', 'sys_user', 'district', '所在地区', 'varchar', '', '', 'String', 'district', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '15');
INSERT INTO `gen_table_column` VALUES ('1480801821059485712', 'sys_user', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '16');
INSERT INTO `gen_table_column` VALUES ('1480801821059485713', 'sys_user', 'create_by', '创建者id', 'bigint', '', '', 'Long', 'createBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '17');
INSERT INTO `gen_table_column` VALUES ('1480801821126594561', 'sys_user', 'update_by', '修改者id', 'bigint', '', '', 'Long', 'updateBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '18');
INSERT INTO `gen_table_column` VALUES ('1480801821126594562', 'sys_user', 'update_at', '修改时间', 'datetime', '', '', 'LocalDateTime', 'updateAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '19');
INSERT INTO `gen_table_column` VALUES ('1480801821193703426', 'gen_table_column', 'id', '主键', 'bigint', 'PRI', 'auto_increment', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801821193703427', 'gen_table_column', 'table_name', '表名称', 'varchar', '', '', 'String', 'tableName', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801821193703428', 'gen_table_column', 'column_name', '列名称', 'varchar', '', '', 'String', 'columnName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801821193703429', 'gen_table_column', 'column_comment', '列描述', 'varchar', '', '', 'String', 'columnComment', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801821193703430', 'gen_table_column', 'column_type', '列类型', 'varchar', '', '', 'String', 'columnType', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801821193703431', 'gen_table_column', 'column_key', '列键类型', 'varchar', '', '', 'String', 'columnKey', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801821193703432', 'gen_table_column', 'column_extra', '列额外参数', 'varchar', '', '', 'String', 'columnExtra', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821193703433', 'gen_table_column', 'field_type', 'JAVA类型', 'varchar', '', '', 'String', 'fieldType', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801821193703434', 'gen_table_column', 'field_name', '字段名称', 'varchar', '', '', 'String', 'fieldName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801821193703435', 'gen_table_column', 'is_required', '是否必填', 'bit', '', '', 'Boolean', 'isRequired', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801821193703436', 'gen_table_column', 'is_insert', '是否为插入字段', 'bit', '', '', 'Boolean', 'isInsert', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '11');
INSERT INTO `gen_table_column` VALUES ('1480801821193703437', 'gen_table_column', 'is_edit', '是否编辑字段', 'bit', '', '', 'Boolean', 'isEdit', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '12');
INSERT INTO `gen_table_column` VALUES ('1480801821260812289', 'gen_table_column', 'is_list', '是否列表字段', 'bit', '', '', 'Boolean', 'isList', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '13');
INSERT INTO `gen_table_column` VALUES ('1480801821260812290', 'gen_table_column', 'is_query', '是否查询字段', 'bit', '', '', 'Boolean', 'isQuery', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '14');
INSERT INTO `gen_table_column` VALUES ('1480801821260812291', 'gen_table_column', 'is_order', '是否排序字段', 'bit', '', '', 'Boolean', 'isOrder', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '15');
INSERT INTO `gen_table_column` VALUES ('1480801821260812292', 'gen_table_column', 'query_type', '查询方式（等于、不等于、大于、小于、范围）', 'varchar', '', '', 'String', 'queryType', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '16');
INSERT INTO `gen_table_column` VALUES ('1480801821260812293', 'gen_table_column', 'html_type', '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件、日期范围）', 'varchar', '', '', 'String', 'htmlType', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '17');
INSERT INTO `gen_table_column` VALUES ('1480801821260812294', 'gen_table_column', 'dict_type', '字典类型', 'varchar', '', '', 'String', 'dictType', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '18');
INSERT INTO `gen_table_column` VALUES ('1480801821260812295', 'gen_table_column', 'sort', '排序', 'int', '', '', 'Integer', 'sort', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '19');
INSERT INTO `gen_table_column` VALUES ('1480801821399224321', 'sys_oauth_user', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801821399224322', 'sys_oauth_user', 'account', '账号', 'varchar', '', '', 'String', 'account', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801821399224323', 'sys_oauth_user', 'nick_name', '昵称', 'varchar', '', '', 'String', 'nickName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801821399224324', 'sys_oauth_user', 'avatar', '头像', 'varchar', '', '', 'String', 'avatar', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801821399224325', 'sys_oauth_user', 'gender_dict', '性别（字典：Gender）', 'varchar', '', '', 'String', 'genderDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801821399224326', 'sys_oauth_user', 'open_id', 'OpenId', 'varchar', '', '', 'String', 'openId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801821399224327', 'sys_oauth_user', 'oauth_type_dict', '认证类型（字典：SysOauthType）', 'varchar', '', '', 'String', 'oauthTypeDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821399224328', 'sys_oauth_user', 'user_id', '关联user', 'bigint', '', '', 'Long', 'userId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801821399224329', 'sys_oauth_user', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801821399224330', 'sys_oauth_user', 'update_at', '修改时间', 'datetime', '', '', 'LocalDateTime', 'updateAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801821457944578', 'gen_table', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801821457944579', 'gen_table', 'table_name', '表名', 'varchar', 'UNI', '', 'String', 'tableName', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801821457944580', 'gen_table', 'table_comment', '表中文名', 'varchar', '', '', 'String', 'tableComment', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801821457944581', 'gen_table', 'table_create_time', '表创建时间', 'datetime', '', '', 'LocalDateTime', 'tableCreateTime', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801821457944582', 'gen_table', 'table_engine', '数据库引擎', 'varchar', '', '', 'String', 'tableEngine', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801821457944583', 'gen_table', 'table_encoding', '表编码集', 'varchar', '', '', 'String', 'tableEncoding', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801821457944584', 'gen_table', 'entity_name', '实体名', 'varchar', '', '', 'String', 'entityName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821457944585', 'gen_table', 'module', '模块名', 'varchar', '', '', 'String', 'module', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801821457944586', 'gen_table', 'pack', '包名', 'varchar', '', '', 'String', 'pack', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801821457944587', 'gen_table', 'front_path', '前端代码路径', 'varchar', '', '', 'String', 'frontPath', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801821457944588', 'gen_table', 'is_sub', '是否是子表', 'bit', '', '', 'Boolean', 'isSub', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '11');
INSERT INTO `gen_table_column` VALUES ('1480801821457944589', 'gen_table', 'is_tree', '是否是树表', 'bit', '', '', 'Boolean', 'isTree', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '12');
INSERT INTO `gen_table_column` VALUES ('1480801821457944590', 'gen_table', 'tree_id', '树编码字段', 'varchar', '', '', 'String', 'treeId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '13');
INSERT INTO `gen_table_column` VALUES ('1480801821457944591', 'gen_table', 'tree_name', '树名称字段', 'varchar', '', '', 'String', 'treeName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '14');
INSERT INTO `gen_table_column` VALUES ('1480801821457944592', 'gen_table', 'tree_parent_id', '树父编码字段', 'varchar', '', '', 'String', 'treeParentId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '15');
INSERT INTO `gen_table_column` VALUES ('1480801821457944593', 'gen_table', 'tree_sort', '树排序字段', 'varchar', '', '', 'String', 'treeSort', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '16');
INSERT INTO `gen_table_column` VALUES ('1480801821457944594', 'gen_table', 'is_front_edit', '前端编辑（新增）页面', 'bit', '', '', 'Boolean', 'isFrontEdit', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '17');
INSERT INTO `gen_table_column` VALUES ('1480801821525053441', 'gen_table', 'is_front_detail', '前端详细页面', 'bit', '', '', 'Boolean', 'isFrontDetail', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '18');
INSERT INTO `gen_table_column` VALUES ('1480801821525053442', 'gen_table', 'menu_title', '菜单标题', 'varchar', '', '', 'String', 'menuTitle', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '19');
INSERT INTO `gen_table_column` VALUES ('1480801821525053443', 'gen_table', 'menu_parent_id', '上级菜单ID', 'bigint', '', '', 'Long', 'menuParentId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '20');
INSERT INTO `gen_table_column` VALUES ('1480801821525053444', 'gen_table', 'author', '作者', 'varchar', '', '', 'String', 'author', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '21');
INSERT INTO `gen_table_column` VALUES ('1480801821592162306', 'sys_oper_log', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801821592162307', 'sys_oper_log', 'title', '模块标题', 'varchar', '', '', 'String', 'title', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801821659271169', 'sys_oper_log', 'method', '方法名称', 'varchar', '', '', 'String', 'method', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801821659271170', 'sys_oper_log', 'request_method', '请求方式', 'varchar', '', '', 'String', 'requestMethod', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801821659271171', 'sys_oper_log', 'oper_user_id', '操作人员ID', 'bigint', '', '', 'Long', 'operUserId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801821659271172', 'sys_oper_log', 'oper_user_name', '操作人员账号名', 'varchar', '', '', 'String', 'operUserName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801821659271173', 'sys_oper_log', 'oper_user_roles', '操作人员角色', 'varchar', '', '', 'String', 'operUserRoles', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821659271174', 'sys_oper_log', 'group_name', '用户组名称', 'varchar', '', '', 'String', 'groupName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801821659271175', 'sys_oper_log', 'oper_url', '请求URL', 'varchar', '', '', 'String', 'operUrl', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801821659271176', 'sys_oper_log', 'oper_ip', '主机地址', 'varchar', '', '', 'String', 'operIp', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801821659271177', 'sys_oper_log', 'oper_location', '操作地点', 'varchar', '', '', 'String', 'operLocation', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '11');
INSERT INTO `gen_table_column` VALUES ('1480801821659271178', 'sys_oper_log', 'oper_param', '请求参数', 'varchar', '', '', 'String', 'operParam', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '12');
INSERT INTO `gen_table_column` VALUES ('1480801821659271179', 'sys_oper_log', 'json_result', '返回参数', 'varchar', '', '', 'String', 'jsonResult', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '13');
INSERT INTO `gen_table_column` VALUES ('1480801821659271180', 'sys_oper_log', 'status_dict', '操作状态（字典：SysOperLogStatus，0：正常，1：异常）', 'varchar', '', '', 'String', 'statusDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '14');
INSERT INTO `gen_table_column` VALUES ('1480801821659271181', 'sys_oper_log', 'error_msg', '错误消息', 'varchar', '', '', 'String', 'errorMsg', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '15');
INSERT INTO `gen_table_column` VALUES ('1480801821659271182', 'sys_oper_log', 'oper_time', '操作时间', 'datetime', '', '', 'LocalDateTime', 'operTime', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '16');
INSERT INTO `gen_table_column` VALUES ('1480801821726380035', 'sys_dict_item', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801821726380036', 'sys_dict_item', 'sort', '排序（升序）', 'int', '', '', 'Integer', 'sort', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801821726380037', 'sys_dict_item', 'item_key', '字典项键值', 'varchar', 'MUL', '', 'String', 'itemKey', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801821726380038', 'sys_dict_item', 'item_value', '字典项值', 'varchar', 'MUL', '', 'String', 'itemValue', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801821726380039', 'sys_dict_item', 'dict_id', '字典类型', 'bigint', '', '', 'Long', 'dictId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801821726380040', 'sys_dict_item', 'remarks', '备注信息', 'varchar', '', '', 'String', 'remarks', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801821726380041', 'sys_dict_item', 'create_by', '创建者', 'bigint', '', '', 'Long', 'createBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821793488898', 'sys_dict_item', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801821793488899', 'sys_dict_item', 'update_by', '更新者', 'bigint', '', '', 'Long', 'updateBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801821793488900', 'sys_dict_item', 'update_at', '更新时间', 'datetime', '', '', 'LocalDateTime', 'updateAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801821793488901', 'sys_dict_item', 'del_flag', '是否删除  1：已删除  0：正常', 'bit', 'MUL', '', 'Boolean', 'delFlag', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '11');
INSERT INTO `gen_table_column` VALUES ('1480801821856403458', 'sys_message', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801821856403459', 'sys_message', 'title', '标题', 'varchar', '', '', 'String', 'title', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801821856403460', 'sys_message', 'content', '信息内容', 'varchar', '', '', 'String', 'content', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801821856403461', 'sys_message', 'type_dict', '信息类型（字典：SysMessageType）', 'varchar', '', '', 'String', 'typeDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801821856403462', 'sys_message', 'sender_id', '发送方', 'bigint', '', '', 'Long', 'senderId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801821856403463', 'sys_message', 'is_group', '是否群发', 'bit', '', '', 'Boolean', 'isGroup', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801821856403464', 'sys_message', 'group_id', '接收组', 'bigint', '', '', 'Long', 'groupId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821856403465', 'sys_message', 'create_at', '发送时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801821919318018', 'sys_menu', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801821919318019', 'sys_menu', 'parent_id', '父菜单ID，一级菜单为0', 'bigint', '', '', 'Long', 'parentId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801821919318020', 'sys_menu', 'name', '菜单名称', 'varchar', '', '', 'String', 'name', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801821919318021', 'sys_menu', 'url', '菜单URL', 'varchar', '', '', 'String', 'url', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801821919318022', 'sys_menu', 'redirect', '菜单跳转', 'varchar', '', '', 'String', 'redirect', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801821919318023', 'sys_menu', 'perms', '授权（多个用逗号分隔，如：user:list,user:create）', 'varchar', '', '', 'String', 'perms', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801821919318024', 'sys_menu', 'type_dict', '类型（字典：SysMenuType，0：目录，1：菜单，2：按钮）', 'varchar', '', '', 'String', 'typeDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801821919318025', 'sys_menu', 'page_path', '页面文件路径', 'varchar', '', '', 'String', 'pagePath', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801821919318026', 'sys_menu', 'icon', '菜单图标', 'varchar', '', '', 'String', 'icon', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801821919318027', 'sys_menu', 'abbr', '菜单缩写', 'varchar', '', '', 'String', 'abbr', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801821919318028', 'sys_menu', 'sort', '排序', 'int', '', '', 'Integer', 'sort', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '11');
INSERT INTO `gen_table_column` VALUES ('1480801821919318029', 'sys_menu', 'remark', '注释', 'varchar', '', '', 'String', 'remark', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '12');
INSERT INTO `gen_table_column` VALUES ('1480801821919318030', 'sys_menu', 'visible', '是否显示', 'bit', '', '', 'Boolean', 'visible', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '13');
INSERT INTO `gen_table_column` VALUES ('1480801821919318031', 'sys_menu', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '14');
INSERT INTO `gen_table_column` VALUES ('1480801821919318032', 'sys_menu', 'update_at', '修改时间', 'datetime', '', '', 'LocalDateTime', 'updateAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '15');
INSERT INTO `gen_table_column` VALUES ('1480801821986426883', 'sys_user_role', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822053535745', 'sys_user_role', 'user_id', '用户ID', 'bigint', '', '', 'Long', 'userId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822053535746', 'sys_user_role', 'role_id', '角色ID', 'bigint', '', '', 'Long', 'roleId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822053535748', 'sys_task', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822053535749', 'sys_task', 'job_name', '任务名', 'varchar', '', '', 'String', 'jobName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822053535750', 'sys_task', 'job_group', '任务分组', 'varchar', '', '', 'String', 'jobGroup', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822053535751', 'sys_task', 'job_status', '任务状态', 'varchar', '', '', 'String', 'jobStatus', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801822053535752', 'sys_task', 'is_concurrent', '任务是否并发', 'bit', '', '', 'Boolean', 'isConcurrent', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801822053535753', 'sys_task', 'cron_expression', 'cron表达式', 'varchar', '', '', 'String', 'cronExpression', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801822053535754', 'sys_task', 'description', '任务描述', 'varchar', '', '', 'String', 'description', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801822053535755', 'sys_task', 'bean_class', '任务执行时调用哪个类的方法 包名+类名', 'varchar', '', '', 'String', 'beanClass', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801822053535756', 'sys_task', 'spring_bean', 'Spring bean', 'varchar', '', '', 'String', 'springBean', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801822053535757', 'sys_task', 'method_name', '任务调用的方法名', 'varchar', '', '', 'String', 'methodName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801822053535758', 'sys_task', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '11');
INSERT INTO `gen_table_column` VALUES ('1480801822053535759', 'sys_task', 'create_by', '创建者', 'bigint', '', '', 'Long', 'createBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '12');
INSERT INTO `gen_table_column` VALUES ('1480801822053535760', 'sys_task', 'update_at', '更新时间', 'datetime', '', '', 'LocalDateTime', 'updateAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '13');
INSERT INTO `gen_table_column` VALUES ('1480801822053535761', 'sys_task', 'update_by', '更新者', 'bigint', '', '', 'Long', 'updateBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '14');
INSERT INTO `gen_table_column` VALUES ('1480801822183559169', 'sys_role_menu', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822183559170', 'sys_role_menu', 'role_id', '角色ID', 'bigint', '', '', 'Long', 'roleId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822183559171', 'sys_role_menu', 'menu_id', '菜单ID', 'bigint', '', '', 'Long', 'menuId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822183559173', 'sys_role', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822183559174', 'sys_role', 'name', '角色名称', 'varchar', 'UNI', '', 'String', 'name', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822183559175', 'sys_role', 'label', '角色标识', 'varchar', '', '', 'String', 'label', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822183559176', 'sys_role', 'remark', '备注', 'varchar', '', '', 'String', 'remark', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801822183559177', 'sys_role', 'data_scope_dict', '数据范围（字典：SysRoleDataScope）', 'varchar', '', '', 'String', 'dataScopeDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801822183559178', 'sys_role', 'create_by', '创建用户id', 'bigint', '', '', 'Long', 'createBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801822183559179', 'sys_role', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801822183559180', 'sys_role', 'update_by', '修改用户id', 'bigint', '', '', 'Long', 'updateBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801822183559181', 'sys_role', 'update_at', '修改时间', 'datetime', '', '', 'LocalDateTime', 'updateAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801822183559182', 'sys_role', 'del_flag', '是否删除  1：已删除  0：正常', 'bit', '', '', 'Boolean', 'delFlag', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801822250668034', 'sys_role_group', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822250668035', 'sys_role_group', 'role_id', '角色ID', 'bigint', '', '', 'Long', 'roleId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822250668036', 'sys_role_group', 'group_id', '用户组ID', 'bigint', '', '', 'Long', 'groupId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822317776899', 'sys_message_history', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822317776900', 'sys_message_history', 'receiver_id', '接收方', 'bigint', '', '', 'Long', 'receiverId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822317776901', 'sys_message_history', 'message_id', '信息内容', 'bigint', '', '', 'Long', 'messageId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822317776902', 'sys_message_history', 'status_dict', '消息状态（字典：SysMessageStatus，未读、已读、删除)', 'varchar', '', '', 'String', 'statusDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801822384885762', 'sys_login_log', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822384885763', 'sys_login_log', 'type_dict', '类型（字典：SysLoginLogType，0：login，1：logout）', 'varchar', '', '', 'String', 'typeDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822384885764', 'sys_login_log', 'user_name', '用户账号', 'varchar', '', '', 'String', 'userName', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822384885765', 'sys_login_log', 'ip', '登录IP地址', 'varchar', '', '', 'String', 'ip', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801822384885766', 'sys_login_log', 'location', '登录地点', 'varchar', '', '', 'String', 'location', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801822384885767', 'sys_login_log', 'browser', '浏览器类型', 'varchar', '', '', 'String', 'browser', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801822384885768', 'sys_login_log', 'os', '操作系统', 'varchar', '', '', 'String', 'os', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801822384885769', 'sys_login_log', 'authc_type_dict', '登录方式', 'varchar', '', '', 'String', 'authcTypeDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801822384885770', 'sys_login_log', 'status_dict', '登录状态（字典：SysLoginLogStatus，0：成功，1：失败）', 'varchar', '', '', 'String', 'statusDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '9');
INSERT INTO `gen_table_column` VALUES ('1480801822384885771', 'sys_login_log', 'message', '提示消息', 'varchar', '', '', 'String', 'message', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '10');
INSERT INTO `gen_table_column` VALUES ('1480801822384885772', 'sys_login_log', 'oper_time', '登录、登出时间', 'datetime', '', '', 'LocalDateTime', 'operTime', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '11');
INSERT INTO `gen_table_column` VALUES ('1480801822447800323', 'sys_group', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822447800324', 'sys_group', 'parent_id', '上级用户组ID，一级用户组为0', 'bigint', '', '', 'Long', 'parentId', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822447800325', 'sys_group', 'sort', '排序', 'int', '', '', 'Integer', 'sort', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822447800326', 'sys_group', 'name', '用户组名称', 'varchar', '', '', 'String', 'name', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801822447800327', 'sys_group', 'del_flag', '是否删除  1：已删除  0：正常', 'bit', '', '', 'Boolean', 'delFlag', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801822514909185', 'sys_file', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822514909186', 'sys_file', 'name', '文件名', 'varchar', '', '', 'String', 'name', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822514909187', 'sys_file', 'oss', '文件上传的OSS配置键值', 'varchar', '', '', 'String', 'oss', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822514909188', 'sys_file', 'type_dict', '文件类型（字典：SysFileType）', 'varchar', '', '', 'String', 'typeDict', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801822514909189', 'sys_file', 'url', 'URL地址', 'varchar', '', '', 'String', 'url', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801822514909190', 'sys_file', 'create_by', '创建人', 'bigint', '', '', 'Long', 'createBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801822514909191', 'sys_file', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801822582018050', 'sys_dict', 'id', '主键', 'bigint', 'PRI', '', 'Long', 'id', '', '\0', '\0', '\0', '\0', '\0', 'EQUAL', '', '', '1');
INSERT INTO `gen_table_column` VALUES ('1480801822582018051', 'sys_dict', 'name', '字典名', 'varchar', '', '', 'String', 'name', '', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '2');
INSERT INTO `gen_table_column` VALUES ('1480801822582018052', 'sys_dict', 'name_cn', '字典中文名', 'varchar', '', '', 'String', 'nameCn', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '3');
INSERT INTO `gen_table_column` VALUES ('1480801822582018053', 'sys_dict', 'remarks', '备注信息', 'varchar', '', '', 'String', 'remarks', '\0', '', '', '', '\0', '\0', 'EQUAL', 'input', '', '4');
INSERT INTO `gen_table_column` VALUES ('1480801822582018054', 'sys_dict', 'create_by', '创建者', 'bigint', '', '', 'Long', 'createBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '5');
INSERT INTO `gen_table_column` VALUES ('1480801822582018055', 'sys_dict', 'create_at', '创建时间', 'datetime', '', '', 'LocalDateTime', 'createAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '6');
INSERT INTO `gen_table_column` VALUES ('1480801822582018056', 'sys_dict', 'update_by', '更新者', 'bigint', '', '', 'Long', 'updateBy', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '7');
INSERT INTO `gen_table_column` VALUES ('1480801822582018057', 'sys_dict', 'update_at', '更新时间', 'datetime', '', '', 'LocalDateTime', 'updateAt', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '8');
INSERT INTO `gen_table_column` VALUES ('1480801822582018058', 'sys_dict', 'del_flag', '是否删除  1：已删除  0：正常', 'bit', '', '', 'Boolean', 'delFlag', '\0', '\0', '\0', '', '\0', '\0', 'EQUAL', 'input', '', '9');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL,
  `config_type_dict` varchar(100) NOT NULL COMMENT '配置分类（字典：SysConfigType)',
  `config_key` varchar(100) NOT NULL COMMENT '键',
  `config_value` varchar(1000) NOT NULL COMMENT '值',
  `enabled` bit(1) DEFAULT b'0' COMMENT '是否启用',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1000', '100', 'aliyun_oss_default', '{\n  \"accessKeyId\": \"LTAIe9c3JXXXXXXX\", \n  \"accessKeySecret\": \"XYt6gs6unAOugxxxxxxxxxxxxxx\", \n  \"bucketName\": \"foundi-01\", \n  \"endpoint\": \"http://oss-cn-beijing.aliyuncs.com\"\n}', '\0', '阿里云OSS配置', '2021-01-27 16:45:05');
INSERT INTO `sys_config` VALUES ('1001', '101', 'huawei_oss_default', '{\n  \"accessKeyId\": \"HAYCRBBVOOHXXXXXXXX\", \n  \"accessKeySecret\": \"w2XoJFk8BJHqjLRhVxxxxxxxxxxxxxx\", \n  \"bucketName\": \"foundi-01\", \n  \"endpoint\": \"obs.cn-north-4.myhuaweicloud.com\"\n}', '\0', '华为云OSS配置', '2021-01-27 16:45:41');
INSERT INTO `sys_config` VALUES ('1002', '102', 'qiniu_oss_default', '{\n  \"accessKey\": \"7zm3dbHF8kKE8WgyQXXXXXXXXXXXXXXX\", \n  \"secretKey\": \"-qi9_-NsU0tzMbXN9RjDxxxxxxxxxxxxxx\", \n  \"bucket\": \"foundi-01\", \n  \"accessUrl\": \"http://pufeth8b1.bkt.clouddn.com/\"\n}', '\0', '七牛云OSS配置', '2021-01-27 16:48:03');
INSERT INTO `sys_config` VALUES ('1003', '103', 'local_oss_default', '{\n  \"uploadDir\":\"/upload\"\n}', '', '本地文件上传目录', '2021-01-27 17:10:53');
INSERT INTO `sys_config` VALUES ('2000', '200', 'aliyun_sms_default', '{\n	\"accessKeyId\": \"LTAIeXXXXXXX\",\n	\"accessKeySecret\": \"XYt6gs6unAOugxxxxxxxxxx\",\n	\"scenes\": {\n		\"validCode\": {\n			\"signName\": \"Foundi\",\n			\"templateCode\": \"SMS_170115099\"\n		}\n	}\n}', '', '阿里云短信配置', '2021-09-06 23:45:20');
INSERT INTO `sys_config` VALUES ('2001', '201', 'huawei_sms_default', '{\n  \"url\": \"https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1\",\n	\"appKey\": \"86lBb54lIlzXXXXXXXXXXX\",\n	\"appSecret\": \"Y4zVfK8BPJp8HJ9k4xxxxxxx\",\n	\"scenes\": {\n		\"validCode\": {\n			\"sender\": \"10690400999302423\",\n			\"templateId\": \"64802b59e0d24ab4a59c0f8a778cb25d\",\n      \"signature\": \"华为云短信测试\"\n		}\n	}\n}', '\0', '华为云短信配置', '2021-09-06 23:49:49');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '字典名',
  `name_cn` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字典中文名',
  `remarks` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注信息',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '是否删除  1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('100', 'SysMenuType', '系统菜单类型', null, '1', '2020-10-17 07:18:13', '1', '2020-10-17 07:18:18', '\0');
INSERT INTO `sys_dict` VALUES ('200', 'SysMessageType', '系统消息类型', null, '1', '2020-10-17 14:30:24', '1', '2020-10-17 14:30:27', '\0');
INSERT INTO `sys_dict` VALUES ('300', 'SysMessageStatus', '系统消息状态', null, '1', '2020-10-17 14:34:18', '1', '2020-10-17 14:34:21', '\0');
INSERT INTO `sys_dict` VALUES ('400', 'SysOperLogStatus', '系统操作日志状态', null, '1', '2020-10-17 14:38:51', '1', '2020-10-17 14:38:53', '\0');
INSERT INTO `sys_dict` VALUES ('500', 'SysUserStatus', '系统用户状态', null, '1', '2020-10-17 14:41:56', '1', '2020-10-17 14:41:57', '\0');
INSERT INTO `sys_dict` VALUES ('600', 'Gender', '性别', null, '1', '2020-10-17 14:42:49', '1', '2020-10-17 14:42:52', '\0');
INSERT INTO `sys_dict` VALUES ('700', 'SysLoginLogStatus', '系统登录日志状态', null, '1', '2020-10-17 14:50:37', '1', '2020-10-17 14:50:39', '\0');
INSERT INTO `sys_dict` VALUES ('800', 'SysFileType', '系统文件类型', null, '1', '2020-10-18 06:50:51', '1', '2020-10-18 06:50:54', '\0');
INSERT INTO `sys_dict` VALUES ('900', 'SysLoginLogType', '系统登录日志类型', null, '1', '2020-10-18 14:54:09', '1', '2020-10-18 14:54:11', '\0');
INSERT INTO `sys_dict` VALUES ('1000', 'SysOAuthType', '系统OAuth用户认证类型', null, '1', '2020-10-19 22:50:02', '1', '2020-10-19 22:50:04', '\0');
INSERT INTO `sys_dict` VALUES ('1100', 'SysConfigType', '系统配置类型', null, '1', '2020-10-22 14:10:40', '1', '2020-10-22 14:10:41', '\0');
INSERT INTO `sys_dict` VALUES ('1200', 'SysRoleDataScope', '系统角色数据权限', null, '1', '2020-10-31 23:37:01', '1', '2020-10-31 23:37:03', '\0');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `id` bigint(20) NOT NULL,
  `sort` int(11) DEFAULT NULL COMMENT '排序（升序）',
  `item_key` varchar(255) NOT NULL DEFAULT '0' COMMENT '字典项键值',
  `item_value` varchar(255) DEFAULT NULL COMMENT '字典项值',
  `dict_id` bigint(20) DEFAULT NULL COMMENT '字典类型',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '是否删除  1：已删除  0：正常',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`item_value`) USING BTREE,
  KEY `sys_dict_label` (`item_key`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典条目';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES ('1000', '0', '0', '目录', '100', null, '1', '2020-10-17 14:27:35', '1', '2020-10-17 14:27:44', '\0');
INSERT INTO `sys_dict_item` VALUES ('1001', '1', '1', '菜单', '100', null, '1', '2020-10-17 14:27:37', '1', '2020-10-17 14:27:47', '\0');
INSERT INTO `sys_dict_item` VALUES ('1002', '2', '2', '按钮', '100', null, '1', '2020-10-17 14:27:41', '1', '2020-10-17 14:27:50', '\0');
INSERT INTO `sys_dict_item` VALUES ('2000', '0', '0', '系统公告', '200', null, '1', '2020-10-17 14:30:55', '1', '2020-10-17 14:31:00', '\0');
INSERT INTO `sys_dict_item` VALUES ('2001', '1', '1', '系统通知', '200', null, '1', '2020-10-17 14:31:23', '1', '2020-10-17 14:31:26', '\0');
INSERT INTO `sys_dict_item` VALUES ('3000', '0', '0', '消息未读', '300', null, '1', '2020-10-17 14:36:05', '1', '2020-10-17 14:36:11', '\0');
INSERT INTO `sys_dict_item` VALUES ('3001', '1', '1', '消息已读', '300', null, '1', '2020-10-17 14:36:40', '1', '2020-10-17 14:36:43', '\0');
INSERT INTO `sys_dict_item` VALUES ('3002', '2', '2', '消息删除', '300', null, '1', '2020-10-17 14:37:11', '1', '2020-10-17 14:37:16', '\0');
INSERT INTO `sys_dict_item` VALUES ('4000', '0', '0', '正常', '400', null, '1', '2020-10-17 14:39:39', '1', '2020-10-17 14:39:42', '\0');
INSERT INTO `sys_dict_item` VALUES ('4001', '1', '1', '异常', '400', null, '1', '2020-10-17 14:40:10', '1', '2020-10-17 14:40:12', '\0');
INSERT INTO `sys_dict_item` VALUES ('5000', '0', '0', '正常', '500', null, '1', '2020-10-17 14:43:46', '1', '2020-10-17 14:43:49', '\0');
INSERT INTO `sys_dict_item` VALUES ('5001', '1', '1', '禁用', '500', null, '1', '2020-10-17 14:44:12', '1', '2020-10-17 14:44:15', '\0');
INSERT INTO `sys_dict_item` VALUES ('6000', '0', '0', '未知', '600', null, '1', '2020-10-17 14:45:21', '1', '2020-10-17 14:45:23', '\0');
INSERT INTO `sys_dict_item` VALUES ('6001', '1', '1', '男', '600', null, '1', '2020-10-17 14:45:38', '1', '2020-10-17 14:45:41', '\0');
INSERT INTO `sys_dict_item` VALUES ('6002', '2', '2', '女', '600', null, '1', '2020-10-17 14:45:58', '1', '2020-10-17 14:45:59', '\0');
INSERT INTO `sys_dict_item` VALUES ('7000', '0', '0', '成功', '700', null, '1', '2020-10-17 14:51:15', '1', '2020-10-17 14:51:17', '\0');
INSERT INTO `sys_dict_item` VALUES ('7001', '1', '1', '失败', '700', null, '1', '2020-10-17 14:51:38', '1', '2020-10-17 14:51:41', '\0');
INSERT INTO `sys_dict_item` VALUES ('8000', '0', '1', '图片', '800', null, '1', '2020-10-18 06:52:56', '1', '2021-08-22 17:05:00', '\0');
INSERT INTO `sys_dict_item` VALUES ('8001', '1', '2', '文档', '800', null, '1', '2020-10-18 06:52:57', '1', '2021-08-22 17:05:04', '\0');
INSERT INTO `sys_dict_item` VALUES ('8002', '2', '3', '音频', '800', null, '1', '2020-10-18 06:52:57', '1', '2021-08-22 17:05:07', '\0');
INSERT INTO `sys_dict_item` VALUES ('8003', '3', '4', '视频', '800', null, '1', '2020-10-18 06:52:58', '1', '2021-08-22 17:05:10', '\0');
INSERT INTO `sys_dict_item` VALUES ('8099', '99', '99', '未知', '800', null, '1', '2020-10-18 06:52:59', '1', '2020-10-18 06:53:03', '\0');
INSERT INTO `sys_dict_item` VALUES ('9000', '0', '0', '登录', '900', null, '1', '2020-10-18 14:54:46', '1', '2020-10-18 14:54:48', '\0');
INSERT INTO `sys_dict_item` VALUES ('9001', '1', '1', '登出', '900', null, '1', '2020-10-18 14:55:09', '1', '2020-10-18 14:55:11', '\0');
INSERT INTO `sys_dict_item` VALUES ('10002', '2', '2', '微信', '1000', null, '1', '2020-10-19 22:52:54', '1', '2020-10-19 22:53:00', '\0');
INSERT INTO `sys_dict_item` VALUES ('10003', '3', '3', '微博', '1000', null, '1', '2020-10-19 22:52:56', '1', '2020-10-19 22:53:03', '\0');
INSERT INTO `sys_dict_item` VALUES ('10004', '4', '4', 'QQ', '1000', null, '1', '2020-10-19 22:52:56', '1', '2020-10-19 22:53:05', '\0');
INSERT INTO `sys_dict_item` VALUES ('11000', '0', '0', '系统配置', '1100', null, '1', '2020-10-22 23:09:19', '1', '2020-10-22 23:09:19', '\0');
INSERT INTO `sys_dict_item` VALUES ('11100', '100', '100', 'OSS（阿里云）', '1100', null, '1', '2020-10-22 23:05:52', '1', '2020-10-22 23:05:55', '\0');
INSERT INTO `sys_dict_item` VALUES ('11101', '101', '101', 'OSS（华为云）', '1100', null, '1', '2020-10-22 23:06:26', '1', '2020-10-22 23:06:29', '\0');
INSERT INTO `sys_dict_item` VALUES ('11102', '102', '102', 'OSS（七牛云）', '1100', null, '1', '2020-10-22 23:07:28', '1', '2020-10-22 23:07:31', '\0');
INSERT INTO `sys_dict_item` VALUES ('11103', '103', '103', 'OSS（本地）', '1100', null, '1', '2021-01-27 16:34:56', '1', '2021-01-27 16:35:01', '\0');
INSERT INTO `sys_dict_item` VALUES ('11200', '200', '200', 'SMS（阿里云）', '1100', null, '1', '2020-10-22 23:08:39', '1', '2020-10-22 23:08:42', '\0');
INSERT INTO `sys_dict_item` VALUES ('11201', '201', '201', 'SMS（华为云）', '1100', '', '1', '2020-10-22 23:09:19', '1', '2020-10-22 23:09:22', '\0');
INSERT INTO `sys_dict_item` VALUES ('12000', '0', '0', '全部数据权限', '1200', null, '1', '2020-10-31 23:39:14', '1', '2020-10-31 23:39:20', '\0');
INSERT INTO `sys_dict_item` VALUES ('12001', '1', '1', '自定数据权限', '1200', null, '1', '2020-10-31 23:39:15', '1', '2020-10-31 23:39:21', '\0');
INSERT INTO `sys_dict_item` VALUES ('12002', '2', '2', '本级数据权限', '1200', null, '1', '2020-10-31 23:39:17', '1', '2020-10-31 23:39:21', '\0');
INSERT INTO `sys_dict_item` VALUES ('12003', '3', '3', '本级及下级数据权限', '1200', null, '1', '2020-10-31 23:39:18', '1', '2020-10-31 23:39:22', '\0');
INSERT INTO `sys_dict_item` VALUES ('12004', '4', '4', '仅本人数据权限', '1200', null, '1', '2020-10-31 23:39:19', '1', '2020-10-31 23:39:23', '\0');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `oss` varchar(255) DEFAULT NULL COMMENT '文件上传的OSS配置键值',
  `type_dict` varchar(255) DEFAULT NULL COMMENT '文件类型（字典：SysFileType）',
  `url` varchar(255) DEFAULT NULL COMMENT 'URL地址',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES ('1430065385868201985', 'ali-pazani-RNHujsmCOqo-unsplash_fd032616.jpg', 'local_oss_default', '1', '/upload/20210824/ali-pazani-RNHujsmCOqo-unsplash_fd032616.jpg', '1', '2021-08-24 15:12:12');
INSERT INTO `sys_file` VALUES ('1430065385947893762', 'clay-banks-36PWbok7F1s-unsplash_fd000481.jpg', 'local_oss_default', '1', '/upload/20210824/clay-banks-36PWbok7F1s-unsplash_fd000481.jpg', '1', '2021-08-24 15:12:12');
INSERT INTO `sys_file` VALUES ('1430065386132443137', 'darius-bashar-WSYpuOuPoF4-unsplash_fd731607.jpg', 'local_oss_default', '1', '/upload/20210824/darius-bashar-WSYpuOuPoF4-unsplash_fd731607.jpg', '1', '2021-08-24 15:12:12');
INSERT INTO `sys_file` VALUES ('1430065386467987457', 'beautiful-beauty-bridal-3014853_fd892738.jpg', 'local_oss_default', '1', '/upload/20210824/beautiful-beauty-bridal-3014853_fd892738.jpg', '1', '2021-08-24 15:12:12');
INSERT INTO `sys_file` VALUES ('1430065797853712386', 'Pexels Videos 1093665_fd036481.mp4', 'local_oss_default', '4', '/upload/20210824/Pexels Videos 1093665_fd036481.mp4', '1', '2021-08-24 15:13:50');
INSERT INTO `sys_file` VALUES ('1430065806078742530', 'new_fd741747.mp4', 'local_oss_default', '4', '/upload/20210824/new_fd741747.mp4', '1', '2021-08-24 15:13:52');
INSERT INTO `sys_file` VALUES ('1430065807290896385', '4e7ecbfeb585a2eafa58fc4e8f28a8eb_fd289172.mov', 'local_oss_default', '4', '/upload/20210824/4e7ecbfeb585a2eafa58fc4e8f28a8eb_fd289172.mov', '1', '2021-08-24 15:13:52');
INSERT INTO `sys_file` VALUES ('1430065807500611586', '3dcf05edacaf026ba256d2724af6a107_fd366874.mov', 'local_oss_default', '4', '/upload/20210824/3dcf05edacaf026ba256d2724af6a107_fd366874.mov', '1', '2021-08-24 15:13:53');
INSERT INTO `sys_file` VALUES ('1430067645410975746', 'photo-1553301208-a3718cc0150e_fd735739.jpg', 'local_oss_default', '1', '/upload/20210824/photo-1553301208-a3718cc0150e_fd735739.jpg', '1', '2021-08-24 15:21:11');
INSERT INTO `sys_file` VALUES ('1430069152701227009', 'photo-1553301208-a3718cc0150e_fd147514.jpg', 'local_oss_default', '1', '/upload/20210824/photo-1553301208-a3718cc0150e_fd147514.jpg', '1', '2021-08-24 15:27:10');
INSERT INTO `sys_file` VALUES ('1430304655899893761', 'Pexels Videos 1093665_fd497460.mp4', 'local_oss_default', '4', '/upload/20210825/Pexels Videos 1093665_fd497460.mp4', '1', '2021-08-25 07:02:58');
INSERT INTO `sys_file` VALUES ('1430304660404576257', '戴着帽子的女士在田野里行走实拍视频_fd149198.mov', 'local_oss_default', '4', '/upload/20210825/戴着帽子的女士在田野里行走实拍视频_fd149198.mov', '1', '2021-08-25 07:02:59');
INSERT INTO `sys_file` VALUES ('1430304662015188993', 'new_fd165241.mp4', 'local_oss_default', '4', '/upload/20210825/new_fd165241.mp4', '1', '2021-08-25 07:03:00');
INSERT INTO `sys_file` VALUES ('1430312229470740482', 'Pexels Videos 1093665_fd285721.mp4', 'local_oss_default', '4', '/upload/20210825/Pexels Videos 1093665_fd285721.mp4', '1', '2021-08-25 07:33:04');
INSERT INTO `sys_file` VALUES ('1430314927834275842', 'ali-pazani-RNHujsmCOqo-unsplash_fd428456.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd428456.jpg', '1', '2021-08-25 07:43:47');
INSERT INTO `sys_file` VALUES ('1430315090564882434', 'ali-pazani-RNHujsmCOqo-unsplash_fd752738.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd752738.jpg', '1', '2021-08-25 07:44:26');
INSERT INTO `sys_file` VALUES ('1430316132488716289', 'ali-pazani-RNHujsmCOqo-unsplash_fd596137.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd596137.jpg', '1', '2021-08-25 07:48:35');
INSERT INTO `sys_file` VALUES ('1430316400664125442', 'ali-pazani-RNHujsmCOqo-unsplash_fd849225.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd849225.jpg', '1', '2021-08-25 07:49:39');
INSERT INTO `sys_file` VALUES ('1430316401247133698', 'beautiful-beauty-bridal-3014853_fd466154.jpg', 'local_oss_default', '1', '/upload/20210825/beautiful-beauty-bridal-3014853_fd466154.jpg', '1', '2021-08-25 07:49:39');
INSERT INTO `sys_file` VALUES ('1430316403474309122', '摄图网_500817434_fd505818.jpg', 'local_oss_default', '1', '/upload/20210825/摄图网_500817434_fd505818.jpg', '1', '2021-08-25 07:49:39');
INSERT INTO `sys_file` VALUES ('1430316611385958401', 'ali-pazani-RNHujsmCOqo-unsplash_fd520316.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd520316.jpg', '1', '2021-08-25 07:50:29');
INSERT INTO `sys_file` VALUES ('1430316611444678658', 'clay-banks-36PWbok7F1s-unsplash_fd647301.jpg', 'local_oss_default', '1', '/upload/20210825/clay-banks-36PWbok7F1s-unsplash_fd647301.jpg', '1', '2021-08-25 07:50:29');
INSERT INTO `sys_file` VALUES ('1430316937497288705', 'ali-pazani-RNHujsmCOqo-unsplash_fd345285.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd345285.jpg', '1', '2021-08-25 07:51:47');
INSERT INTO `sys_file` VALUES ('1430316937610534913', 'clay-banks-36PWbok7F1s-unsplash_fd756124.jpg', 'local_oss_default', '1', '/upload/20210825/clay-banks-36PWbok7F1s-unsplash_fd756124.jpg', '1', '2021-08-25 07:51:47');
INSERT INTO `sys_file` VALUES ('1430316938029965314', 'darius-bashar-WSYpuOuPoF4-unsplash_fd302756.jpg', 'local_oss_default', '1', '/upload/20210825/darius-bashar-WSYpuOuPoF4-unsplash_fd302756.jpg', '1', '2021-08-25 07:51:47');
INSERT INTO `sys_file` VALUES ('1430316938222903298', 'beautiful-beauty-bridal-3014853_fd876058.jpg', 'local_oss_default', '1', '/upload/20210825/beautiful-beauty-bridal-3014853_fd876058.jpg', '1', '2021-08-25 07:51:47');
INSERT INTO `sys_file` VALUES ('1430414102487900162', 'ali-pazani-RNHujsmCOqo-unsplash_fd900180.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd900180.jpg', '1', '2021-08-25 14:17:53');
INSERT INTO `sys_file` VALUES ('1430414102869581826', 'clay-banks-36PWbok7F1s-unsplash_fd319103.jpg', 'local_oss_default', '1', '/upload/20210825/clay-banks-36PWbok7F1s-unsplash_fd319103.jpg', '1', '2021-08-25 14:17:53');
INSERT INTO `sys_file` VALUES ('1430414102953467906', 'darius-bashar-WSYpuOuPoF4-unsplash_fd303163.jpg', 'local_oss_default', '1', '/upload/20210825/darius-bashar-WSYpuOuPoF4-unsplash_fd303163.jpg', '1', '2021-08-25 14:17:53');
INSERT INTO `sys_file` VALUES ('1430414103309983745', 'beautiful-beauty-bridal-3014853_fd216980.jpg', 'local_oss_default', '1', '/upload/20210825/beautiful-beauty-bridal-3014853_fd216980.jpg', '1', '2021-08-25 14:17:53');
INSERT INTO `sys_file` VALUES ('1430426787157909506', 'ali-pazani-RNHujsmCOqo-unsplash_fd788182.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd788182.jpg', '1', '2021-08-25 15:08:17');
INSERT INTO `sys_file` VALUES ('1430426787250184193', 'clay-banks-36PWbok7F1s-unsplash_fd967711.jpg', 'local_oss_default', '1', '/upload/20210825/clay-banks-36PWbok7F1s-unsplash_fd967711.jpg', '1', '2021-08-25 15:08:17');
INSERT INTO `sys_file` VALUES ('1430426787430539266', 'darius-bashar-WSYpuOuPoF4-unsplash_fd753287.jpg', 'local_oss_default', '1', '/upload/20210825/darius-bashar-WSYpuOuPoF4-unsplash_fd753287.jpg', '1', '2021-08-25 15:08:17');
INSERT INTO `sys_file` VALUES ('1430426788089044993', 'beautiful-beauty-bridal-3014853_fd575995.jpg', 'local_oss_default', '1', '/upload/20210825/beautiful-beauty-bridal-3014853_fd575995.jpg', '1', '2021-08-25 15:08:17');
INSERT INTO `sys_file` VALUES ('1430427032520499201', 'ali-pazani-RNHujsmCOqo-unsplash_fd380859.jpg', 'local_oss_default', '1', '/upload/20210825/ali-pazani-RNHujsmCOqo-unsplash_fd380859.jpg', '1', '2021-08-25 15:09:15');
INSERT INTO `sys_file` VALUES ('1430427039239774209', '戴着帽子的女士在田野里行走实拍视频_fd519824.mov', 'local_oss_default', '4', '/upload/20210825/戴着帽子的女士在田野里行走实拍视频_fd519824.mov', '1', '2021-08-25 15:09:17');
INSERT INTO `sys_file` VALUES ('1430427057623408642', '戴着帽子的时尚女士在铁轨上散步实拍视频_fd805871.', 'local_oss_default', '99', '/upload/20210825/戴着帽子的时尚女士在铁轨上散步实拍视频_fd805871.', '1', '2021-08-25 15:09:21');
INSERT INTO `sys_file` VALUES ('1430427265790910466', 'photo-1553301208-a3718cc0150e_fd784826.jpg', 'local_oss_default', '1', '/upload/20210825/photo-1553301208-a3718cc0150e_fd784826.jpg', '1', '2021-08-25 15:10:11');
INSERT INTO `sys_file` VALUES ('1430794631892705281', 'Pexels Videos 1093665_fd832940.mp4', 'local_oss_default', '4', '/upload/20210826/Pexels Videos 1093665_fd832940.mp4', '1', '2021-08-26 15:29:58');
INSERT INTO `sys_file` VALUES ('1434553092363288577', 'sean-kong-1dJmkdVuelg-unsplash_fd618583.png', 'local_oss_default', '1', '/upload/20210906/sean-kong-1dJmkdVuelg-unsplash_fd618583.png', '1', '2021-09-06 00:24:45');
INSERT INTO `sys_file` VALUES ('1435625396858007554', 'sean-kong-1dJmkdVuelg-unsplash_fd384467.png', 'local_oss_default', '1', '/upload/20210908/sean-kong-1dJmkdVuelg-unsplash_fd384467.png', '1', '2021-09-08 23:25:42');
INSERT INTO `sys_file` VALUES ('1435629652411428865', 'sean-kong-1dJmkdVuelg-unsplash_fd526014.png', 'local_oss_default', '1', '/upload/20210908/sean-kong-1dJmkdVuelg-unsplash_fd526014.png', '1', '2021-09-08 23:42:37');
INSERT INTO `sys_file` VALUES ('1435629928098836481', 'sean-kong-1dJmkdVuelg-unsplash_fd673283.png', 'local_oss_default', '1', '/upload/20210908/sean-kong-1dJmkdVuelg-unsplash_fd673283.png', '1', '2021-09-08 23:43:42');
INSERT INTO `sys_file` VALUES ('1435630136803209218', 'sean-kong-1dJmkdVuelg-unsplash_fd611408.png', 'local_oss_default', '1', '/upload/20210908/sean-kong-1dJmkdVuelg-unsplash_fd611408.png', '1', '2021-09-08 23:44:32');
INSERT INTO `sys_file` VALUES ('1435630287793958913', 'sean-kong-1dJmkdVuelg-unsplash_fd537162.png', 'local_oss_default', '1', '/upload/20210908/sean-kong-1dJmkdVuelg-unsplash_fd537162.png', '1', '2021-09-08 23:45:08');
INSERT INTO `sys_file` VALUES ('1442513394585317377', 'ali-pazani-RNHujsmCOqo-unsplash_fd458384.png', 'local_oss_default', '1', '/upload/20210927/ali-pazani-RNHujsmCOqo-unsplash_fd458384.png', '1', '2021-09-27 23:36:09');
INSERT INTO `sys_file` VALUES ('1453403111694659586', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd944432.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd944432.png', '1', '2021-10-28 00:47:59');
INSERT INTO `sys_file` VALUES ('1453403136424275969', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd786938.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd786938.png', '1', '2021-10-28 00:48:05');
INSERT INTO `sys_file` VALUES ('1453403137535766530', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd245593.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd245593.png', '1', '2021-10-28 00:48:06');
INSERT INTO `sys_file` VALUES ('1453403138718560257', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd597962.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd597962.png', '1', '2021-10-28 00:48:06');
INSERT INTO `sys_file` VALUES ('1453403139498700801', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd168230.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd168230.png', '1', '2021-10-28 00:48:06');
INSERT INTO `sys_file` VALUES ('1453403140161400834', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd090813.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd090813.png', '1', '2021-10-28 00:48:06');
INSERT INTO `sys_file` VALUES ('1453403145081319425', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd808187.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd808187.png', '1', '2021-10-28 00:48:07');
INSERT INTO `sys_file` VALUES ('1453403145605607425', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd316511.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd316511.png', '1', '2021-10-28 00:48:08');
INSERT INTO `sys_file` VALUES ('1453403146649989122', 'raimond-klavins-OCZALdYIv_Y-unsplash_fd729495.png', 'local_oss_default', '1', '/upload/20211028/raimond-klavins-OCZALdYIv_Y-unsplash_fd729495.png', '1', '2021-10-28 00:48:08');
INSERT INTO `sys_file` VALUES ('1464160661054447618', '微信图片_20211118142203_fd153452.png', 'local_oss_default', '1', '/upload/20211126/微信图片_20211118142203_fd153452.png', '1', '2021-11-26 17:14:39');
INSERT INTO `sys_file` VALUES ('1464160720479346690', '微信图片_20211118142203_fd880698.png', 'local_oss_default', '1', '/upload/20211126/微信图片_20211118142203_fd880698.png', '1', '2021-11-26 17:14:53');
INSERT INTO `sys_file` VALUES ('1464160722719105025', '微信图片_20211118142203_fd458864.png', 'local_oss_default', '1', '/upload/20211126/微信图片_20211118142203_fd458864.png', '1', '2021-11-26 17:14:54');
INSERT INTO `sys_file` VALUES ('1464160723507634178', '微信图片_20211118142203_fd270681.png', 'local_oss_default', '1', '/upload/20211126/微信图片_20211118142203_fd270681.png', '1', '2021-11-26 17:14:54');
INSERT INTO `sys_file` VALUES ('1465606723598311425', 'daniel-rigdon-1389526-unsplash_fd233567.png', 'local_oss_default', '1', '/upload/20211130/daniel-rigdon-1389526-unsplash_fd233567.png', '1', '2021-11-30 17:00:47');
INSERT INTO `sys_file` VALUES ('1465606777985851393', 'daniel-rigdon-1389526-unsplash_fd102876.png', 'local_oss_default', '1', '/upload/20211130/daniel-rigdon-1389526-unsplash_fd102876.png', '1', '2021-11-30 17:01:00');
INSERT INTO `sys_file` VALUES ('1465608382483275778', '001 (2)_fd768007.png', 'local_oss_default', '1', '/upload/20211130/001 (2)_fd768007.png', '1', '2021-11-30 17:07:23');
INSERT INTO `sys_file` VALUES ('1465609076967743490', '1_HTML_form_fd840904.png', 'local_oss_default', '1', '/upload/20211130/1_HTML_form_fd840904.png', '1', '2021-11-30 17:10:08');
INSERT INTO `sys_file` VALUES ('1465609127689461761', '5a52f64413671_fd088195.png', 'local_oss_default', '1', '/upload/20211130/5a52f64413671_fd088195.png', '1', '2021-11-30 17:10:20');
INSERT INTO `sys_file` VALUES ('1465609146559635458', '5a52f64413671_fd507810.png', 'local_oss_default', '1', '/upload/20211130/5a52f64413671_fd507810.png', '1', '2021-11-30 17:10:25');
INSERT INTO `sys_file` VALUES ('1465610679066042369', '5a52f64413671_fd713705.png', 'local_oss_default', '1', '/upload/20211130/5a52f64413671_fd713705.png', '1', '2021-11-30 17:16:30');
INSERT INTO `sys_file` VALUES ('1465610876940722177', '5c690f6egy1fywzgd1y8zj20j60j60u2_fd990368.png', 'local_oss_default', '1', '/upload/20211130/5c690f6egy1fywzgd1y8zj20j60j60u2_fd990368.png', '1', '2021-11-30 17:17:17');
INSERT INTO `sys_file` VALUES ('1465611051016921089', '5c690f6egy1fywzgcs6mwj20j60j6my7_fd894059.png', 'local_oss_default', '1', '/upload/20211130/5c690f6egy1fywzgcs6mwj20j60j6my7_fd894059.png', '1', '2021-11-30 17:17:59');
INSERT INTO `sys_file` VALUES ('1465615241369886721', 'aiony-haust-1174431-unsplash_fd484420.png', 'local_oss_default', '1', '/upload/20211130/aiony-haust-1174431-unsplash_fd484420.png', '1', '2021-11-30 17:34:38');
INSERT INTO `sys_file` VALUES ('1465693883198349314', 'avatar-2191931_fd984934.png', 'local_oss_default', '1', '/upload/20211130/avatar-2191931_fd984934.png', '1', '2021-11-30 22:47:08');
INSERT INTO `sys_file` VALUES ('1465695264567205890', 'religion-3452582_fd657008.png', 'local_oss_default', '1', '/upload/20211130/religion-3452582_fd657008.png', '1', '2021-11-30 22:52:37');
INSERT INTO `sys_file` VALUES ('1465695874154766337', '5c690f6egy1fywzgcqwr8j20j60j6ab2_fd690080.png', 'local_oss_default', '1', '/upload/20211130/5c690f6egy1fywzgcqwr8j20j60j6ab2_fd690080.png', '1', '2021-11-30 22:55:02');
INSERT INTO `sys_file` VALUES ('1465716209105248257', 'communication-4141527_fd786053.png', 'local_oss_default', '1', '/upload/20211201/communication-4141527_fd786053.png', '1', '2021-12-01 00:15:51');
INSERT INTO `sys_file` VALUES ('1465719452489228289', '003_fd514328.jpg', 'local_oss_default', '1', '/upload/20211201/003_fd514328.jpg', '1', '2021-12-01 00:28:44');
INSERT INTO `sys_file` VALUES ('1466329808907542530', 'play-large_fd740294.png', 'local_oss_default', '1', '/upload/20211202/play-large_fd740294.png', '1', '2021-12-02 16:54:04');
INSERT INTO `sys_file` VALUES ('1466330131055255553', 'avatar-2191931_fd840610.png', 'local_oss_default', '1', '/upload/20211202/avatar-2191931_fd840610.png', '1', '2021-12-02 16:55:21');
INSERT INTO `sys_file` VALUES ('1467391376701382657', 'ali-pazani-RNHujsmCOqo-unsplash_fd171236.png', 'local_oss_default', '1', '/upload/20211205/ali-pazani-RNHujsmCOqo-unsplash_fd171236.png', '1', '2021-12-05 15:12:22');
INSERT INTO `sys_file` VALUES ('1468620785349419009', 'sean-kong-1dJmkdVuelg-unsplash_fd640172.png', 'local_oss_default', '1', '/upload/20211209/sean-kong-1dJmkdVuelg-unsplash_fd640172.png', '1', '2021-12-09 00:37:36');
INSERT INTO `sys_file` VALUES ('1471885112550371329', 'marco-xu-UlCLN7s-nkY-unsplash_fd182012.png', 'local_oss_default', '1', '/upload/20211218/marco-xu-UlCLN7s-nkY-unsplash_fd182012.png', '1', '2021-12-18 00:48:52');
INSERT INTO `sys_file` VALUES ('1473683369689731073', 'religion-3452582_fd355233.png', 'local_oss_default', '1', '/upload/20211222/religion-3452582_fd355233.png', '1', '2021-12-22 23:54:30');
INSERT INTO `sys_file` VALUES ('1474156602436210689', '5c690f6egy1fywzgd1y8zj20j60j60u2_fd989195.png', 'local_oss_default', '1', '/upload/20211224/5c690f6egy1fywzgd1y8zj20j60j60u2_fd989195.png', '1', '2021-12-24 07:14:57');
INSERT INTO `sys_file` VALUES ('1477295471058804737', '001_fd778536.jpg', 'local_oss_default', '1', '/upload/20220101/001_fd778536.jpg', '1', '2022-01-01 23:07:42');
INSERT INTO `sys_file` VALUES ('1477295471058804738', '001 (2)_fd982315.jpg', 'local_oss_default', '1', '/upload/20220101/001 (2)_fd982315.jpg', '1', '2022-01-01 23:07:42');
INSERT INTO `sys_file` VALUES ('1477540175658614786', '预览图 (7)_fd450895.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (7)_fd450895.jpg', '1', '2022-01-02 15:20:04');
INSERT INTO `sys_file` VALUES ('1477540440386306050', '预览图 (7)_fd919628.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (7)_fd919628.jpg', '1', '2022-01-02 15:21:07');
INSERT INTO `sys_file` VALUES ('1477542254036910081', '预览图 (7)_fd871157.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (7)_fd871157.jpg', '1', '2022-01-02 15:28:19');
INSERT INTO `sys_file` VALUES ('1477542290867093505', '预览图 (7)_fd024743.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (7)_fd024743.jpg', '1', '2022-01-02 15:28:28');
INSERT INTO `sys_file` VALUES ('1477655225228591106', '预览图 (8)_fd806518.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (8)_fd806518.jpg', '1', '2022-01-02 22:57:14');
INSERT INTO `sys_file` VALUES ('1477655699767951361', '预览图 (7)_fd570380.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (7)_fd570380.jpg', '1', '2022-01-02 22:59:07');
INSERT INTO `sys_file` VALUES ('1477660415663013889', '预览图 (6)_fd606776.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (6)_fd606776.jpg', '1', '2022-01-02 23:17:51');
INSERT INTO `sys_file` VALUES ('1477660808392474626', '预览图 (2)_fd791991.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (2)_fd791991.jpg', '1', '2022-01-02 23:19:25');
INSERT INTO `sys_file` VALUES ('1477660849144332289', '预览图 (6)_fd923257.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (6)_fd923257.jpg', '1', '2022-01-02 23:19:35');
INSERT INTO `sys_file` VALUES ('1477662383508815874', '预览图 (1)_fd753425.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (1)_fd753425.jpg', '1', '2022-01-02 23:25:41');
INSERT INTO `sys_file` VALUES ('1477662479600320514', '预览图 (2)_fd479659.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (2)_fd479659.jpg', '1', '2022-01-02 23:26:03');
INSERT INTO `sys_file` VALUES ('1477662493626073090', '预览图 (8)_fd402499.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (8)_fd402499.jpg', '1', '2022-01-02 23:26:07');
INSERT INTO `sys_file` VALUES ('1477663020460015617', '预览图 (2)_fd725831.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (2)_fd725831.jpg', '1', '2022-01-02 23:28:12');
INSERT INTO `sys_file` VALUES ('1477663063204167682', '预览图 (1)_fd419787.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (1)_fd419787.jpg', '1', '2022-01-02 23:28:23');
INSERT INTO `sys_file` VALUES ('1477663331832561665', '预览图 (8)_fd690708.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (8)_fd690708.jpg', '1', '2022-01-02 23:29:27');
INSERT INTO `sys_file` VALUES ('1477663922726109185', '预览图 (7)_fd942598.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (7)_fd942598.jpg', '1', '2022-01-02 23:31:48');
INSERT INTO `sys_file` VALUES ('1477666109288087553', '预览图 (8)_fd869623.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (8)_fd869623.jpg', '1', '2022-01-02 23:40:29');
INSERT INTO `sys_file` VALUES ('1477666308668522497', '预览图 (7)_fd367282.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (7)_fd367282.jpg', '1', '2022-01-02 23:41:16');
INSERT INTO `sys_file` VALUES ('1477666409998712834', '预览图 (4)_fd465558.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (4)_fd465558.jpg', '1', '2022-01-02 23:41:41');
INSERT INTO `sys_file` VALUES ('1477666778908721153', '预览图 (6)_fd455388.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (6)_fd455388.jpg', '1', '2022-01-02 23:43:08');
INSERT INTO `sys_file` VALUES ('1477667499133632513', '预览图 (4)_fd253777.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (4)_fd253777.jpg', '1', '2022-01-02 23:46:00');
INSERT INTO `sys_file` VALUES ('1477667718697058305', '预览图 (4)_fd189574.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (4)_fd189574.jpg', '1', '2022-01-02 23:46:53');
INSERT INTO `sys_file` VALUES ('1477667745062453250', '预览图 (2)_fd544964.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (2)_fd544964.jpg', '1', '2022-01-02 23:46:59');
INSERT INTO `sys_file` VALUES ('1477667965250830337', '预览图 (7)_fd554809.jpg', 'local_oss_default', '1', '/upload/20220102/预览图 (7)_fd554809.jpg', '1', '2022-01-02 23:47:51');
INSERT INTO `sys_file` VALUES ('1477668084599750657', 'marco-xu-UlCLN7s-nkY-unsplash_fd192550.jpg', 'local_oss_default', '1', '/upload/20220102/marco-xu-UlCLN7s-nkY-unsplash_fd192550.jpg', '1', '2022-01-02 23:48:20');
INSERT INTO `sys_file` VALUES ('1477676618062233601', 'darius-bashar-WSYpuOuPoF4-unsplash_fd682157.jpg', 'local_oss_default', '1', '/upload/20220103/darius-bashar-WSYpuOuPoF4-unsplash_fd682157.jpg', '1', '2022-01-03 00:22:14');
INSERT INTO `sys_file` VALUES ('1477676689377984514', 'ali-pazani-RNHujsmCOqo-unsplash_fd710354.jpg', 'local_oss_default', '1', '/upload/20220103/ali-pazani-RNHujsmCOqo-unsplash_fd710354.jpg', '1', '2022-01-03 00:22:31');
INSERT INTO `sys_file` VALUES ('1477677389478625281', 'ali-pazani-RNHujsmCOqo-unsplash_fd966657.png', 'local_oss_default', '1', '/upload/20220103/ali-pazani-RNHujsmCOqo-unsplash_fd966657.png', '1', '2022-01-03 00:25:18');
INSERT INTO `sys_file` VALUES ('1477677566134321154', 'ali-pazani-RNHujsmCOqo-unsplash_fd823257.jpg', 'local_oss_default', '1', '/upload/20220103/ali-pazani-RNHujsmCOqo-unsplash_fd823257.jpg', '1', '2022-01-03 00:26:00');
INSERT INTO `sys_file` VALUES ('1478038183626145793', 'darius-bashar-WSYpuOuPoF4-unsplash_fd480806.jpg', 'local_oss_default', '1', '/upload/20220104/darius-bashar-WSYpuOuPoF4-unsplash_fd480806.jpg', '1', '2022-01-04 00:18:58');
INSERT INTO `sys_file` VALUES ('1479962189161861121', 'marco-xu-UlCLN7s-nkY-unsplash_fd418632.jpg', 'local_oss_default', '1', '/upload/20220109/marco-xu-UlCLN7s-nkY-unsplash_fd418632.jpg', '1', '2022-01-09 07:44:17');
INSERT INTO `sys_file` VALUES ('1479962205351874562', 'sean-kong-1dJmkdVuelg-unsplash_fd023991.jpg', 'local_oss_default', '1', '/upload/20220109/sean-kong-1dJmkdVuelg-unsplash_fd023991.jpg', '1', '2022-01-09 07:44:21');
INSERT INTO `sys_file` VALUES ('1480921813801275393', 'ali-pazani-RNHujsmCOqo-unsplash_fd039447.png', 'local_oss_default', '1', '/upload/20220111/ali-pazani-RNHujsmCOqo-unsplash_fd039447.png', '1', '2022-01-11 23:17:29');

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级用户组ID，一级用户组为0',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `name` varchar(50) NOT NULL COMMENT '用户组名称',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '是否删除  1：已删除  0：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户组';

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES ('10', '0', '10', 'foundi-admin演示站', '\0');
INSERT INTO `sys_group` VALUES ('20', '10', '20', '网站管理组', '\0');
INSERT INTO `sys_group` VALUES ('30', '10', '30', '网站用户组', '\0');
INSERT INTO `sys_group` VALUES ('1419183541404688386', '10', '40', '临时用户组', '\0');

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL,
  `type_dict` varchar(255) DEFAULT '0' COMMENT '类型（字典：SysLoginLogType，0：login，1：logout）',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ip` varchar(50) DEFAULT '' COMMENT '登录IP地址',
  `location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `authc_type_dict` varchar(255) DEFAULT '0' COMMENT '登录方式',
  `status_dict` varchar(255) DEFAULT '0' COMMENT '登录状态（字典：SysLoginLogStatus，0：成功，1：失败）',
  `message` varchar(255) DEFAULT '' COMMENT '提示消息',
  `oper_time` datetime DEFAULT NULL COMMENT '登录、登出时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统访问日志';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES ('1476953071476015105', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-01 00:27:07');
INSERT INTO `sys_login_log` VALUES ('1477295248966213634', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-01 23:06:49');
INSERT INTO `sys_login_log` VALUES ('1477415025067950081', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 07:02:46');
INSERT INTO `sys_login_log` VALUES ('1477482829137838082', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 11:32:11');
INSERT INTO `sys_login_log` VALUES ('1477483551111774210', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-02 11:35:04');
INSERT INTO `sys_login_log` VALUES ('1477483735635984386', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 11:35:48');
INSERT INTO `sys_login_log` VALUES ('1477534809436717057', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 14:58:44');
INSERT INTO `sys_login_log` VALUES ('1477535800051630082', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-02 15:02:41');
INSERT INTO `sys_login_log` VALUES ('1477535822419853313', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 15:02:46');
INSERT INTO `sys_login_log` VALUES ('1477536034949431298', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 15:03:37');
INSERT INTO `sys_login_log` VALUES ('1477539220112277505', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-02 15:16:16');
INSERT INTO `sys_login_log` VALUES ('1477539246729330689', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 15:16:22');
INSERT INTO `sys_login_log` VALUES ('1477539431085768706', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 15:17:06');
INSERT INTO `sys_login_log` VALUES ('1477540102476398594', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-02 15:19:46');
INSERT INTO `sys_login_log` VALUES ('1477540122990739457', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 15:19:51');
INSERT INTO `sys_login_log` VALUES ('1477655202390605826', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-02 22:57:08');
INSERT INTO `sys_login_log` VALUES ('1477677200823025665', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 00:24:33');
INSERT INTO `sys_login_log` VALUES ('1477677407467995137', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-03 00:25:22');
INSERT INTO `sys_login_log` VALUES ('1477677427936198658', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 00:25:27');
INSERT INTO `sys_login_log` VALUES ('1477899914670243842', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 15:09:32');
INSERT INTO `sys_login_log` VALUES ('1477900333630881794', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-03 15:11:12');
INSERT INTO `sys_login_log` VALUES ('1477900355709698050', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 15:11:17');
INSERT INTO `sys_login_log` VALUES ('1477900612573069314', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 15:12:19');
INSERT INTO `sys_login_log` VALUES ('1477900764360736769', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-03 15:12:55');
INSERT INTO `sys_login_log` VALUES ('1477900797629956098', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 15:13:03');
INSERT INTO `sys_login_log` VALUES ('1477923832546197506', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 16:44:35');
INSERT INTO `sys_login_log` VALUES ('1477936022938451970', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-03 17:33:01');
INSERT INTO `sys_login_log` VALUES ('1477936047542239233', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 17:33:07');
INSERT INTO `sys_login_log` VALUES ('1478018450751225858', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-03 23:00:34');
INSERT INTO `sys_login_log` VALUES ('1478038066026250241', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-04 00:18:30');
INSERT INTO `sys_login_log` VALUES ('1478266305889996802', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-04 15:25:27');
INSERT INTO `sys_login_log` VALUES ('1478287998016589825', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-04 16:51:39');
INSERT INTO `sys_login_log` VALUES ('1478491463590383617', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-05 06:20:09');
INSERT INTO `sys_login_log` VALUES ('1478510946765803521', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-05 07:37:34');
INSERT INTO `sys_login_log` VALUES ('1478641573003563010', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-05 16:16:37');
INSERT INTO `sys_login_log` VALUES ('1479017371946487810', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-06 17:09:55');
INSERT INTO `sys_login_log` VALUES ('1479383605837860866', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-07 17:25:12');
INSERT INTO `sys_login_log` VALUES ('1479472450616987650', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-07 23:18:14');
INSERT INTO `sys_login_log` VALUES ('1479596408796385282', '0', 'admin', '192.168.2.8', '内网IP', 'Mobile Safari', 'Mac OS X (iPhone)', '0', '0', '登录成功', '2022-01-08 07:30:48');
INSERT INTO `sys_login_log` VALUES ('1479747890401284098', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-08 17:32:44');
INSERT INTO `sys_login_log` VALUES ('1479960219940327426', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-09 07:36:27');
INSERT INTO `sys_login_log` VALUES ('1480063540336300033', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-09 14:27:01');
INSERT INTO `sys_login_log` VALUES ('1480090530208559105', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-09 16:14:16');
INSERT INTO `sys_login_log` VALUES ('1480106796109541378', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-09 17:18:54');
INSERT INTO `sys_login_log` VALUES ('1480193412400427010', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-09 23:03:05');
INSERT INTO `sys_login_log` VALUES ('1480324219400105986', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-10 07:42:52');
INSERT INTO `sys_login_log` VALUES ('1480454569170317313', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-10 16:20:49');
INSERT INTO `sys_login_log` VALUES ('1480458898644152321', '0', 'admin', '192.168.2.8', '内网IP', 'Firefox 9', 'Windows 10', '0', '0', '登录成功', '2022-01-10 16:38:02');
INSERT INTO `sys_login_log` VALUES ('1480472647056764929', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-10 17:32:40');
INSERT INTO `sys_login_log` VALUES ('1480561349468631041', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-10 23:25:08');
INSERT INTO `sys_login_log` VALUES ('1480786722827546625', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-11 14:20:41');
INSERT INTO `sys_login_log` VALUES ('1480916350011478017', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-11 22:55:47');
INSERT INTO `sys_login_log` VALUES ('1480921840233779201', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-11 23:17:36');
INSERT INTO `sys_login_log` VALUES ('1480921861675061250', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-11 23:17:41');
INSERT INTO `sys_login_log` VALUES ('1480926031706624001', '1', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '退出成功', '2022-01-11 23:34:15');
INSERT INTO `sys_login_log` VALUES ('1480926055790317569', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-11 23:34:21');
INSERT INTO `sys_login_log` VALUES ('1480926255036534785', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-11 23:35:08');
INSERT INTO `sys_login_log` VALUES ('1480927177078771714', '0', 'admin', '192.168.2.8', '内网IP', 'Chrome 9', 'Windows 10', '0', '0', '登录成功', '2022-01-11 23:38:48');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(255) NOT NULL COMMENT '菜单名称',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `redirect` varchar(255) DEFAULT NULL COMMENT '菜单跳转',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权（多个用逗号分隔，如：user:list,user:create）',
  `type_dict` varchar(255) DEFAULT '0' COMMENT '类型（字典：SysMenuType，0：目录，1：菜单，2：按钮）',
  `page_path` varchar(255) DEFAULT NULL COMMENT '页面文件路径',
  `icon` varchar(64) DEFAULT NULL COMMENT '菜单图标',
  `abbr` varchar(64) DEFAULT NULL COMMENT '菜单缩写',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '注释',
  `visible` bit(1) DEFAULT b'1' COMMENT '是否显示',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('10', '0', '管理主页', '/dashboard', null, 'admin:dashboard,system:user:get,system:role:add,system:role:get,system:role:edit', '1', null, 'home', 'DS', '0', null, '', '2020-12-13 23:38:48', '2021-12-24 17:20:29');
INSERT INTO `sys_menu` VALUES ('11', '0', '用户信息', '/userProfile', '', '', '1', null, 'me', 'UP', '10', '', '', '2021-09-06 00:17:28', '2021-12-24 17:20:45');
INSERT INTO `sys_menu` VALUES ('20', '0', '系统管理', '/system', '/system/user', '', '0', null, 'system', 'SY', '20', null, '', '2020-12-21 15:04:26', '2021-12-24 17:20:56');
INSERT INTO `sys_menu` VALUES ('30', '0', '系统工具', '/tools', '/tools/generator', '', '0', null, 'terminal', 'ST', '30', null, '', '2021-02-25 15:16:26', '2021-12-24 17:21:19');
INSERT INTO `sys_menu` VALUES ('40', '0', '系统监控', '/monitor', '/monitor/db', '', '0', null, 'electrocardiogram', 'JK', '40', null, '', '2021-12-10 14:56:36', '2021-12-10 14:58:56');
INSERT INTO `sys_menu` VALUES ('100', '0', '测试页面', '/test', '/test/uploadForm', '', '0', null, 'experiment-one', 'CS', '100', null, '', '2022-01-02 07:02:13', '2022-01-02 07:10:34');
INSERT INTO `sys_menu` VALUES ('1330895557467602946', '20', '系统配置管理', '/system/config', null, 'system:config:list', '1', null, 'setting-config', 'CO', '1', null, '', '2020-11-23 23:26:42', '2021-05-21 15:34:50');
INSERT INTO `sys_menu` VALUES ('1330895557467602947', '1330895557467602946', '查看', '', null, 'system:config:list,system:config:get', '2', null, '', null, '0', null, '', '2020-11-23 23:26:42', null);
INSERT INTO `sys_menu` VALUES ('1330895557467602948', '1330895557467602946', '添加', '', null, 'system:config:add', '2', null, '', null, '0', null, '', '2020-11-23 23:26:42', '2021-05-13 23:29:41');
INSERT INTO `sys_menu` VALUES ('1330895557467602949', '1330895557467602946', '修改', '', null, 'system:config:edit', '2', null, '', null, '0', null, '', '2020-11-23 23:26:42', null);
INSERT INTO `sys_menu` VALUES ('1330895557467602950', '1330895557467602946', '删除', '', null, 'system:config:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:26:42', null);
INSERT INTO `sys_menu` VALUES ('1330898329399246850', '20', '角色管理', '/system/role', null, 'system:role:list', '1', null, 'role', 'RO', '4', null, '', '2020-11-23 23:37:43', '2021-05-21 15:35:47');
INSERT INTO `sys_menu` VALUES ('1330898329399246851', '1330898329399246850', '查看', '', null, 'system:role:list,system:role:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898329399246852', '1330898329399246850', '添加', '', null, 'system:role:add', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898329399246853', '1330898329399246850', '修改', '', null, 'system:role:edit,system:role:export', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', '2021-07-26 08:05:13');
INSERT INTO `sys_menu` VALUES ('1330898329399246854', '1330898329399246850', '删除', '', null, 'system:role:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898329785122817', '20', '系统消息管理', '/system/message', null, 'system:message:list', '1', null, 'mail', 'ME', '7', null, '', '2020-11-23 23:37:43', '2021-05-21 15:36:19');
INSERT INTO `sys_menu` VALUES ('1330898329785122818', '1330898329785122817', '查看', '', null, 'system:message:list,system:message:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898329785122819', '1330898329785122817', '添加', '', null, 'system:message:add', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898329785122820', '1330898329785122817', '修改', '', null, 'system:message:edit', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898329785122821', '1330898329785122817', '删除', '', null, 'system:message:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330087112706', '20', '系统用户管理', '/system/user', null, 'system:user:list', '1', null, 'people', 'US', '2', null, '', '2020-11-23 23:37:43', '2021-05-21 15:35:09');
INSERT INTO `sys_menu` VALUES ('1330898330087112707', '1330898330087112706', '查看', '', null, 'system:user:list,system:user:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330087112708', '1330898330087112706', '添加', '', null, 'system:user:add', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330087112709', '1330898330087112706', '修改', '', null, 'system:user:edit,system:user:export', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330087112710', '1330898330087112706', '删除', '', null, 'system:user:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330233913345', '20', '任务管理', '/system/task', null, 'system:task:list', '1', null, 'time', 'TA', '8', null, '', '2020-11-23 23:37:43', '2021-05-21 15:36:33');
INSERT INTO `sys_menu` VALUES ('1330898330233913346', '1330898330233913345', '查看', '', null, 'system:task:list,system:task:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330233913347', '1330898330233913345', '添加', '', null, 'system:task:add', '2', null, '', null, '2', null, '', '2020-11-23 23:37:43', '2021-04-27 14:55:58');
INSERT INTO `sys_menu` VALUES ('1330898330233913348', '1330898330233913345', '修改', '', null, 'system:task:edit,system:task:export', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330233913349', '1330898330233913345', '删除', '', null, 'system:task:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330678509570', '20', '菜单管理', '/system/menu', null, 'system:menu:list', '1', null, 'view-list', 'MU', '11', null, '', '2020-11-23 23:37:43', '2021-05-21 22:48:00');
INSERT INTO `sys_menu` VALUES ('1330898330678509571', '1330898330678509570', '查看', '', null, 'system:menu:list,system:menu:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330678509572', '1330898330678509570', '添加', '', null, 'system:menu:add', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330678509573', '1330898330678509570', '修改', '', null, 'system:menu:edit,system:menu:export', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', '2021-05-13 23:48:57');
INSERT INTO `sys_menu` VALUES ('1330898330678509574', '1330898330678509570', '删除', '', null, 'system:menu:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330795950081', '20', '访问日志管理', '/system/loginLog', null, 'system:loginLog:list', '1', null, 'user-log', 'LO', '12', null, '', '2020-11-23 23:37:43', '2021-05-21 22:48:11');
INSERT INTO `sys_menu` VALUES ('1330898330795950082', '1330898330795950081', '查看', '', null, 'system:loginLog:list,system:loginLog:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330795950083', '1330898330795950081', '添加', '', null, 'system:loginLog:add', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330795950084', '1330898330795950081', '修改', '', null, 'system:loginLog:edit,system:loginLog:export', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330795950085', '1330898330795950081', '删除', '', null, 'system:loginLog:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330930167809', '20', '用户组管理', '/system/group', null, 'system:group:list', '1', null, 'peoples', 'GR', '3', null, '', '2020-11-23 23:37:43', '2021-05-21 15:35:28');
INSERT INTO `sys_menu` VALUES ('1330898330930167810', '1330898330930167809', '查看', '', null, 'system:group:list,system:group:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330930167811', '1330898330930167809', '添加', '', null, 'system:group:add', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898330930167812', '1330898330930167809', '修改', '', null, 'system:group:edit,system:group:export', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', '2021-07-25 14:16:45');
INSERT INTO `sys_menu` VALUES ('1330898330930167813', '1330898330930167809', '删除', '', null, 'system:group:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898331173437442', '20', '文件管理', '/system/file', null, 'system:file:list', '1', null, 'folder-close', 'FL', '6', null, '', '2020-11-23 23:37:43', '2021-05-21 15:36:06');
INSERT INTO `sys_menu` VALUES ('1330898331173437443', '1330898331173437442', '查看', '', null, 'system:file:list,system:file:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898331173437444', '1330898331173437442', '添加', '', null, 'system:file:add,system:file:upload', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898331173437445', '1330898331173437442', '修改', '', null, 'system:file:edit,system:file:upload', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898331173437446', '1330898331173437442', '删除', '', null, 'system:file:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898331462844418', '20', '字典条目管理', '/system/dictItem/:id', null, 'system:dictItem:list', '1', null, 'book-one', 'DC', '10', null, '\0', '2020-11-23 23:37:43', '2021-05-21 22:47:42');
INSERT INTO `sys_menu` VALUES ('1330898331462844419', '1330898331462844418', '查看', '', null, 'system:dictItem:list,system:dictItem:get', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898331462844420', '1330898331462844418', '添加', '', null, 'system:dictItem:add', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898331462844421', '1330898331462844418', '修改', '', null, 'system:dictItem:edit,system:dictItem:export', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1330898331462844422', '1330898331462844418', '删除', '', null, 'system:dictItem:delete', '2', null, '', null, '0', null, '', '2020-11-23 23:37:43', null);
INSERT INTO `sys_menu` VALUES ('1364834446406103042', '30', '代码生成表管理', '/generator', null, 'generator:genTable:list', '1', null, 'file-code', 'GN', '0', null, '', '2021-02-25 15:07:44', '2021-05-19 23:45:01');
INSERT INTO `sys_menu` VALUES ('1364834446406103043', '1364834446406103042', '查看', '', null, 'generator:genTable:list,generator:genTable:get', '2', null, '', null, '0', null, '', '2021-02-25 15:07:44', null);
INSERT INTO `sys_menu` VALUES ('1364834446406103044', '1364834446406103042', '添加', '', null, 'generator:genTable:add', '2', null, '', null, '0', null, '', '2021-02-25 15:07:44', null);
INSERT INTO `sys_menu` VALUES ('1364834446406103045', '1364834446406103042', '修改', '/generator/edit/:id', '', 'generator:genTable:edit', '1', '/generator/edit', 'terminal', null, '0', null, '', '2021-02-25 15:07:44', '2021-05-20 00:39:08');
INSERT INTO `sys_menu` VALUES ('1364834446406103046', '1364834446406103042', '删除', '', null, 'generator:genTable:delete', '2', null, '', null, '0', null, '', '2021-02-25 15:07:44', null);
INSERT INTO `sys_menu` VALUES ('1364834446406103047', '1364834446406103042', '预览', '/generator/preview/:id', null, 'generator:genTable:get', '1', '/generator/preview', 'preview-open', null, '0', null, '', '2022-01-11 23:32:14', '2022-01-11 23:33:49');
INSERT INTO `sys_menu` VALUES ('1388163373723598849', '20', '系统操作日志管理', '/system/operLog', null, 'system:operLog:list', '1', null, 'log', 'LG', '13', null, '', '2021-05-01 00:08:33', '2021-05-21 22:48:21');
INSERT INTO `sys_menu` VALUES ('1388163373723598850', '1388163373723598849', '查看', '', null, 'system:operLog:list,system:operLog:get,system:operLog:export', '2', null, '', null, '0', null, '', '2021-05-01 00:08:33', null);
INSERT INTO `sys_menu` VALUES ('1388163373723598851', '1388163373723598849', '添加', '', null, 'system:operLog:add', '2', null, '', null, '0', null, '', '2021-05-01 00:08:33', null);
INSERT INTO `sys_menu` VALUES ('1388163373723598852', '1388163373723598849', '修改', '', null, 'system:operLog:edit', '2', null, '', null, '0', null, '', '2021-05-01 00:08:33', null);
INSERT INTO `sys_menu` VALUES ('1388163373723598853', '1388163373723598849', '删除', '', null, 'system:operLog:delete', '2', null, '', null, '0', null, '', '2021-05-01 00:08:33', null);
INSERT INTO `sys_menu` VALUES ('1390339339984146433', '20', '系统字典管理', '/system/dict', null, 'system:dict:list', '1', null, 'book-one', 'DC', '9', null, '', '2021-05-07 00:15:04', '2021-05-21 15:38:03');
INSERT INTO `sys_menu` VALUES ('1390339339984146434', '1390339339984146433', '查看', '', null, 'system:dict:list,system:dict:get,system:dict:export', '2', null, '', null, '0', null, '', '2021-05-07 00:15:04', null);
INSERT INTO `sys_menu` VALUES ('1390339339984146435', '1390339339984146433', '添加', '', null, 'system:dict:add', '2', null, '', null, '0', null, '', '2021-05-07 00:15:04', null);
INSERT INTO `sys_menu` VALUES ('1390339339984146436', '1390339339984146433', '修改', '', null, 'system:dict:edit', '2', null, '', null, '0', null, '', '2021-05-07 00:15:04', null);
INSERT INTO `sys_menu` VALUES ('1390339339984146437', '1390339339984146433', '删除', '', null, 'system:dict:delete', '2', null, '', null, '0', null, '', '2021-05-07 00:15:04', null);
INSERT INTO `sys_menu` VALUES ('1423791489158344706', '20', 'OAuth用户管理', '/system/oauthUser/:id', null, 'system:oauthUser:list', '1', null, 'user-positioning', 'OU', '5', null, '\0', '2021-08-07 07:41:58', '2021-08-14 14:11:05');
INSERT INTO `sys_menu` VALUES ('1423791489158344707', '1423791489158344706', '查看', '', null, 'system:oauthUser:list,system:oauthUser:get,system:oauthUser:export', '2', null, '', null, '0', null, '', '2021-08-07 07:41:58', null);
INSERT INTO `sys_menu` VALUES ('1423791489158344708', '1423791489158344706', '添加', '', null, 'system:oauthUser:add', '2', null, '', null, '0', null, '', '2021-08-07 07:41:58', null);
INSERT INTO `sys_menu` VALUES ('1423791489158344709', '1423791489158344706', '修改', '', null, 'system:oauthUser:edit', '2', null, '', null, '0', null, '', '2021-08-07 07:41:58', null);
INSERT INTO `sys_menu` VALUES ('1423791489158344710', '1423791489158344706', '删除', '', null, 'system:oauthUser:delete', '2', null, '', null, '0', null, '', '2021-08-07 07:41:58', null);
INSERT INTO `sys_menu` VALUES ('1469200281613914114', '40', '数据源监控', '/monitor/data-source', '', 'monitor:dataSource:list,monitor:dataSource:get,monitor:dataSource:edit', '1', null, 'database-time', 'SJ', '0', '', '', '2021-12-10 15:00:18', '2021-12-11 00:07:20');
INSERT INTO `sys_menu` VALUES ('1477483348354924546', '100', '上传控件测试', '/test/uploadForm', '', 'test:uploadForm:list', '1', null, 'circle-small', 'SC', '0', '', '', '2022-01-02 11:34:15', '2022-01-02 15:19:08');
INSERT INTO `sys_menu` VALUES ('1477900255235145729', '100', '分割面板测试', '/test/splitPane', '', 'test:splitPane:list', '1', null, 'circle-small', 'FG', '20', '', '', '2022-01-03 15:10:54', '2022-01-03 16:39:16');
INSERT INTO `sys_menu` VALUES ('1477935879090601985', '100', '外部页测试', '/test/external', '', 'test:external:list', '1', null, 'circle-small', 'WB', '0', '', '', '2022-01-03 17:32:27', '2022-01-03 17:32:55');

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(2000) DEFAULT NULL COMMENT '信息内容',
  `type_dict` varchar(255) DEFAULT '0' COMMENT '信息类型（字典：SysMessageType）',
  `sender_id` bigint(20) DEFAULT NULL COMMENT '发送方',
  `is_group` bit(1) DEFAULT b'1' COMMENT '是否群发',
  `group_id` bigint(20) DEFAULT '0' COMMENT '接收组',
  `create_at` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统消息';

-- ----------------------------
-- Records of sys_message
-- ----------------------------

-- ----------------------------
-- Table structure for sys_message_history
-- ----------------------------
DROP TABLE IF EXISTS `sys_message_history`;
CREATE TABLE `sys_message_history` (
  `id` bigint(20) NOT NULL,
  `receiver_id` bigint(20) DEFAULT NULL COMMENT '接收方',
  `message_id` bigint(20) DEFAULT NULL COMMENT '信息内容',
  `status_dict` varchar(255) DEFAULT '0' COMMENT '消息状态（字典：SysMessageStatus，未读、已读、删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统消息历史';

-- ----------------------------
-- Records of sys_message_history
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oauth_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_user`;
CREATE TABLE `sys_oauth_user` (
  `id` bigint(20) NOT NULL,
  `account` varchar(255) NOT NULL COMMENT '账号',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender_dict` varchar(64) DEFAULT '0' COMMENT '性别（字典：Gender）',
  `open_id` varchar(255) DEFAULT NULL COMMENT 'OpenId',
  `oauth_type_dict` varchar(255) DEFAULT NULL COMMENT '认证类型（字典：SysOauthType）',
  `user_id` bigint(20) DEFAULT NULL COMMENT '关联user',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='OAuth用户';

-- ----------------------------
-- Records of sys_oauth_user
-- ----------------------------
INSERT INTO `sys_oauth_user` VALUES ('1423899117662367745', '管理员1111', '管理员1111', '', '1', '000111', '2', '1', '2021-08-07 14:49:39', null);
INSERT INTO `sys_oauth_user` VALUES ('1423899213061812226', '管理员111122', '管理员111122', '', '0', '111222', '3', '1', '2021-08-07 14:50:02', null);
INSERT INTO `sys_oauth_user` VALUES ('1423899278824304641', '管理员111133', '管理员111133', '', '1', '222333', '4', '1', '2021-08-07 14:50:17', null);
INSERT INTO `sys_oauth_user` VALUES ('1423899443152941058', '用户01', '用户01', '', '2', '333444', '2', '1416901112715386882', '2021-08-07 14:50:57', null);
INSERT INTO `sys_oauth_user` VALUES ('1423899536740446209', '用户02', '1', '', '2', '555666', '4', '1416901112715386882', '2021-08-07 14:51:19', '2021-08-07 14:52:39');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `id` bigint(20) NOT NULL,
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `oper_user_id` bigint(20) DEFAULT NULL COMMENT '操作人员ID',
  `oper_user_name` varchar(255) DEFAULT '' COMMENT '操作人员账号名',
  `oper_user_roles` varchar(255) DEFAULT '' COMMENT '操作人员角色',
  `group_name` varchar(50) DEFAULT '' COMMENT '用户组名称',
  `oper_url` varchar(2000) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status_dict` varchar(255) DEFAULT '0' COMMENT '操作状态（字典：SysOperLogStatus，0：正常，1：异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统操作日志';

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES ('1477295471260131329', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 001 (2).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220101/001 (2)_fd982315.jpg\"],\"bz_code\":1}', '0', '', '2022-01-01 23:07:42');
INSERT INTO `sys_oper_log` VALUES ('1477295471260131330', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 001.jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220101/001_fd778536.jpg\"],\"bz_code\":1}', '0', '', '2022-01-01 23:07:42');
INSERT INTO `sys_oper_log` VALUES ('1477416988811722754', '修改系统菜单', 'net.foundi.admin.system.controller.MenuController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=100, parentId=0, name=测试页面, url=/test, redirect=/test/upload, perms=, typeDict=0, pagePath=null, icon=experiment-one, abbr=CS, sort=100, remark=null, visible=true, createAt=2022-01-02T07:02:13, updateAt=2022-01-02T07:02:14)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"100\",\"parentId\":\"0\",\"parentPath\":null,\"sort\":100,\"level\":null,\"children\":null,\"name\":\"测试页面\",\"url\":\"/test\",\"redirect\":\"/test/upload\",\"perms\":\"\",\"typeDict\":\"0\",\"pagePath\":null,\"icon\":\"experiment-one\",\"abbr\":\"CS\",\"remark\":null,\"visible\":true,\"createAt\":\"1641078133000\",\"updateAt\":\"1641078634000\"},\"bz_code\":1}', '0', '', '2022-01-02 07:10:34');
INSERT INTO `sys_oper_log` VALUES ('1477483348552056834', '新增系统菜单', 'net.foundi.admin.system.controller.MenuController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=null, parentId=0, name=上传控件测试, url=/test/upload, redirect=, perms=, typeDict=1, pagePath=null, icon=circle-small, abbr=SC, sort=0, remark=, visible=true, createAt=null, updateAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477483348354924546\",\"parentId\":\"0\",\"parentPath\":null,\"sort\":0,\"level\":null,\"children\":null,\"name\":\"上传控件测试\",\"url\":\"/test/upload\",\"redirect\":\"\",\"perms\":\"\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"SC\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641094455000\",\"updateAt\":null},\"bz_code\":1}', '0', '', '2022-01-02 11:34:15');
INSERT INTO `sys_oper_log` VALUES ('1477483429284020225', '修改系统菜单', 'net.foundi.admin.system.controller.MenuController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=1477483348354924546, parentId=100, name=上传控件测试, url=/test/upload, redirect=, perms=, typeDict=1, pagePath=null, icon=circle-small, abbr=SC, sort=0, remark=, visible=true, createAt=2022-01-02T11:34:15, updateAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477483348354924546\",\"parentId\":\"100\",\"parentPath\":null,\"sort\":0,\"level\":null,\"children\":null,\"name\":\"上传控件测试\",\"url\":\"/test/upload\",\"redirect\":\"\",\"perms\":\"\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"SC\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641094455000\",\"updateAt\":\"1641094474000\"},\"bz_code\":1}', '0', '', '2022-01-02 11:34:34');
INSERT INTO `sys_oper_log` VALUES ('1477483533969653761', '修改系统角色', 'net.foundi.admin.system.controller.RoleController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/role', '192.168.2.8', '内网IP', '{dto=RoleDto(id=1, name=超级管理员, label=super_admin, remark=超级管理员, dataScopeDict=0, createBy=1, createAt=2020-11-19T14:11:55, updateBy=1, updateAt=2021-12-10T15:00:45, delFlag=false, menuIdList=[10, 11, 20, 1330895557467602946, 1330895557467602947, 1330895557467602948, 1330895557467602949, 1330895557467602950, 1330898330087112706, 1330898330087112707, 1330898330087112708, 1330898330087112709, 1330898330087112710, 1330898330930167809, 1330898330930167810, 1330898330930167811, 1330898330930167812, 1330898330930167813, 1330898329399246850, 1330898329399246851, 1330898329399246852, 1330898329399246853, 1330898329399246854, 1423791489158344706, 1423791489158344707, 1423791489158344708, 1423791489158344709, 1423791489158344710, 1330898331173437442, 1330898331173437443, 1330898331173437444, 1330898331173437445, 1330898331173437446, 1330898329785122817, 1330898329785122818, 1330898329785122819, 1330898329785122820, 1330898329785122821, 1330898330233913345, 1330898330233913346, 1330898330233913348, 1330898330233913349, 1330898330233913347, 1390339339984146433, 1390339339984146434, 1390339339984146435, 1390339339984146436, 1390339339984146437, 1330898331462844418, 1330898331462844419, 1330898331462844420, 1330898331462844421, 1330898331462844422, 1330898330678509570, 1330898330678509571, 1330898330678509572, 1330898330678509573, 1330898330678509574, 1330898330795950081, 1330898330795950082, 1330898330795950083, 1330898330795950084, 1330898330795950085, 1388163373723598849, 1388163373723598850, 1388163373723598851, 1388163373723598852, 1388163373723598853, 30, 1364834446406103042, 1364834446406103043, 1364834446406103044, 1364834446406103045, 1364834446406103046, 40, 1469200281613914114, 100, 1477483348354924546], groupIdList=[10, 20, 30, 1419183541404688386])}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1\",\"name\":\"超级管理员\",\"label\":\"super_admin\",\"remark\":\"超级管理员\",\"dataScopeDict\":\"0\",\"createBy\":\"1\",\"createAt\":\"1605766315000\",\"updateBy\":\"1\",\"updateAt\":\"1641094499000\",\"delFlag\":false,\"menuIdList\":[\"10\",\"11\",\"20\",\"1330895557467602946\",\"1330895557467602947\",\"1330895557467602948\",\"1330895557467602949\",\"1330895557467602950\",\"1330898330087112706\",\"1330898330087112707\",\"1330898330087112708\",\"1330898330087112709\",\"1330898330087112710\",\"1330898330930167809\",\"1330898330930167810\",\"1330898330930167811\",\"1330898330930167812\",\"1330898330930167813\",\"1330898329399246850\",\"1330898329399246851\",\"1330898329399246852\",\"1330898329399246853\",\"1330898329399246854\",\"1423791489158344706\",\"1423791489158344707\",\"1423791489158344708\",\"1423791489158344709\",\"1423791489158344710\",\"1330898331173437442\",\"1330898331173437443\",\"1330898331173437444\",\"1330898331173437445\",\"1330898331173437446\",\"1330898329785122817\",\"1330898329785122818\",\"1330898329785122819\",\"1330898329785122820\",\"1330898329785122821\",\"1330898330233913345\",\"1330898330233913346\",\"1330898330233913348\",\"1330898330233913349\",\"1330898330233913347\",\"1390339339984146433\",\"1390339339984146434\",\"1390339339984146435\",\"1390339339984146436\",\"1390339339984146437\",\"1330898331462844418\",\"1330898331462844419\",\"1330898331462844420\",\"1330898331462844421\",\"1330898331462844422\",\"1330898330678509570\",\"1330898330678509571\",\"1330898330678509572\",\"1330898330678509573\",\"1330898330678509574\",\"1330898330795950081\",\"1330898330795950082\",\"1330898330795950083\",\"1330898330795950084\",\"1330898330795950085\",\"1388163373723598849\",\"1388163373723598850\",\"1388163373723598851\",\"1388163373723598852\",\"1388163373723598853\",\"30\",\"1364834446406103042\",\"1364834446406103043\",\"1364834446406103044\",\"1364834446406103045\",\"1364834446406103046\",\"40\",\"1469200281613914114\"],\"groupIdList\":[\"10\",\"20\",\"30\",\"1419183541404688386\"]},\"bz_code\":1}', '0', '', '2022-01-02 11:34:59');
INSERT INTO `sys_oper_log` VALUES ('1477538260040286210', '修改系统菜单', 'net.foundi.admin.system.controller.MenuController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=1477483348354924546, parentId=100, name=上传控件测试, url=/test/uploadForm, redirect=, perms=, typeDict=1, pagePath=null, icon=circle-small, abbr=SC, sort=0, remark=, visible=true, createAt=2022-01-02T11:34:15, updateAt=2022-01-02T11:34:34)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477483348354924546\",\"parentId\":\"100\",\"parentPath\":null,\"sort\":0,\"level\":null,\"children\":null,\"name\":\"上传控件测试\",\"url\":\"/test/uploadForm\",\"redirect\":\"\",\"perms\":\"\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"SC\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641094455000\",\"updateAt\":\"1641107547000\"},\"bz_code\":1}', '0', '', '2022-01-02 15:12:27');
INSERT INTO `sys_oper_log` VALUES ('1477539939611574274', '修改系统菜单', 'net.foundi.admin.system.controller.MenuController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=1477483348354924546, parentId=100, name=上传控件测试, url=/test/uploadForm, redirect=, perms=test:uploadForm:list, typeDict=1, pagePath=null, icon=circle-small, abbr=SC, sort=0, remark=, visible=true, createAt=2022-01-02T11:34:15, updateAt=2022-01-02T15:12:27)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477483348354924546\",\"parentId\":\"100\",\"parentPath\":null,\"sort\":0,\"level\":null,\"children\":null,\"name\":\"上传控件测试\",\"url\":\"/test/uploadForm\",\"redirect\":\"\",\"perms\":\"test:uploadForm:list\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"SC\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641094455000\",\"updateAt\":\"1641107948000\"},\"bz_code\":1}', '0', '', '2022-01-02 15:19:08');
INSERT INTO `sys_oper_log` VALUES ('1477540175725723650', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (7).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (7)_fd450895.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 15:20:04');
INSERT INTO `sys_oper_log` VALUES ('1477540440512135170', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (7).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (7)_fd919628.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 15:21:07');
INSERT INTO `sys_oper_log` VALUES ('1477542254171127810', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (7).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (7)_fd871157.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 15:28:19');
INSERT INTO `sys_oper_log` VALUES ('1477542290997116930', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (7).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (7)_fd024743.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 15:28:28');
INSERT INTO `sys_oper_log` VALUES ('1477655225358614530', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (8).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (8)_fd806518.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 22:57:14');
INSERT INTO `sys_oper_log` VALUES ('1477655699897974785', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (7).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (7)_fd570380.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 22:59:07');
INSERT INTO `sys_oper_log` VALUES ('1477660415725928449', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (6).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (6)_fd606776.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:17:51');
INSERT INTO `sys_oper_log` VALUES ('1477660808526692354', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (2).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (2)_fd791991.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:19:25');
INSERT INTO `sys_oper_log` VALUES ('1477660849274355713', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (6).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (6)_fd923257.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:19:35');
INSERT INTO `sys_oper_log` VALUES ('1477662383575924737', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (1).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (1)_fd753425.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:25:41');
INSERT INTO `sys_oper_log` VALUES ('1477662479600320515', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (2).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (2)_fd479659.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:26:03');
INSERT INTO `sys_oper_log` VALUES ('1477662493760290817', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (8).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (8)_fd402499.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:26:07');
INSERT INTO `sys_oper_log` VALUES ('1477663020460015618', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (2).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (2)_fd725831.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:28:12');
INSERT INTO `sys_oper_log` VALUES ('1477663063204167683', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (1).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (1)_fd419787.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:28:23');
INSERT INTO `sys_oper_log` VALUES ('1477663331962585090', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (8).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (8)_fd690708.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:29:27');
INSERT INTO `sys_oper_log` VALUES ('1477663922847744001', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (7).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (7)_fd942598.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:31:48');
INSERT INTO `sys_oper_log` VALUES ('1477666109413916673', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (8).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (8)_fd869623.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:40:29');
INSERT INTO `sys_oper_log` VALUES ('1477666308735631362', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (7).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (7)_fd367282.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:41:16');
INSERT INTO `sys_oper_log` VALUES ('1477666410128736257', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (4).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (4)_fd465558.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:41:41');
INSERT INTO `sys_oper_log` VALUES ('1477666778971635713', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (6).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (6)_fd455388.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:43:08');
INSERT INTO `sys_oper_log` VALUES ('1477667499133632514', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (4).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (4)_fd253777.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:46:00');
INSERT INTO `sys_oper_log` VALUES ('1477667718764167169', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (4).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (4)_fd189574.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:46:53');
INSERT INTO `sys_oper_log` VALUES ('1477667745192476673', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (2).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (2)_fd544964.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:46:59');
INSERT INTO `sys_oper_log` VALUES ('1477667965376659458', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: 预览图 (7).jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/预览图 (7)_fd554809.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:47:51');
INSERT INTO `sys_oper_log` VALUES ('1477668084658470913', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: marco-xu-UlCLN7s-nkY-unsplash.jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220102/marco-xu-UlCLN7s-nkY-unsplash_fd192550.jpg\"],\"bz_code\":1}', '0', '', '2022-01-02 23:48:20');
INSERT INTO `sys_oper_log` VALUES ('1477676618129342465', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: darius-bashar-WSYpuOuPoF4-unsplash.jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220103/darius-bashar-WSYpuOuPoF4-unsplash_fd682157.jpg\"],\"bz_code\":1,\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJhY2NvdW50IjoiYjhlNWNiMGU0NmU3NGNjZDkzY2ZiOGI0MWIyY2Q1N2MifQ.s08V5g4OI0SlZzM15ZSo3jEtML_1RZCOQTQKmgkonM7k_zjE7qlCrl8VNyZrWSpN_v-vY-7Un1ags3usRvj_XQ\"}', '0', '', '2022-01-03 00:22:14');
INSERT INTO `sys_oper_log` VALUES ('1477676689533173762', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: ali-pazani-RNHujsmCOqo-unsplash.jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220103/ali-pazani-RNHujsmCOqo-unsplash_fd710354.jpg\"],\"bz_code\":1}', '0', '', '2022-01-03 00:22:31');
INSERT INTO `sys_oper_log` VALUES ('1477677389608648706', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: ali-pazani-RNHujsmCOqo-unsplash.png', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220103/ali-pazani-RNHujsmCOqo-unsplash_fd966657.png\"],\"bz_code\":1}', '0', '', '2022-01-03 00:25:18');
INSERT INTO `sys_oper_log` VALUES ('1477677389805780994', '修改当前用户头像', 'net.foundi.admin.system.controller.UserController.CurrentUserChangeAvatar()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/user/current/avatar', '192.168.2.8', '内网IP', '{avatar=/upload/20220103/ali-pazani-RNHujsmCOqo-unsplash_fd966657.png}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-03 00:25:18');
INSERT INTO `sys_oper_log` VALUES ('1477677566197235713', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: ali-pazani-RNHujsmCOqo-unsplash.jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220103/ali-pazani-RNHujsmCOqo-unsplash_fd823257.jpg\"],\"bz_code\":1}', '0', '', '2022-01-03 00:26:00');
INSERT INTO `sys_oper_log` VALUES ('1477900255432278018', '新增系统菜单', 'net.foundi.admin.system.controller.MenuController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=null, parentId=100, name=分割面板测试, url=/test/split-pane, redirect=, perms=, typeDict=1, pagePath=null, icon=circle-small, abbr=FG, sort=20, remark=, visible=true, createAt=null, updateAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477900255235145729\",\"parentId\":\"100\",\"parentPath\":null,\"sort\":20,\"level\":null,\"children\":null,\"name\":\"分割面板测试\",\"url\":\"/test/split-pane\",\"redirect\":\"\",\"perms\":\"\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"FG\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641193854000\",\"updateAt\":null},\"bz_code\":1}', '0', '', '2022-01-03 15:10:54');
INSERT INTO `sys_oper_log` VALUES ('1477900317952573441', '修改系统角色', 'net.foundi.admin.system.controller.RoleController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/role', '192.168.2.8', '内网IP', '{dto=RoleDto(id=1, name=超级管理员, label=super_admin, remark=超级管理员, dataScopeDict=0, createBy=1, createAt=2020-11-19T14:11:55, updateBy=1, updateAt=2022-01-02T11:34:59, delFlag=false, menuIdList=[10, 11, 20, 1330895557467602946, 1330895557467602947, 1330895557467602948, 1330895557467602949, 1330895557467602950, 1330898330087112706, 1330898330087112707, 1330898330087112708, 1330898330087112709, 1330898330087112710, 1330898330930167809, 1330898330930167810, 1330898330930167811, 1330898330930167812, 1330898330930167813, 1330898329399246850, 1330898329399246851, 1330898329399246852, 1330898329399246853, 1330898329399246854, 1423791489158344706, 1423791489158344707, 1423791489158344708, 1423791489158344709, 1423791489158344710, 1330898331173437442, 1330898331173437443, 1330898331173437444, 1330898331173437445, 1330898331173437446, 1330898329785122817, 1330898329785122818, 1330898329785122819, 1330898329785122820, 1330898329785122821, 1330898330233913345, 1330898330233913346, 1330898330233913348, 1330898330233913349, 1330898330233913347, 1390339339984146433, 1390339339984146434, 1390339339984146435, 1390339339984146436, 1390339339984146437, 1330898331462844418, 1330898331462844419, 1330898331462844420, 1330898331462844421, 1330898331462844422, 1330898330678509570, 1330898330678509571, 1330898330678509572, 1330898330678509573, 1330898330678509574, 1330898330795950081, 1330898330795950082, 1330898330795950083, 1330898330795950084, 1330898330795950085, 1388163373723598849, 1388163373723598850, 1388163373723598851, 1388163373723598852, 1388163373723598853, 30, 1364834446406103042, 1364834446406103043, 1364834446406103044, 1364834446406103045, 1364834446406103046, 40, 1469200281613914114, 100, 1477483348354924546, 1477900255235145729], groupIdList=[10, 20, 30, 1419183541404688386])}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1\",\"name\":\"超级管理员\",\"label\":\"super_admin\",\"remark\":\"超级管理员\",\"dataScopeDict\":\"0\",\"createBy\":\"1\",\"createAt\":\"1605766315000\",\"updateBy\":\"1\",\"updateAt\":\"1641193868000\",\"delFlag\":false,\"menuIdList\":[\"10\",\"11\",\"20\",\"1330895557467602946\",\"1330895557467602947\",\"1330895557467602948\",\"1330895557467602949\",\"1330895557467602950\",\"1330898330087112706\",\"1330898330087112707\",\"1330898330087112708\",\"1330898330087112709\",\"1330898330087112710\",\"1330898330930167809\",\"1330898330930167810\",\"1330898330930167811\",\"1330898330930167812\",\"1330898330930167813\",\"1330898329399246850\",\"1330898329399246851\",\"1330898329399246852\",\"1330898329399246853\",\"1330898329399246854\",\"1423791489158344706\",\"1423791489158344707\",\"1423791489158344708\",\"1423791489158344709\",\"1423791489158344710\",\"1330898331173437442\",\"1330898331173437443\",\"1330898331173437444\",\"1330898331173437445\",\"1330898331173437446\",\"1330898329785122817\",\"1330898329785122818\",\"1330898329785122819\",\"1330898329785122820\",\"1330898329785122821\",\"1330898330233913345\",\"1330898330233913346\",\"1330898330233913348\",\"1330898330233913349\",\"1330898330233913347\",\"1390339339984146433\",\"1390339339984146434\",\"1390339339984146435\",\"1390339339984146436\",\"1390339339984146437\",\"1330898331462844418\",\"1330898331462844419\",\"1330898331462844420\",\"1330898331462844421\",\"1330898331462844422\",\"1330898330678509570\",\"1330898330678509571\",\"1330898330678509572\",\"1330898330678509573\",\"1330898330678509574\",\"1330898330795950081\",\"1330898330795950082\",\"1330898330795950083\",\"1330898330795950084\",\"1330898330795950085\",\"1388163373723598849\",\"1388163373723598850\",\"1388163373723598851\",\"1388163373723598852\",\"1388163373723598853\",\"30\",\"1364834446406103042\",\"1364834446406103043\",\"1364834446406103044\",\"1364834446406103045\",\"1364834446406103046\",\"40\",\"1469200281613914114\",\"100\",\"1477483348354924546\"],\"groupIdList\":[\"10\",\"20\",\"30\",\"1419183541404688386\"]},\"bz_code\":1}', '0', '', '2022-01-03 15:11:09');
INSERT INTO `sys_oper_log` VALUES ('1477900739064889346', '修改系统菜单', 'net.foundi.admin.system.controller.MenuController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=1477900255235145729, parentId=100, name=分割面板测试, url=/test/split-pane, redirect=, perms=test:splitPane:list, typeDict=1, pagePath=null, icon=circle-small, abbr=FG, sort=20, remark=, visible=true, createAt=2022-01-03T15:10:54, updateAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477900255235145729\",\"parentId\":\"100\",\"parentPath\":null,\"sort\":20,\"level\":null,\"children\":null,\"name\":\"分割面板测试\",\"url\":\"/test/split-pane\",\"redirect\":\"\",\"perms\":\"test:splitPane:list\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"FG\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641193854000\",\"updateAt\":\"1641193969000\"},\"bz_code\":1}', '0', '', '2022-01-03 15:12:49');
INSERT INTO `sys_oper_log` VALUES ('1477922493690806273', '修改系统菜单', 'net.foundi.admin.system.controller.MenuController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=1477900255235145729, parentId=100, name=分割面板测试, url=/test/splitPane, redirect=, perms=test:splitPane:list, typeDict=1, pagePath=null, icon=circle-small, abbr=FG, sort=20, remark=, visible=true, createAt=2022-01-03T15:10:54, updateAt=2022-01-03T15:12:49)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477900255235145729\",\"parentId\":\"100\",\"parentPath\":null,\"sort\":20,\"level\":null,\"children\":null,\"name\":\"分割面板测试\",\"url\":\"/test/splitPane\",\"redirect\":\"\",\"perms\":\"test:splitPane:list\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"FG\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641193854000\",\"updateAt\":\"1641199156000\"},\"bz_code\":1}', '0', '', '2022-01-03 16:39:16');
INSERT INTO `sys_oper_log` VALUES ('1477923957041528834', '删除系统访问日志', 'net.foundi.admin.system.controller.LoginLogController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/system/loginLog/1399015389781913602,1398661932021542914,1398301950277152769,1398301694000984066,1398296159985954817,1398293442366369793,1398291950184001538,1397931230057558017,1397566860374929410,1397222397484036098,1396851494413717505,1396492306411999233,1396122814712434689,1396114542211158017,1395753429644660738,1395644798085419010,1395388596877303809,1395039267473907713,1394903091551199233,1394327830908690434,1393797665229168641,1392869616518295553,1392733808469487618,1392730273602375682,1392366855968100354,1391998860141891585,1391998848259428353,1391634557710639105,1391442242274598914,1391421770958905346,1391410288191873025,1391409346579054594,1391408943011512322,1391408620343705601,1391279470911995906,1391274914455011329,1390676559966294018,1390343830494040065,1390340617057439745,1390192144324616193,1390192131888504834,1390192116172447746,1389960036863827970,1389478051624116226,1388523890917023745,1388165190691889154,1388164082246402049,1388142347325313026,1387747170492448770,1387079879689768962,1386711408825196545,1386699795585949698,1386334415709396993,1386213276739698689,1385967950959235074,1385967937126420481,1385254066740912130,1385250275442311170,1385247815894044674', '192.168.2.8', '内网IP', '{ids=[1399015389781913602, 1398661932021542914, 1398301950277152769, 1398301694000984066, 1398296159985954817, 1398293442366369793, 1398291950184001538, 1397931230057558017, 1397566860374929410, 1397222397484036098, 1396851494413717505, 1396492306411999233, 1396122814712434689, 1396114542211158017, 1395753429644660738, 1395644798085419010, 1395388596877303809, 1395039267473907713, 1394903091551199233, 1394327830908690434, 1393797665229168641, 1392869616518295553, 1392733808469487618, 1392730273602375682, 1392366855968100354, 1391998860141891585, 1391998848259428353, 1391634557710639105, 1391442242274598914, 1391421770958905346, 1391410288191873025, 1391409346579054594, 1391408943011512322, 1391408620343705601, 1391279470911995906, 1391274914455011329, 1390676559966294018, 1390343830494040065, 1390340617057439745, 1390192144324616193, 1390192131888504834, 1390192116172447746, 1389960036863827970, 1389478051624116226, 1388523890917023745, 1388165190691889154, 1388164082246402049, 1388142347325313026, 1387747170492448770, 1387079879689768962, 1386711408825196545, 1386699795585949698, 1386334415709396993, 1386213276739698689, 1385967950959235074, 1385967937126420481, 1385254066740912130, 1385250275442311170, 1385247815894044674]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-03 16:45:04');
INSERT INTO `sys_oper_log` VALUES ('1477935879157710850', '新增系统菜单', 'net.foundi.admin.system.controller.MenuController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=null, parentId=100, name=测试外部页, url=/test/external, redirect=, perms=test:external:list, typeDict=1, pagePath=null, icon=circle-small, abbr=WB, sort=0, remark=, visible=true, createAt=null, updateAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477935879090601985\",\"parentId\":\"100\",\"parentPath\":null,\"sort\":0,\"level\":null,\"children\":null,\"name\":\"测试外部页\",\"url\":\"/test/external\",\"redirect\":\"\",\"perms\":\"test:external:list\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"WB\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641202347000\",\"updateAt\":null},\"bz_code\":1}', '0', '', '2022-01-03 17:32:27');
INSERT INTO `sys_oper_log` VALUES ('1477935928449171457', '修改系统角色', 'net.foundi.admin.system.controller.RoleController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/role', '192.168.2.8', '内网IP', '{dto=RoleDto(id=1, name=超级管理员, label=super_admin, remark=超级管理员, dataScopeDict=0, createBy=1, createAt=2020-11-19T14:11:55, updateBy=1, updateAt=2022-01-03T15:11:08, delFlag=false, menuIdList=[10, 11, 20, 1330895557467602946, 1330895557467602947, 1330895557467602948, 1330895557467602949, 1330895557467602950, 1330898330087112706, 1330898330087112707, 1330898330087112708, 1330898330087112709, 1330898330087112710, 1330898330930167809, 1330898330930167810, 1330898330930167811, 1330898330930167812, 1330898330930167813, 1330898329399246850, 1330898329399246851, 1330898329399246852, 1330898329399246853, 1330898329399246854, 1423791489158344706, 1423791489158344707, 1423791489158344708, 1423791489158344709, 1423791489158344710, 1330898331173437442, 1330898331173437443, 1330898331173437444, 1330898331173437445, 1330898331173437446, 1330898329785122817, 1330898329785122818, 1330898329785122819, 1330898329785122820, 1330898329785122821, 1330898330233913345, 1330898330233913346, 1330898330233913348, 1330898330233913349, 1330898330233913347, 1390339339984146433, 1390339339984146434, 1390339339984146435, 1390339339984146436, 1390339339984146437, 1330898331462844418, 1330898331462844419, 1330898331462844420, 1330898331462844421, 1330898331462844422, 1330898330678509570, 1330898330678509571, 1330898330678509572, 1330898330678509573, 1330898330678509574, 1330898330795950081, 1330898330795950082, 1330898330795950083, 1330898330795950084, 1330898330795950085, 1388163373723598849, 1388163373723598850, 1388163373723598851, 1388163373723598852, 1388163373723598853, 30, 1364834446406103042, 1364834446406103043, 1364834446406103044, 1364834446406103045, 1364834446406103046, 40, 1469200281613914114, 100, 1477483348354924546, 1477935879090601985, 1477900255235145729], groupIdList=[10, 20, 30, 1419183541404688386])}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1\",\"name\":\"超级管理员\",\"label\":\"super_admin\",\"remark\":\"超级管理员\",\"dataScopeDict\":\"0\",\"createBy\":\"1\",\"createAt\":\"1605766315000\",\"updateBy\":\"1\",\"updateAt\":\"1641202359000\",\"delFlag\":false,\"menuIdList\":[\"10\",\"11\",\"20\",\"1330895557467602946\",\"1330895557467602947\",\"1330895557467602948\",\"1330895557467602949\",\"1330895557467602950\",\"1330898330087112706\",\"1330898330087112707\",\"1330898330087112708\",\"1330898330087112709\",\"1330898330087112710\",\"1330898330930167809\",\"1330898330930167810\",\"1330898330930167811\",\"1330898330930167812\",\"1330898330930167813\",\"1330898329399246850\",\"1330898329399246851\",\"1330898329399246852\",\"1330898329399246853\",\"1330898329399246854\",\"1423791489158344706\",\"1423791489158344707\",\"1423791489158344708\",\"1423791489158344709\",\"1423791489158344710\",\"1330898331173437442\",\"1330898331173437443\",\"1330898331173437444\",\"1330898331173437445\",\"1330898331173437446\",\"1330898329785122817\",\"1330898329785122818\",\"1330898329785122819\",\"1330898329785122820\",\"1330898329785122821\",\"1330898330233913345\",\"1330898330233913346\",\"1330898330233913348\",\"1330898330233913349\",\"1330898330233913347\",\"1390339339984146433\",\"1390339339984146434\",\"1390339339984146435\",\"1390339339984146436\",\"1390339339984146437\",\"1330898331462844418\",\"1330898331462844419\",\"1330898331462844420\",\"1330898331462844421\",\"1330898331462844422\",\"1330898330678509570\",\"1330898330678509571\",\"1330898330678509572\",\"1330898330678509573\",\"1330898330678509574\",\"1330898330795950081\",\"1330898330795950082\",\"1330898330795950083\",\"1330898330795950084\",\"1330898330795950085\",\"1388163373723598849\",\"1388163373723598850\",\"1388163373723598851\",\"1388163373723598852\",\"1388163373723598853\",\"30\",\"1364834446406103042\",\"1364834446406103043\",\"1364834446406103044\",\"1364834446406103045\",\"1364834446406103046\",\"40\",\"1469200281613914114\",\"100\",\"1477483348354924546\",\"1477900255235145729\"],\"groupIdList\":[\"10\",\"20\",\"30\",\"1419183541404688386\"]},\"bz_code\":1}', '0', '', '2022-01-03 17:32:39');
INSERT INTO `sys_oper_log` VALUES ('1477935995633532929', '修改系统菜单', 'net.foundi.admin.system.controller.MenuController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=1477935879090601985, parentId=100, name=外部页测试, url=/test/external, redirect=, perms=test:external:list, typeDict=1, pagePath=null, icon=circle-small, abbr=WB, sort=0, remark=, visible=true, createAt=2022-01-03T17:32:27, updateAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1477935879090601985\",\"parentId\":\"100\",\"parentPath\":null,\"sort\":0,\"level\":null,\"children\":null,\"name\":\"外部页测试\",\"url\":\"/test/external\",\"redirect\":\"\",\"perms\":\"test:external:list\",\"typeDict\":\"1\",\"pagePath\":null,\"icon\":\"circle-small\",\"abbr\":\"WB\",\"remark\":\"\",\"visible\":true,\"createAt\":\"1641202347000\",\"updateAt\":\"1641202375000\"},\"bz_code\":1}', '0', '', '2022-01-03 17:32:55');
INSERT INTO `sys_oper_log` VALUES ('1478038183747780610', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: darius-bashar-WSYpuOuPoF4-unsplash.jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220104/darius-bashar-WSYpuOuPoF4-unsplash_fd480806.jpg\"],\"bz_code\":1}', '0', '', '2022-01-04 00:18:58');
INSERT INTO `sys_oper_log` VALUES ('1479962189421907970', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: marco-xu-UlCLN7s-nkY-unsplash.jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220109/marco-xu-UlCLN7s-nkY-unsplash_fd418632.jpg\"],\"bz_code\":1}', '0', '', '2022-01-09 07:44:17');
INSERT INTO `sys_oper_log` VALUES ('1479962205486092290', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: sean-kong-1dJmkdVuelg-unsplash.jpg', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220109/sean-kong-1dJmkdVuelg-unsplash_fd023991.jpg\"],\"bz_code\":1}', '0', '', '2022-01-09 07:44:21');
INSERT INTO `sys_oper_log` VALUES ('1480065605682892802', '新增系统配置', 'net.foundi.admin.system.controller.ConfigController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/config', '192.168.2.8', '内网IP', '{dto=ConfigDto(id=null, configTypeDict=0, configKey=33, configValue=2222\n4444\n555\n6666, enabled=null, remark=sdfsfsfsf, createAt=null, test1=null, test2=null, test3=null, test4=null)}', 'null', '1', '', '2022-01-09 14:35:13');
INSERT INTO `sys_oper_log` VALUES ('1480065823195303937', '新增系统配置', 'net.foundi.admin.system.controller.ConfigController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/config', '192.168.2.8', '内网IP', '{dto=ConfigDto(id=null, configTypeDict=0, configKey=333, configValue=3333, enabled=null, remark=, createAt=null, test1=null, test2=null, test3=null, test4=null)}', 'null', '1', '', '2022-01-09 14:36:05');
INSERT INTO `sys_oper_log` VALUES ('1480066272937914369', '新增系统配置', 'net.foundi.admin.system.controller.ConfigController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/config', '192.168.2.8', '内网IP', '{dto=ConfigDto(id=null, configTypeDict=0, configKey=4444, configValue=444\n4\n4\n\n\n5\n555, enabled=null, remark=fff, createAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480066272250048513\",\"configTypeDict\":\"0\",\"configKey\":\"4444\",\"configValue\":\"444\\n4\\n4\\n\\n\\n5\\n555\",\"enabled\":false,\"remark\":\"fff\",\"createAt\":\"1641710272000\"},\"bz_code\":1}', '0', '', '2022-01-09 14:37:52');
INSERT INTO `sys_oper_log` VALUES ('1480066293640998914', '删除系统配置', 'net.foundi.admin.system.controller.ConfigController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/system/config/1480066272250048513', '192.168.2.8', '内网IP', '{ids=[1480066272250048513]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-09 14:37:57');
INSERT INTO `sys_oper_log` VALUES ('1480066505776312321', '新增系统配置', 'net.foundi.admin.system.controller.ConfigController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/config', '192.168.2.8', '内网IP', '{dto=ConfigDto(id=null, configTypeDict=0, configKey=444, configValue=44, enabled=null, remark=, createAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480066505486905345\",\"configTypeDict\":\"0\",\"configKey\":\"444\",\"configValue\":\"44\",\"enabled\":false,\"remark\":\"\",\"createAt\":\"1641710328000\"},\"bz_code\":1}', '0', '', '2022-01-09 14:38:48');
INSERT INTO `sys_oper_log` VALUES ('1480066522348007425', '删除系统配置', 'net.foundi.admin.system.controller.ConfigController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/system/config/1480066505486905345', '192.168.2.8', '内网IP', '{ids=[1480066505486905345]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-09 14:38:52');
INSERT INTO `sys_oper_log` VALUES ('1480066838837604354', '新增系统配置', 'net.foundi.admin.system.controller.ConfigController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/config', '192.168.2.8', '内网IP', '{dto=ConfigDto(id=null, configTypeDict=0, configKey=hh, configValue=hh, enabled=null, remark=, createAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480066837843554305\",\"configTypeDict\":\"0\",\"configKey\":\"hh\",\"configValue\":\"hh\",\"enabled\":false,\"remark\":\"\",\"createAt\":\"1641710407000\"},\"bz_code\":1}', '0', '', '2022-01-09 14:40:07');
INSERT INTO `sys_oper_log` VALUES ('1480066859276447745', '删除系统配置', 'net.foundi.admin.system.controller.ConfigController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/system/config/1480066837843554305', '192.168.2.8', '内网IP', '{ids=[1480066837843554305]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-09 14:40:12');
INSERT INTO `sys_oper_log` VALUES ('1480066900883943426', '新增系统配置', 'net.foundi.admin.system.controller.ConfigController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/config', '192.168.2.8', '内网IP', '{dto=ConfigDto(id=null, configTypeDict=0, configKey=bb, configValue=bb, enabled=null, remark=, createAt=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480066900816834562\",\"configTypeDict\":\"0\",\"configKey\":\"bb\",\"configValue\":\"bb\",\"enabled\":false,\"remark\":\"\",\"createAt\":\"1641710422000\"},\"bz_code\":1}', '0', '', '2022-01-09 14:40:22');
INSERT INTO `sys_oper_log` VALUES ('1480066925626142721', '修改系统配置', 'net.foundi.admin.system.controller.ConfigController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/config', '192.168.2.8', '内网IP', '{dto=ConfigDto(id=1480066900816834562, configTypeDict=0, configKey=bb, configValue=bb\n\nvv, enabled=false, remark=, createAt=2022-01-09T14:40:22)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480066900816834562\",\"configTypeDict\":\"0\",\"configKey\":\"bb\",\"configValue\":\"bb\\n\\nvv\",\"enabled\":false,\"remark\":\"\",\"createAt\":\"1641710422000\"},\"bz_code\":1}', '0', '', '2022-01-09 14:40:28');
INSERT INTO `sys_oper_log` VALUES ('1480066941300256770', '删除系统配置', 'net.foundi.admin.system.controller.ConfigController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/system/config/1480066900816834562', '192.168.2.8', '内网IP', '{ids=[1480066900816834562]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-09 14:40:32');
INSERT INTO `sys_oper_log` VALUES ('1480566587286106114', '新增系统字典', 'net.foundi.admin.system.controller.DictController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/dict', '192.168.2.8', '内网IP', '{dto=DictDto(id=null, name=123, nameCn=222, remarks=, createBy=null, createAt=null, updateBy=null, updateAt=null, delFlag=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480566587084779522\",\"name\":\"123\",\"nameCn\":\"222\",\"remarks\":\"\",\"createBy\":\"1\",\"createAt\":\"1641829557000\",\"updateBy\":null,\"updateAt\":null,\"delFlag\":false},\"bz_code\":1}', '0', '', '2022-01-10 23:45:57');
INSERT INTO `sys_oper_log` VALUES ('1480566655531626498', '新增系统字典条目', 'net.foundi.admin.system.controller.DictItemController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/dictItem', '192.168.2.8', '内网IP', '{dto=DictItemDto(id=null, sort=0, itemKey=22, itemValue=22, dictId=1480566587084779522, remarks=, createBy=null, createAt=null, updateBy=null, updateAt=null, delFlag=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480566655334494210\",\"sort\":0,\"itemKey\":\"22\",\"itemValue\":\"22\",\"dictId\":\"1480566587084779522\",\"remarks\":\"\",\"createBy\":\"1\",\"createAt\":\"1641829573000\",\"updateBy\":null,\"updateAt\":null,\"delFlag\":false},\"bz_code\":1}', '0', '', '2022-01-10 23:46:13');
INSERT INTO `sys_oper_log` VALUES ('1480566672879267841', '新增系统字典条目', 'net.foundi.admin.system.controller.DictItemController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/dictItem', '192.168.2.8', '内网IP', '{dto=DictItemDto(id=null, sort=0, itemKey=33, itemValue=33, dictId=1480566587084779522, remarks=, createBy=null, createAt=null, updateBy=null, updateAt=null, delFlag=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480566672682135553\",\"sort\":0,\"itemKey\":\"33\",\"itemValue\":\"33\",\"dictId\":\"1480566587084779522\",\"remarks\":\"\",\"createBy\":\"1\",\"createAt\":\"1641829577000\",\"updateBy\":null,\"updateAt\":null,\"delFlag\":false},\"bz_code\":1}', '0', '', '2022-01-10 23:46:17');
INSERT INTO `sys_oper_log` VALUES ('1480566688708571137', '新增系统字典条目', 'net.foundi.admin.system.controller.DictItemController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/dictItem', '192.168.2.8', '内网IP', '{dto=DictItemDto(id=null, sort=0, itemKey=44, itemValue=44, dictId=1480566587084779522, remarks=, createBy=null, createAt=null, updateBy=null, updateAt=null, delFlag=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480566688574353410\",\"sort\":0,\"itemKey\":\"44\",\"itemValue\":\"44\",\"dictId\":\"1480566587084779522\",\"remarks\":\"\",\"createBy\":\"1\",\"createAt\":\"1641829581000\",\"updateBy\":null,\"updateAt\":null,\"delFlag\":false},\"bz_code\":1}', '0', '', '2022-01-10 23:46:21');
INSERT INTO `sys_oper_log` VALUES ('1480566792395960321', '修改系统字典', 'net.foundi.admin.system.controller.DictController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/dict', '192.168.2.8', '内网IP', '{dto=DictDto(id=1480566587084779522, name=123, nameCn=2225, remarks=, createBy=1, createAt=2022-01-10T23:45:57, updateBy=null, updateAt=null, delFlag=false)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480566587084779522\",\"name\":\"123\",\"nameCn\":\"2225\",\"remarks\":\"\",\"createBy\":\"1\",\"createAt\":\"1641829557000\",\"updateBy\":\"1\",\"updateAt\":\"1641829606000\",\"delFlag\":false},\"bz_code\":1}', '0', '', '2022-01-10 23:46:46');
INSERT INTO `sys_oper_log` VALUES ('1480566929272877058', '删除系统字典条目', 'net.foundi.admin.system.controller.DictItemController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/system/dictItem/1480566655334494210,1480566672682135553,1480566688574353410', '192.168.2.8', '内网IP', '{ids=[1480566655334494210, 1480566672682135553, 1480566688574353410]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-10 23:47:18');
INSERT INTO `sys_oper_log` VALUES ('1480567053164228609', '删除系统字典', 'net.foundi.admin.system.controller.DictController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/system/dict/1480566587084779522', '192.168.2.8', '内网IP', '{ids=[1480566587084779522]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-10 23:47:48');
INSERT INTO `sys_oper_log` VALUES ('1480789785239891969', '新增系统字典条目', 'net.foundi.admin.system.controller.DictItemController.add()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/dictItem', '192.168.2.8', '内网IP', '{dto=DictItemDto(id=null, sort=0, itemKey=22, itemValue=22, dictId=600, remarks=, createBy=null, createAt=null, updateBy=null, updateAt=null, delFlag=null)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480789785105674241\",\"sort\":0,\"itemKey\":\"22\",\"itemValue\":\"22\",\"dictId\":\"600\",\"remarks\":\"\",\"createBy\":\"1\",\"createAt\":\"1641882771000\",\"updateBy\":null,\"updateAt\":null,\"delFlag\":false},\"bz_code\":1}', '0', '', '2022-01-11 14:32:51');
INSERT INTO `sys_oper_log` VALUES ('1480789806639230978', '修改系统字典条目', 'net.foundi.admin.system.controller.DictItemController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/dictItem', '192.168.2.8', '内网IP', '{dto=DictItemDto(id=1480789785105674241, sort=0, itemKey=22, itemValue=2233, dictId=600, remarks=, createBy=1, createAt=2022-01-11T14:32:51, updateBy=null, updateAt=null, delFlag=false)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1480789785105674241\",\"sort\":0,\"itemKey\":\"22\",\"itemValue\":\"2233\",\"dictId\":\"600\",\"remarks\":\"\",\"createBy\":\"1\",\"createAt\":\"1641882771000\",\"updateBy\":\"1\",\"updateAt\":\"1641882776000\",\"delFlag\":false},\"bz_code\":1}', '0', '', '2022-01-11 14:32:56');
INSERT INTO `sys_oper_log` VALUES ('1480789819196977153', '删除系统字典条目', 'net.foundi.admin.system.controller.DictItemController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/system/dictItem/1480789785105674241', '192.168.2.8', '内网IP', '{ids=[1480789785105674241]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-11 14:32:59');
INSERT INTO `sys_oper_log` VALUES ('1480801796338257921', '删除业务表', 'net.foundi.admin.generator.controller.GenTableController.deleteMulti()', 'DELETE', '1', 'admin', '超级管理员,系统管理员', '', '/generator/genTable/1387749196181897218,1387749196450332674,1387749196517441538,1387749196601327617,1387749196689408001,1387749196739739649,1387749196802654210,1387749196886540289,1387749196928483330,1387749197041729538,1387749197100449793,1387749197171752961,1387749197238861826,1387749197297582081,1387749197394051074,1392737836943290370', '192.168.2.8', '内网IP', '{ids=[1387749196181897218, 1387749196450332674, 1387749196517441538, 1387749196601327617, 1387749196689408001, 1387749196739739649, 1387749196802654210, 1387749196886540289, 1387749196928483330, 1387749197041729538, 1387749197100449793, 1387749197171752961, 1387749197238861826, 1387749197297582081, 1387749197394051074, 1392737836943290370]}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-11 15:20:35');
INSERT INTO `sys_oper_log` VALUES ('1480921813935493121', '文件操作', 'net.foundi.admin.system.controller.FileController.upload()', 'POST', '1', 'admin', '超级管理员,系统管理员', '', '/system/file/upload', '192.168.2.8', '内网IP', '上传文件: ali-pazani-RNHujsmCOqo-unsplash.png', '{\"msg\":[\"成功\"],\"content\":[\"/upload/20220111/ali-pazani-RNHujsmCOqo-unsplash_fd039447.png\"],\"bz_code\":1}', '0', '', '2022-01-11 23:17:29');
INSERT INTO `sys_oper_log` VALUES ('1480921814799519745', '修改当前用户头像', 'net.foundi.admin.system.controller.UserController.CurrentUserChangeAvatar()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/user/current/avatar', '192.168.2.8', '内网IP', '{avatar=/upload/20220111/ali-pazani-RNHujsmCOqo-unsplash_fd039447.png}', '{\"msg\":[\"成功\"],\"bz_code\":1}', '0', '', '2022-01-11 23:17:29');
INSERT INTO `sys_oper_log` VALUES ('1480925923652964354', '修改系统菜单', 'net.foundi.admin.system.controller.MenuController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/menu', '192.168.2.8', '内网IP', '{dto=MenuDto(id=1364834446406103047, parentId=1364834446406103042, name=预览, url=/generator/preview/:id, redirect=null, perms=generator:genTable:get, typeDict=2, pagePath=/generator/preview, icon=preview-open, abbr=null, sort=0, remark=null, visible=true, createAt=2022-01-11T23:32:14, updateAt=2022-01-11T23:32:19)}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1364834446406103047\",\"parentId\":\"1364834446406103042\",\"parentPath\":null,\"sort\":0,\"level\":null,\"children\":null,\"name\":\"预览\",\"url\":\"/generator/preview/:id\",\"redirect\":null,\"perms\":\"generator:genTable:get\",\"typeDict\":\"2\",\"pagePath\":\"/generator/preview\",\"icon\":\"preview-open\",\"abbr\":null,\"remark\":null,\"visible\":true,\"createAt\":\"1641915134000\",\"updateAt\":\"1641915229000\"},\"bz_code\":1}', '0', '', '2022-01-11 23:33:49');
INSERT INTO `sys_oper_log` VALUES ('1480926012253442049', '修改系统角色', 'net.foundi.admin.system.controller.RoleController.edit()', 'PUT', '1', 'admin', '超级管理员,系统管理员', '', '/system/role', '192.168.2.8', '内网IP', '{dto=RoleDto(id=1, name=超级管理员, label=super_admin, remark=超级管理员, dataScopeDict=0, createBy=1, createAt=2020-11-19T14:11:55, updateBy=1, updateAt=2022-01-03T17:32:39, delFlag=false, menuIdList=[10, 11, 20, 1330895557467602946, 1330895557467602947, 1330895557467602948, 1330895557467602949, 1330895557467602950, 1330898330087112706, 1330898330087112707, 1330898330087112708, 1330898330087112709, 1330898330087112710, 1330898330930167809, 1330898330930167810, 1330898330930167811, 1330898330930167812, 1330898330930167813, 1330898329399246850, 1330898329399246851, 1330898329399246852, 1330898329399246853, 1330898329399246854, 1423791489158344706, 1423791489158344707, 1423791489158344708, 1423791489158344709, 1423791489158344710, 1330898331173437442, 1330898331173437443, 1330898331173437444, 1330898331173437445, 1330898331173437446, 1330898329785122817, 1330898329785122818, 1330898329785122819, 1330898329785122820, 1330898329785122821, 1330898330233913345, 1330898330233913346, 1330898330233913348, 1330898330233913349, 1330898330233913347, 1390339339984146433, 1390339339984146434, 1390339339984146435, 1390339339984146436, 1390339339984146437, 1330898331462844418, 1330898331462844419, 1330898331462844420, 1330898331462844421, 1330898331462844422, 1330898330678509570, 1330898330678509571, 1330898330678509572, 1330898330678509573, 1330898330678509574, 1330898330795950081, 1330898330795950082, 1330898330795950083, 1330898330795950084, 1330898330795950085, 1388163373723598849, 1388163373723598850, 1388163373723598851, 1388163373723598852, 1388163373723598853, 30, 1364834446406103042, 1364834446406103043, 1364834446406103044, 1364834446406103045, 1364834446406103046, 1364834446406103047, 40, 1469200281613914114, 100, 1477483348354924546, 1477935879090601985, 1477900255235145729], groupIdList=[10, 20, 30, 1419183541404688386])}', '{\"msg\":[\"成功\"],\"content\":{\"id\":\"1\",\"name\":\"超级管理员\",\"label\":\"super_admin\",\"remark\":\"超级管理员\",\"dataScopeDict\":\"0\",\"createBy\":\"1\",\"createAt\":\"1605766315000\",\"updateBy\":\"1\",\"updateAt\":\"1641915250000\",\"delFlag\":false,\"menuIdList\":[\"10\",\"11\",\"20\",\"1330895557467602946\",\"1330895557467602947\",\"1330895557467602948\",\"1330895557467602949\",\"1330895557467602950\",\"1330898330087112706\",\"1330898330087112707\",\"1330898330087112708\",\"1330898330087112709\",\"1330898330087112710\",\"1330898330930167809\",\"1330898330930167810\",\"1330898330930167811\",\"1330898330930167812\",\"1330898330930167813\",\"1330898329399246850\",\"1330898329399246851\",\"1330898329399246852\",\"1330898329399246853\",\"1330898329399246854\",\"1423791489158344706\",\"1423791489158344707\",\"1423791489158344708\",\"1423791489158344709\",\"1423791489158344710\",\"1330898331173437442\",\"1330898331173437443\",\"1330898331173437444\",\"1330898331173437445\",\"1330898331173437446\",\"1330898329785122817\",\"1330898329785122818\",\"1330898329785122819\",\"1330898329785122820\",\"1330898329785122821\",\"1330898330233913345\",\"1330898330233913346\",\"1330898330233913348\",\"1330898330233913349\",\"1330898330233913347\",\"1390339339984146433\",\"1390339339984146434\",\"1390339339984146435\",\"1390339339984146436\",\"1390339339984146437\",\"1330898331462844418\",\"1330898331462844419\",\"1330898331462844420\",\"1330898331462844421\",\"1330898331462844422\",\"1330898330678509570\",\"1330898330678509571\",\"1330898330678509572\",\"1330898330678509573\",\"1330898330678509574\",\"1330898330795950081\",\"1330898330795950082\",\"1330898330795950083\",\"1330898330795950084\",\"1330898330795950085\",\"1388163373723598849\",\"1388163373723598850\",\"1388163373723598851\",\"1388163373723598852\",\"1388163373723598853\",\"30\",\"1364834446406103042\",\"1364834446406103043\",\"1364834446406103044\",\"1364834446406103045\",\"1364834446406103046\",\"40\",\"1469200281613914114\",\"100\",\"1477483348354924546\",\"1477935879090601985\",\"1477900255235145729\"],\"groupIdList\":[\"10\",\"20\",\"30\",\"1419183541404688386\"]},\"bz_code\":1}', '0', '', '2022-01-11 23:34:10');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `label` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `data_scope_dict` varchar(255) DEFAULT '4' COMMENT '数据范围（字典：SysRoleDataScope）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改用户id',
  `update_at` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '是否删除  1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'super_admin', '超级管理员', '0', '1', '2020-11-19 14:11:55', '1', '2022-01-11 23:34:10', '\0');
INSERT INTO `sys_role` VALUES ('10', '系统管理员', 'admin', '系统管理员', '3', '1', '2020-11-19 14:12:55', '1', '2021-08-03 07:47:50', '\0');
INSERT INTO `sys_role` VALUES ('100', '用户', 'user', '用户', '4', '1', '2020-11-26 23:03:25', '1', '2021-08-04 07:54:51', '\0');
INSERT INTO `sys_role` VALUES ('1422500776445493249', 'APP用户', 'app_user', 'App用户，没有后台权限', '4', '1', '2021-08-03 18:13:09', '1', '2021-08-03 18:13:18', '\0');

-- ----------------------------
-- Table structure for sys_role_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_group`;
CREATE TABLE `sys_role_group` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `group_id` bigint(20) DEFAULT NULL COMMENT '用户组ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色与用户组对应关系';

-- ----------------------------
-- Records of sys_role_group
-- ----------------------------
INSERT INTO `sys_role_group` VALUES ('1422343413226815489', '10', '20');
INSERT INTO `sys_role_group` VALUES ('1422500814420721665', '1422500776445493249', '30');
INSERT INTO `sys_role_group` VALUES ('1422707565766176770', '100', '10');
INSERT INTO `sys_role_group` VALUES ('1422707565766176771', '100', '20');
INSERT INTO `sys_role_group` VALUES ('1422707565766176772', '100', '30');
INSERT INTO `sys_role_group` VALUES ('1422707565766176773', '100', '1419183541404688386');
INSERT INTO `sys_role_group` VALUES ('1480926012136001537', '1', '10');
INSERT INTO `sys_role_group` VALUES ('1480926012136001538', '1', '20');
INSERT INTO `sys_role_group` VALUES ('1480926012136001539', '1', '30');
INSERT INTO `sys_role_group` VALUES ('1480926012136001540', '1', '1419183541404688386');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1422343413210038274', '10', '1330895557467602950');
INSERT INTO `sys_role_menu` VALUES ('1422343413210038275', '10', '20');
INSERT INTO `sys_role_menu` VALUES ('1422343413210038276', '10', '1330895557467602946');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622338', '100', '10');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622339', '100', '20');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622340', '100', '1330895557467602946');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622341', '100', '1330895557467602947');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622342', '100', '1330895557467602948');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622343', '100', '1330895557467602949');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622344', '100', '1330895557467602950');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622345', '100', '1330898330087112706');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622346', '100', '1330898330087112707');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622347', '100', '1330898330087112708');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622348', '100', '1330898330087112709');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622349', '100', '1330898330087112710');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622350', '100', '1330898330930167809');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622351', '100', '1330898330930167810');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622352', '100', '1330898330930167811');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622353', '100', '1330898330930167812');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622354', '100', '1330898330930167813');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622355', '100', '1330898329399246850');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622356', '100', '1330898329399246851');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622357', '100', '1330898329399246852');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622358', '100', '1330898329399246853');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622359', '100', '1330898329399246854');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622360', '100', '1330898331173437442');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622361', '100', '1330898331173437443');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622362', '100', '1330898331173437444');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622363', '100', '1330898331173437445');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622364', '100', '1330898331173437446');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622365', '100', '1330898329785122817');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622366', '100', '1330898329785122818');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622367', '100', '1330898329785122819');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622368', '100', '1330898329785122820');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622369', '100', '1330898329785122821');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622370', '100', '1330898330233913345');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622371', '100', '1330898330233913346');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622372', '100', '1330898330233913348');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622373', '100', '1330898330233913349');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622374', '100', '1330898330233913347');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622375', '100', '1390339339984146433');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622376', '100', '1390339339984146434');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622377', '100', '1390339339984146435');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622378', '100', '1390339339984146436');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622379', '100', '1390339339984146437');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622380', '100', '1330898331462844418');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622381', '100', '1330898331462844419');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622382', '100', '1330898331462844420');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622383', '100', '1330898331462844421');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622384', '100', '1330898331462844422');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622385', '100', '1330898330678509570');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622386', '100', '1330898330678509571');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622387', '100', '1330898330678509572');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622388', '100', '1330898330678509573');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622389', '100', '1330898330678509574');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622390', '100', '1330898330795950081');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622391', '100', '1330898330795950082');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622392', '100', '1330898330795950083');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622393', '100', '1330898330795950084');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622394', '100', '1330898330795950085');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622395', '100', '1388163373723598849');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622396', '100', '1388163373723598850');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622397', '100', '1388163373723598851');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622398', '100', '1388163373723598852');
INSERT INTO `sys_role_menu` VALUES ('1422707565732622399', '100', '1388163373723598853');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309762', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309763', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309764', '1', '20');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309765', '1', '1330895557467602946');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309766', '1', '1330895557467602947');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309767', '1', '1330895557467602948');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309768', '1', '1330895557467602949');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309769', '1', '1330895557467602950');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309770', '1', '1330898330087112706');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309771', '1', '1330898330087112707');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309772', '1', '1330898330087112708');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309773', '1', '1330898330087112709');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309774', '1', '1330898330087112710');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309775', '1', '1330898330930167809');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309776', '1', '1330898330930167810');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309777', '1', '1330898330930167811');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309778', '1', '1330898330930167812');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309779', '1', '1330898330930167813');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309780', '1', '1330898329399246850');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309781', '1', '1330898329399246851');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309782', '1', '1330898329399246852');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309783', '1', '1330898329399246853');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309784', '1', '1330898329399246854');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309785', '1', '1423791489158344706');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309786', '1', '1423791489158344707');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309787', '1', '1423791489158344708');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309788', '1', '1423791489158344709');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309789', '1', '1423791489158344710');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309790', '1', '1330898331173437442');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309791', '1', '1330898331173437443');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309792', '1', '1330898331173437444');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309793', '1', '1330898331173437445');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309794', '1', '1330898331173437446');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309795', '1', '1330898329785122817');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309796', '1', '1330898329785122818');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309797', '1', '1330898329785122819');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309798', '1', '1330898329785122820');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309799', '1', '1330898329785122821');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309800', '1', '1330898330233913345');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309801', '1', '1330898330233913346');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309802', '1', '1330898330233913348');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309803', '1', '1330898330233913349');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309804', '1', '1330898330233913347');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309805', '1', '1390339339984146433');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309806', '1', '1390339339984146434');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309807', '1', '1390339339984146435');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309808', '1', '1390339339984146436');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309809', '1', '1390339339984146437');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309810', '1', '1330898331462844418');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309811', '1', '1330898331462844419');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309812', '1', '1330898331462844420');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309813', '1', '1330898331462844421');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309814', '1', '1330898331462844422');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309815', '1', '1330898330678509570');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309816', '1', '1330898330678509571');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309817', '1', '1330898330678509572');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309818', '1', '1330898330678509573');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309819', '1', '1330898330678509574');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309820', '1', '1330898330795950081');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309821', '1', '1330898330795950082');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309822', '1', '1330898330795950083');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309823', '1', '1330898330795950084');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309824', '1', '1330898330795950085');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309825', '1', '1388163373723598849');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309826', '1', '1388163373723598850');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309827', '1', '1388163373723598851');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309828', '1', '1388163373723598852');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309829', '1', '1388163373723598853');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309830', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309831', '1', '1364834446406103042');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309832', '1', '1364834446406103043');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309833', '1', '1364834446406103044');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309834', '1', '1364834446406103045');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309835', '1', '1364834446406103046');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309836', '1', '1364834446406103047');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309837', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309838', '1', '1469200281613914114');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309839', '1', '100');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309840', '1', '1477483348354924546');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309841', '1', '1477935879090601985');
INSERT INTO `sys_role_menu` VALUES ('1480926012056309842', '1', '1477900255235145729');

-- ----------------------------
-- Table structure for sys_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_task`;
CREATE TABLE `sys_task` (
  `id` bigint(20) NOT NULL,
  `job_name` varchar(255) DEFAULT NULL COMMENT '任务名',
  `job_group` varchar(255) DEFAULT NULL COMMENT '任务分组',
  `job_status` varchar(255) DEFAULT NULL COMMENT '任务状态',
  `is_concurrent` bit(1) DEFAULT b'0' COMMENT '任务是否并发',
  `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron表达式',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `bean_class` varchar(255) DEFAULT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `spring_bean` varchar(255) DEFAULT NULL COMMENT 'Spring bean',
  `method_name` varchar(255) DEFAULT NULL COMMENT '任务调用的方法名',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统任务';

-- ----------------------------
-- Records of sys_task
-- ----------------------------
INSERT INTO `sys_task` VALUES ('1395388928797745153', '测试任务1', '测试', '0', '', '0/10 * * * * ?', null, 'net.foundi.support.task.jobs.TestTask', null, null, '2021-05-20 22:40:20', '1', '2021-12-13 00:56:12', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `group_id` bigint(20) DEFAULT NULL COMMENT '用户组',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `status_dict` varchar(255) DEFAULT '0' COMMENT '状态（字典：SysUserStatus，0：正常，1：禁用）',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `gender_dict` varchar(255) DEFAULT '0' COMMENT '性别（字典：Gender，0：未知，1：男，2：女）',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `address` varchar(500) DEFAULT NULL COMMENT '住址',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '所在城市',
  `district` varchar(255) DEFAULT NULL COMMENT '所在地区',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `update_by` bigint(20) DEFAULT NULL COMMENT '修改者id',
  `update_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`) USING BTREE,
  UNIQUE KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$iBNeuqPrTagJmSXUGD1zqe31a7N48acZk02jQKnmrG6FUstbqFtGO', '13903271895', '20', 'Foundi Admin', '/upload/20220111/ali-pazani-RNHujsmCOqo-unsplash_fd039447.png', '0', 'afeng7882999@163.com', '1', '2021-01-12 00:00:00', '创客基地', '宁夏回族自治区', '吴忠市', '利通区', '2017-08-15 21:40:39', '1', '1', '2022-01-11 23:17:29');
INSERT INTO `sys_user` VALUES ('1416901112715386882', 'user123456', '$2a$10$yQLePb05O/amzc9WRDKQROr2xlHT5L7i1Ve8V.DYP4AdGskfLleXG', '13933161866', '20', 'user123456', '/upload/20211130/religion-3452582_fd657008.png', '0', 'user123456@163.com', '1', '1986-06-13 00:00:00', '家庭住址', '台湾省', '台北市', '大同区', '2021-07-19 07:22:05', '1', '1', '2021-11-30 22:52:38');
INSERT INTO `sys_user` VALUES ('1419178419610050561', 'test123456', '$2a$10$VIUf3JHS.xflqd0bdBMfNei0IviLB7ez/OMbllIkgO1714.OHNOZm', '', '30', '11111166', '/upload/20211130/avatar-2191931_fd984934.png', '0', '', '0', '2021-07-17 00:00:00', '', '香港特别行政区', '東區', '', '2021-07-25 14:11:17', '1', '1', '2021-12-30 14:21:41');
INSERT INTO `sys_user` VALUES ('1453257357319544833', 'admin888', '$2a$10$yvhR9EnhrLQiBefnU1wsGetypKggMRvL.4lRrbdXS7dc.6OgU2x3y', '13933161895', '1419183541404688386', null, '/upload/20211130/5c690f6egy1fywzgcqwr8j20j60j6ab2_fd690080.png', '0', 'afeng78829959@163.com', '0', '2021-12-14 00:00:00', null, '河北省', '唐山市', '路北区', '2021-10-27 15:08:49', null, '1', '2021-12-30 14:21:18');
INSERT INTO `sys_user` VALUES ('1453764972772433922', 'admin77777777', '$2a$10$aMnpOcmZWsT.ga7H11PbBOEyqmo0wzE4jbloMnZ14BOcCqsfJ5SvC', '13866778900', '1419183541404688386', null, '/upload/20211202/avatar-2191931_fd840610.png', '0', 'afeng888@163.com', '0', null, null, null, null, null, '2021-10-29 00:45:54', null, '1', '2021-12-30 14:21:06');
INSERT INTO `sys_user` VALUES ('1464266848898932737', 'afeng7882999', '$2a$10$nvagavzwj7LS/OhvYVJNBuGLwwEhkJTQg/aT6j4CYx1MKjWLV0/kO', '13844556789', '30', null, '/upload/20211202/play-large_fd740294.png', '0', 'afeng7888299@163.com', '0', null, null, null, null, null, '2021-11-27 00:16:36', null, '1', '2021-12-30 14:20:54');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1465695270468591617', '1416901112715386882', '100');
INSERT INTO `sys_user_role` VALUES ('1471874364327616514', '1', '1');
INSERT INTO `sys_user_role` VALUES ('1471874364327616515', '1', '10');
INSERT INTO `sys_user_role` VALUES ('1476438123692019714', '1464266848898932737', '100');
INSERT INTO `sys_user_role` VALUES ('1476438173738455042', '1453764972772433922', '100');
INSERT INTO `sys_user_role` VALUES ('1476438221993922562', '1453257357319544833', '100');
INSERT INTO `sys_user_role` VALUES ('1476438321105326082', '1419178419610050561', '1');
INSERT INTO `sys_user_role` VALUES ('1476438321105326083', '1419178419610050561', '10');
INSERT INTO `sys_user_role` VALUES ('1476438321105326084', '1419178419610050561', '100');
INSERT INTO `sys_user_role` VALUES ('1476438321105326085', '1419178419610050561', '1422500776445493249');
