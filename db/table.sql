-- ----------------------------
-- Table structure for tcase
-- ----------------------------
DROP TABLE IF EXISTS `tcase`;
CREATE TABLE `tcase` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(100) NOT NULL COMMENT '事项编号',
  `accepter` varchar(32) NOT NULL COMMENT '受理人',
  `accept_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '受理时间',
  `case_source` varchar(100) NOT NULL COMMENT '案件来源',
  `party_type` varchar(32) NOT NULL COMMENT '当事人类型',
  `org_name` varchar(100) NOT NULL COMMENT '名称',
  `org_agent` varchar(32) NOT NULL COMMENT '法定代表人',
  `org_code` varchar(32) NOT NULL COMMENT '统一社会信用代码',
  `org_responsible_person` varchar(100) NOT NULL COMMENT '负责人',
  `org_address` varchar(100) NOT NULL COMMENT '住址',
  `org_phone` varchar(32) NOT NULL COMMENT '联系电话', 
  `psn_name` varchar(32) NOT NULL COMMENT '姓名',
  `psn_organization` varchar(100) NOT NULL COMMENT '工作单位',
  `psn_code` varchar(32) NOT NULL COMMENT '身份证',
  `psn_post` varchar(100) NOT NULL COMMENT '职务',
  `psn_address` varchar(100) NOT NULL COMMENT '住址',
  `psn_phone` varchar(32) NOT NULL COMMENT '联系电话', 
  `psn_sex` varchar(8) NOT NULL COMMENT '性别', 
  `case_happen_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '案发时间',
  `case_happen_address` varchar(100) NOT NULL COMMENT '案发地点',
  `project_code` varchar(32) NOT NULL COMMENT '案件所涉项目代码',
  `project_name` varchar(100) NOT NULL COMMENT '案件所涉项目名称',
  `case_cause` varchar(500) NOT NULL COMMENT '案由',
  `case_summary` varchar(500) NOT NULL COMMENT '案情摘要',
  `case_handler` varchar(64) NOT NULL COMMENT '办案人',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记'
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件';

-- ----------------------------
-- Table structure for tcase_attach
-- ----------------------------
DROP TABLE IF EXISTS `tcase_attach`;
CREATE TABLE `tcase_attach` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `attach_type` varchar(100) NOT NULL COMMENT '资料类型',
  `filename` varchar(100) NOT NULL COMMENT '文件名称',
  `filepath` varchar(300) NOT NULL COMMENT '保存路径',
  `flow_node` varchar(32) NOT NULL COMMENT '流程点',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记'
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件资料';

-- ----------------------------
-- Table structure for tcase_notify
-- ----------------------------
DROP TABLE IF EXISTS `tcase_notify`;
CREATE TABLE `tcase_notify` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `notify_type` varchar(8) NOT NULL COMMENT '告知书类型',
  `year` varchar(8) NOT NULL COMMENT '年份',
  `seq` varchar(8) NOT NULL COMMENT '流水号',
  `party_name` varchar(100) NOT NULL COMMENT '名称',
  `content` varchar(1000) NOT NULL COMMENT '正文',
  `launch_dept` varchar(100) NOT NULL COMMENT '发证部门',
  `launch_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发证时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记'
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件告知书';