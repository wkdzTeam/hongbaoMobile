/*
SQLyog Ultimate v11.3 (64 bit)
MySQL - 5.6.28 : Database - hongbao_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hongbao_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

/*Table structure for table `blacklist_info` */

CREATE TABLE `blacklist_info` (
  `id` varchar(64) NOT NULL COMMENT 'id主键',
  `user_no` varchar(64) DEFAULT NULL COMMENT '用户编号',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `c3p0testtable` */

CREATE TABLE `c3p0testtable` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `channel_data` */

CREATE TABLE `channel_data` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `statictics_date` date DEFAULT NULL COMMENT '统计日期',
  `ip` int(10) DEFAULT NULL COMMENT 'ip数',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建日期',
  `income_original` decimal(10,2) DEFAULT NULL COMMENT '收入_原来',
  `income_new` decimal(10,2) DEFAULT NULL COMMENT '收入_现在',
  `channel` varchar(50) DEFAULT NULL COMMENT '渠道ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8mb4 COMMENT='渠道数据表';

/*Table structure for table `channel_user` */

CREATE TABLE `channel_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_loginname` varchar(20) NOT NULL COMMENT '渠道登录名称',
  `channel_password` varchar(50) NOT NULL COMMENT '渠道登录密码',
  `channel_name` varchar(20) NOT NULL COMMENT '名字',
  `phone` varchar(20) DEFAULT NULL COMMENT '座机',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `email` varchar(50) DEFAULT NULL COMMENT 'email',
  `bankname` varchar(50) DEFAULT NULL COMMENT '银行名字，支付宝',
  `banknumber` varchar(50) DEFAULT NULL COMMENT '银行号码，支付宝账号',
  `into_type` int(2) DEFAULT '1' COMMENT '1:cps',
  `into_scale` int(5) DEFAULT NULL COMMENT '分成100百分比',
  `deducted_scale` int(5) DEFAULT '100' COMMENT '扣量比例',
  `status` int(2) DEFAULT '0' COMMENT '0:使用,1:停用',
  `parent_chid` varchar(50) DEFAULT NULL COMMENT '父类渠道id',
  `login_ip` varchar(40) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` bigint(20) DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT '0' COMMENT '是否删除0未删除1删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1041 DEFAULT CHARSET=utf8mb4 COMMENT='渠道信息表';

/*Table structure for table `duihuan_info` */

CREATE TABLE `duihuan_info` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '兑换用户id',
  `user_no` varchar(20) DEFAULT NULL COMMENT '用户编号',
  `user_open_id` varchar(100) DEFAULT NULL COMMENT '用户openid',
  `transfer_message` varchar(200) DEFAULT NULL COMMENT '转账说明',
  `transfer_amount` decimal(12,2) DEFAULT NULL COMMENT '转账金额',
  `transfer_no` varchar(50) DEFAULT NULL COMMENT '转账编号',
  `transfer_time` varchar(50) DEFAULT NULL COMMENT '转账时间',
  `transfer_type` char(1) DEFAULT '1' COMMENT '标示[1:红包佣金2:转盘奖励]',
  `pay_flag` char(1) DEFAULT '1' COMMENT '兑换标记[0：兑换；1：未兑换]',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  `result_message` varchar(255) DEFAULT NULL COMMENT '返回消息',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_del_flag` (`del_flag`),
  KEY `index_pay_flag` (`pay_flag`),
  KEY `index_user_no` (`user_no`),
  KEY `index_transfer_no` (`transfer_no`),
  KEY `index_transfer_amount` (`transfer_amount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='兑换表\r\n';

/*Table structure for table `gen_scheme` */

CREATE TABLE `gen_scheme` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `category` varchar(2000) DEFAULT NULL COMMENT '分类',
  `package_name` varchar(500) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `sub_module_name` varchar(30) DEFAULT NULL COMMENT '生成子模块名',
  `function_name` varchar(500) DEFAULT NULL COMMENT '生成功能名',
  `function_name_simple` varchar(100) DEFAULT NULL COMMENT '生成功能名（简写）',
  `function_author` varchar(100) DEFAULT NULL COMMENT '生成功能作者',
  `gen_table_id` varchar(200) DEFAULT NULL COMMENT '生成表编号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_scheme_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生成方案';

/*Table structure for table `gen_table` */

CREATE TABLE `gen_table` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `class_name` varchar(100) DEFAULT NULL COMMENT '实体类名称',
  `parent_table` varchar(200) DEFAULT NULL COMMENT '关联父表',
  `parent_table_fk` varchar(100) DEFAULT NULL COMMENT '关联父表外键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_table_name` (`name`),
  KEY `gen_table_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务表';

/*Table structure for table `gen_table_column` */

CREATE TABLE `gen_table_column` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `gen_table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `jdbc_type` varchar(100) DEFAULT NULL COMMENT '列的数据类型的字节长度',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键',
  `is_null` char(1) DEFAULT NULL COMMENT '是否可为空',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200) DEFAULT NULL COMMENT '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）',
  `show_type` varchar(200) DEFAULT NULL COMMENT '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）',
  `dict_type` varchar(200) DEFAULT NULL COMMENT '字典类型',
  `settings` varchar(2000) DEFAULT NULL COMMENT '其它设置（扩展字段JSON）',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_table_column_table_id` (`gen_table_id`),
  KEY `gen_table_column_name` (`name`),
  KEY `gen_table_column_sort` (`sort`),
  KEY `gen_table_column_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务表字段';

/*Table structure for table `gen_template` */

CREATE TABLE `gen_template` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `category` varchar(2000) DEFAULT NULL COMMENT '分类',
  `file_path` varchar(500) DEFAULT NULL COMMENT '生成文件路径',
  `file_name` varchar(200) DEFAULT NULL COMMENT '生成文件名',
  `content` text COMMENT '内容',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_template_del_falg` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码模板表';

/*Table structure for table `hongbao_draw_info` */

CREATE TABLE `hongbao_draw_info` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `hongbao_draw_no` varchar(64) DEFAULT NULL COMMENT '红包转盘编号',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `draw_type` varchar(5) DEFAULT NULL COMMENT '转盘类型',
  `amount_type` varchar(5) DEFAULT NULL COMMENT '金额类型',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '投注金额',
  `lucky_num` int(11) DEFAULT NULL COMMENT '幸运号码',
  `lucky_amount` decimal(12,2) DEFAULT NULL COMMENT '开奖金额',
  `open_flag` varchar(5) DEFAULT '0' COMMENT '打开标识',
  `open_date` bigint(20) DEFAULT NULL COMMENT '打开时间',
  `pay_id` varchar(64) DEFAULT NULL COMMENT '支付id',
  `pay_type` char(1) DEFAULT NULL COMMENT '支付类型（1：公众号支付，2：wap支付，3：余额支付）',
  `pay_merchant` varchar(5) DEFAULT NULL COMMENT '支付商家(1：盾行，2：威富通）',
  `pay_url` varchar(500) DEFAULT NULL COMMENT '支付url',
  `pay_token_id` varchar(64) DEFAULT NULL COMMENT '威富通的预支付ID',
  `pay_flag` varchar(5) DEFAULT '0' COMMENT '支付标识',
  `pay_date` bigint(20) DEFAULT NULL COMMENT '支付时间',
  `channel` varchar(50) DEFAULT '0' COMMENT '渠道ID',
  `parent_userid` varchar(64) DEFAULT '0' COMMENT '用户父类ID',
  `import_flag` char(1) DEFAULT NULL COMMENT '导入标识（0：否，1：是）',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`),
  KEY `index_draw_type` (`draw_type`),
  KEY `index_amount_type` (`amount_type`),
  KEY `index_del_flag` (`del_flag`),
  KEY `index_pay_flag` (`pay_flag`),
  KEY `index_open_flag` (`open_flag`),
  KEY `index_user_id` (`user_id`),
  KEY `index_create_date` (`create_date`),
  KEY `index_hongbao_draw_no` (`hongbao_draw_no`),
  KEY `index_channel` (`channel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='红包转盘信息表';

/*Table structure for table `hongbao_info` */

CREATE TABLE `hongbao_info` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `hongbao_no` varchar(64) DEFAULT NULL COMMENT '红包编号',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `amount_type` varchar(5) DEFAULT NULL COMMENT '金额类型',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '投注金额',
  `lucky_amount` decimal(12,2) DEFAULT NULL COMMENT '开奖金额',
  `lucky_amount_list` varchar(255) DEFAULT NULL COMMENT '开奖金额列表',
  `open_flag` varchar(5) DEFAULT '0' COMMENT '打开标识',
  `open_date` bigint(20) DEFAULT NULL COMMENT '打开时间',
  `pay_id` varchar(64) DEFAULT NULL COMMENT '支付id',
  `pay_type` char(1) DEFAULT NULL COMMENT '支付类型（1：公众号支付，2：wap支付，3：余额支付）',
  `pay_merchant` varchar(5) DEFAULT NULL COMMENT '支付商家(1：盾行，2：威富通）',
  `pay_url` varchar(1000) DEFAULT NULL COMMENT '支付url',
  `pay_token_id` varchar(64) DEFAULT NULL COMMENT '威富通的预支付ID',
  `pay_flag` varchar(5) DEFAULT '0' COMMENT '支付标识',
  `pay_date` bigint(20) DEFAULT NULL COMMENT '支付时间',
  `channel` varchar(50) DEFAULT '0' COMMENT '渠道ID',
  `parent_userid` varchar(64) DEFAULT '0' COMMENT '父类ID',
  `import_flag` char(1) DEFAULT NULL COMMENT '导入标识（0：否，1：是）',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='红包信息表';

/*Table structure for table `pay_config` */

CREATE TABLE `pay_config` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `config_name` varchar(50) DEFAULT NULL COMMENT '配置名称',
  `pay_merchant` varchar(5) DEFAULT NULL COMMENT '支付商家（1：盾行，2：威富通）',
  `pay_type` char(1) DEFAULT NULL COMMENT '支付类型（1：公众号支付，2：wap支付，3：余额支付）',
  `app_id` varchar(64) DEFAULT NULL COMMENT '应用ID',
  `app_secret` varchar(64) DEFAULT NULL COMMENT '应用密钥',
  `mch_id` varchar(64) DEFAULT NULL COMMENT '威富通商户号',
  `key` varchar(200) DEFAULT NULL COMMENT '交易密钥',
  `item_no` varchar(50) DEFAULT NULL COMMENT '商品编号',
  `bank_id` varchar(50) DEFAULT NULL COMMENT '银行编号',
  `req_url` varchar(100) DEFAULT NULL COMMENT '请求url',
  `pay_url` varchar(100) DEFAULT NULL COMMENT '支付url',
  `sign_url` varchar(100) DEFAULT NULL COMMENT '签名验证url',
  `weixin_callback_domain` varchar(100) DEFAULT NULL COMMENT '微信回调域名',
  `async_notify_url` varchar(100) DEFAULT NULL COMMENT '异步通知url',
  `sync_notify_url` varchar(100) DEFAULT NULL COMMENT '同步通知url',
  `draw_async_notify_url` varchar(100) DEFAULT NULL COMMENT '转盘异步通知url',
  `draw_sync_notify_url` varchar(100) DEFAULT NULL COMMENT '转盘同步通知url',
  `callback_domain` varchar(50) DEFAULT NULL COMMENT '回调域名',
  `order_prefix` varchar(50) DEFAULT NULL COMMENT '订单前缀',
  `use_flag` char(1) DEFAULT '0' COMMENT '使用标志',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`),
  KEY `index_pay_merchant` (`pay_merchant`),
  KEY `index_mch_id` (`mch_id`),
  KEY `index_app_id` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付配置表';

/*Table structure for table `pay_info` */

CREATE TABLE `pay_info` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `hongbao_id` varchar(64) DEFAULT NULL COMMENT '红包id',
  `trade_code` varchar(64) DEFAULT NULL COMMENT '盾行支付平台流水号',
  `trade_paycode` varchar(64) DEFAULT NULL COMMENT '第三方支付平台流水号',
  `pay_title` varchar(64) DEFAULT NULL COMMENT '支付标题',
  `pay_type` char(1) DEFAULT NULL COMMENT '支付类型（1：公众号支付，2：wap支付，3：余额支付）',
  `pay_merchant` varchar(5) DEFAULT NULL COMMENT '支付商家(1：盾行，2：威富通）',
  `pay_business_type` varchar(5) DEFAULT NULL COMMENT '支付业务类型',
  `mch_id` varchar(64) DEFAULT NULL COMMENT '商户号',
  `user_ip` varchar(50) DEFAULT NULL COMMENT '用户ip',
  `user_city` varchar(100) DEFAULT NULL COMMENT '用户城市',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '金额',
  `import_flag` char(1) DEFAULT NULL COMMENT '导入标识（0：否，1：是）',
  `buckle_flag` int(1) DEFAULT '0' COMMENT '是否扣量:(0：否，1：是)',
  `channel` varchar(50) DEFAULT '0' COMMENT '渠道id',
  `parent_userid` varchar(64) DEFAULT '0' COMMENT '父类ID',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_channel` (`channel`),
  KEY `index_import_flag` (`import_flag`),
  KEY `index_del_flag` (`del_flag`),
  KEY `index_create_date` (`create_date`),
  KEY `index_buckle_flag` (`buckle_flag`),
  KEY `index_hongbao_id` (`hongbao_id`),
  KEY `index_pay_business_type` (`pay_business_type`),
  KEY `index_pay_type` (`pay_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付信息表';

/*Table structure for table `pay_push` */

CREATE TABLE `pay_push` (
  `id` varchar(64) NOT NULL COMMENT '主键id',
  `mch_id` varchar(64) DEFAULT NULL COMMENT '商户号',
  `pay_merchant` varchar(5) DEFAULT NULL COMMENT '支付商家',
  `push_data` varchar(5000) DEFAULT NULL COMMENT '推送报文',
  `pay_flag` varchar(5) DEFAULT NULL COMMENT '支付标识',
  `server_ip` varchar(50) DEFAULT NULL COMMENT '服务器ip',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`),
  KEY `index_mch_id` (`mch_id`),
  KEY `index_pay_merchant` (`pay_merchant`),
  KEY `index_pay_flag` (`pay_flag`),
  KEY `index_del_flag` (`del_flag`),
  KEY `index_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付推送表';

/*Table structure for table `sys_area` */

CREATE TABLE `sys_area` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` char(1) DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` bigint(20) NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` bigint(20) NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`),
  KEY `sys_area_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='区域表';

/*Table structure for table `sys_dict` */

CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序[升序]',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` bigint(20) NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` bigint(20) NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

/*Table structure for table `sys_log` */

CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

/*Table structure for table `sys_mdict` */

CREATE TABLE `sys_mdict` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '编号',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) CHARACTER SET utf8mb4 NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `description` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '创建者',
  `create_date` bigint(20) NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '更新者',
  `update_date` bigint(20) NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_mdict_parent_id` (`parent_id`),
  KEY `sys_mdict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='多级字典表';

/*Table structure for table `sys_menu` */

CREATE TABLE `sys_menu` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '编号',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) CHARACTER SET utf8mb4 NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '链接',
  `target` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '图标',
  `is_show` char(1) CHARACTER SET utf8mb4 NOT NULL COMMENT '是否在菜单中显示',
  `permission` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '创建者',
  `create_date` bigint(20) NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '更新者',
  `update_date` bigint(20) NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Table structure for table `sys_office` */

CREATE TABLE `sys_office` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '编号',
  `parent_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) CHARACTER SET utf8mb4 NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `area_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '归属区域',
  `code` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '区域编码',
  `type` char(1) CHARACTER SET utf8mb4 NOT NULL COMMENT '机构类型',
  `grade` char(1) CHARACTER SET utf8mb4 NOT NULL COMMENT '机构等级',
  `address` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '传真',
  `email` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '邮箱',
  `USEABLE` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '是否启用',
  `PRIMARY_PERSON` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '主负责人',
  `DEPUTY_PERSON` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '副负责人',
  `create_by` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '创建者',
  `create_date` bigint(20) NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '更新者',
  `update_date` bigint(20) NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_del_flag` (`del_flag`),
  KEY `sys_office_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构表';

/*Table structure for table `sys_role` */

CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `office_id` varchar(64) DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `enname` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围',
  `is_sys` varchar(64) DEFAULT NULL COMMENT '是否系统数据',
  `useable` varchar(64) DEFAULT NULL COMMENT '是否可用',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` bigint(20) NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` bigint(20) NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`),
  KEY `sys_role_enname` (`enname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Table structure for table `sys_role_menu` */

CREATE TABLE `sys_role_menu` (
  `role_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色编号',
  `menu_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

/*Table structure for table `sys_role_office` */

CREATE TABLE `sys_role_office` (
  `role_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色编号',
  `office_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '机构编号',
  PRIMARY KEY (`role_id`,`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-机构';

/*Table structure for table `sys_serial_number` */

CREATE TABLE `sys_serial_number` (
  `no_name` varchar(50) NOT NULL COMMENT '序号名称',
  `current_value` int(11) NOT NULL DEFAULT '1' COMMENT '当前值',
  `increment` int(11) NOT NULL DEFAULT '1' COMMENT '增量',
  `update_date` bigint(20) NOT NULL COMMENT '更新时间',
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`no_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='序号表';

/*Table structure for table `sys_user` */

CREATE TABLE `sys_user` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '编号',
  `company_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '归属公司',
  `office_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '归属部门',
  `login_name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '登录名',
  `password` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '密码',
  `no` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '工号',
  `name` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '姓名',
  `email` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '手机',
  `user_type` char(1) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '创建者',
  `create_date` bigint(20) NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '更新者',
  `update_date` bigint(20) NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理用户表';

/*Table structure for table `sys_user_role` */

CREATE TABLE `sys_user_role` (
  `user_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理用户-角色';

/*Table structure for table `tousu_info` */

CREATE TABLE `tousu_info` (
  `id` varchar(50) DEFAULT NULL COMMENT 'id',
  `user_no` varchar(20) DEFAULT NULL COMMENT '用户编号',
  `number` varchar(50) DEFAULT NULL COMMENT '微信号/QQ/邮箱',
  `content` varchar(200) DEFAULT NULL COMMENT '投诉内容',
  `type` varchar(2) DEFAULT NULL COMMENT '类型',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) DEFAULT NULL COMMENT '是否删除',
  `status` char(2) DEFAULT '0' COMMENT '状态：0、未处理，1、已处理'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户投诉表';

/*Table structure for table `transfer_info` */

CREATE TABLE `transfer_info` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户id',
  `user_open_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户openid',
  `hongbao_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '红包id',
  `transfer_amount` bigint(20) DEFAULT NULL COMMENT '转账金额',
  `transfer_message` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '转账消息',
  `transfer_flag` varchar(1) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '转账标识',
  `result_message` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '结果消息',
  `payment_no` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '转账编号',
  `payment_time` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '转账时间',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='转账信息表';

/*Table structure for table `user_image` */

CREATE TABLE `user_image` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户id',
  `file_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '文件名',
  `old_file_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '原文件名',
  `suffix` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '后缀',
  `path` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '路径',
  `qiniu_key` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '七牛云存储key',
  `file_hash` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '文件哈希码',
  `image_type` char(1) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '图片类型（1：图片，2：头像）',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`) USING BTREE,
  KEY `index_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户相册表';

/*Table structure for table `user_info` */

CREATE TABLE `user_info` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键id',
  `user_no` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户编号',
  `balance` decimal(12,2) DEFAULT '0.00' COMMENT '余额',
  `pay_amount` decimal(12,2) DEFAULT '0.00' COMMENT '支付金额',
  `join_item_count` int(11) DEFAULT '0' COMMENT '参加夺宝次数',
  `last_login_time` bigint(20) DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '最后登录ip',
  `channel` varchar(50) CHARACTER SET utf8mb4 DEFAULT '0' COMMENT '渠道ID',
  `import_flag` char(1) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '导入标识（0：否，1：是）',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  `parent_userid` varchar(64) CHARACTER SET utf8mb4 DEFAULT '0' COMMENT '父类ID',
  `open_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL,
  `open_id_2` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL,
  `equipment` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '设备',
  `grade` int(30) DEFAULT '1' COMMENT '等级',
  `yongjin` decimal(12,2) DEFAULT '0.00' COMMENT '佣金',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_no` (`user_no`) USING BTREE,
  KEY `index_del_flag` (`del_flag`) USING BTREE,
  KEY `index_create_date` (`create_date`),
  KEY `index_parent_userid` (`parent_userid`),
  KEY `index_open_id` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Table structure for table `user_oauth` */

CREATE TABLE `user_oauth` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户id',
  `open_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '第三方登录id-开发者',
  `union_id` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '第三方登录id-应用',
  `access_token` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '登录令牌',
  `refresh_token` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '刷新令牌',
  `expires_in` int(11) DEFAULT NULL COMMENT '令牌超时时间(s)',
  `source` char(1) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '注册来源（1：微信，2：QQ，3：微博）',
  `login_num` int(11) DEFAULT NULL COMMENT '登录次数',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '最后修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_open_id` (`open_id`) USING BTREE,
  KEY `index_user_id` (`user_id`),
  KEY `index_del_flag` (`del_flag`),
  KEY `index_create_date` (`create_date`),
  KEY `index_source` (`source`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户第三方登录记录表';

/*Table structure for table `yongjin_info` */

CREATE TABLE `yongjin_info` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `user_no` varchar(20) DEFAULT NULL COMMENT '用户编号',
  `user_id_origin` varchar(64) DEFAULT NULL COMMENT '来源用户id',
  `user_no_origin` varchar(20) DEFAULT NULL COMMENT '来源用户编号',
  `hongbao_no` varchar(64) DEFAULT NULL COMMENT '红包编号',
  `amount` decimal(12,2) DEFAULT NULL COMMENT '佣金金额',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_date` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记[0：正常；1：删除]',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_del_flag` (`del_flag`),
  KEY `index_user_no` (`user_no`),
  KEY `index_user_id_origin` (`user_id_origin`),
  KEY `index_user_no_origin` (`user_no_origin`),
  KEY `index_hongbao_no` (`hongbao_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='佣金表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
