-- ----------------------------
-- Table structure for tsequence
-- ----------------------------

DROP TABLE IF EXISTS tsequence;
CREATE TABLE tsequence
(
  name    varchar(100) COMMENT '关键字',
  nextid  int(11) default 1 COMMENT '编号'
);

-- ----------------------------
-- Table structure for tcase
-- ----------------------------
DROP TABLE IF EXISTS `tcase`;
CREATE TABLE `tcase` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_seq` varchar(100) NOT NULL COMMENT '事项编号',
  `accepter` varchar(32) NULL COMMENT '受理人',
  `accept_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '受理时间',
  `initial_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '立案日期',
  `initial_handler` varchar(32) NULL COMMENT '立案经办人',
  `decision_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '处罚决定日期',
  `settle_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '结案日期',
  `case_source` varchar(100) NULL COMMENT '案件来源',
  `party_type` varchar(32) NOT NULL COMMENT '当事人类型',
  `org_name` varchar(100) NULL COMMENT '名称',
  `org_agent` varchar(32) NULL COMMENT '法定代表人',
  `org_code` varchar(32) NULL COMMENT '统一社会信用代码',
  `org_responsible_person` varchar(100) NULL COMMENT '负责人',
  `org_responsible_person_post` varchar(100) NULL COMMENT '职务',
  `org_address` varchar(100) NULL COMMENT '住址',
  `org_phone` varchar(32) NULL COMMENT '联系电话', 
  `psn_name` varchar(32) NULL COMMENT '姓名',
  `psn_organization` varchar(100) NULL COMMENT '工作单位',
  `psn_code` varchar(32) NULL COMMENT '身份证',
  `psn_birthday` datetime NULL COMMENT '出生年月',
  `psn_post` varchar(100) NULL COMMENT '职务',
  `psn_address` varchar(100) NULL COMMENT '住址',
  `psn_phone` varchar(32) NULL COMMENT '联系电话', 
  `psn_sex` varchar(8) NULL COMMENT '性别', 
  `case_happen_date` varchar(32) NOT NULL COMMENT '案发时间',
  `case_happen_address` varchar(100) NOT NULL COMMENT '案发地点',
  `project_code` varchar(32) NULL COMMENT '案件所涉项目代码',
  `project_name` varchar(100) NOT NULL COMMENT '案件所涉项目名称',
  `case_cause` varchar(200) NOT NULL COMMENT '案由',
  `case_transfer` char(1) DEFAULT '0' COMMENT '是否案源, 1：表示为案源',
  `transfer_case_id` varchar(32) NULL COMMENT '移交后的case_id',
  `status` varchar(32) default '0' COMMENT '状态',
  `upload_status` varchar(32) default '0' COMMENT '上报状态',
  `attach_upload_progress` varchar(32) default '0' COMMENT '附件上传进度',
  `attach_upload_detail` varchar(200) NULL COMMENT '附件上传详情',
  `upload_status_lib4`  varchar(32) default '0' COMMENT '上报四库状态',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件';

-- ----------------------------
-- Table structure for tcase_process
-- ----------------------------
DROP TABLE IF EXISTS `tcase_process`;
CREATE TABLE `tcase_process` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `case_summary` text NULL COMMENT '案情摘要',
  `case_handler` varchar(100) NULL COMMENT '办案人',
  `case_stage` varchar(8) NULL COMMENT '事项类型',
  `case_stage_status` varchar(8) NULL COMMENT '事项类型状态',
  `proc_inst_id` varchar(64) NULL COMMENT '受理流程号',
  `proc_def_id` varchar(64) NULL COMMENT '流程定义号',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件流程';

-- ----------------------------
-- Table structure for tcase_attach
-- ----------------------------
DROP TABLE IF EXISTS `tcase_attach`;
CREATE TABLE `tcase_attach` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `attach_type` varchar(100) NOT NULL COMMENT '资料类型',
  `filename` varchar(100) NULL COMMENT '文件名称',
  `filepath` varchar(300) NULL COMMENT '保存路径',
  `flow_node` varchar(32) NULL COMMENT '流程点',
  `mandatory` varchar(32) default '0' COMMENT '是否必须',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
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
  `content` text NOT NULL COMMENT '正文',
  `launch_dept` varchar(100) NOT NULL COMMENT '发证部门',
  `launch_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发证时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  unique(case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件告知书';

-- ----------------------------
-- Table structure for tcase_decision
-- ----------------------------
DROP TABLE IF EXISTS `tcase_decision`;
CREATE TABLE `tcase_decision` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `decision_type` varchar(8) NOT NULL COMMENT '决定书类型',
  `year` varchar(8) NOT NULL COMMENT '年份',
  `seq` varchar(8) NOT NULL COMMENT '流水号',
  `record_org` varchar(64) NOT NULL COMMENT '备案单位',
  `compile_date` varchar(64) NOT NULL COMMENT '拟稿日期',
  `print_count` varchar(64) NOT NULL COMMENT '印数',
  `destination_address` varchar(100) NULL COMMENT '送达地点',
  `party_name` varchar(100) NOT NULL COMMENT '名称',
  `content` text NOT NULL COMMENT '正文',
  `launch_dept` varchar(100) NOT NULL COMMENT '发证部门',
  `launch_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发证时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  unique(case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件决定书';

-- ----------------------------
-- Table structure for tcase_handle
-- ----------------------------
DROP TABLE IF EXISTS `tcase_handle`;
CREATE TABLE `tcase_handle` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `punish_money` decimal(15,2) NULL COMMENT '实际罚款金额（元）',
  `invest_report` text NULL COMMENT '案件调查报告内容',
  `fact` text NULL COMMENT '案件事实经过及证据',
  `investigator` varchar(64) DEFAULT NULL COMMENT '调查人',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  unique(case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件审理';

-- ----------------------------
-- Table structure for tcase_handle_punish_lib
-- ----------------------------
DROP TABLE IF EXISTS `tcase_handle_punish_lib`;
CREATE TABLE `tcase_handle_punish_lib` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `punish_lib_id` varchar(32) NOT NULL COMMENT '裁量权编号',
  `punish_lib_range_id` varchar(32) NULL COMMENT '裁量基准号',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件裁量权';

-- ----------------------------
-- Table structure for tcase_settle
-- ----------------------------
DROP TABLE IF EXISTS `tcase_settle`;
CREATE TABLE `tcase_settle` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `handle_summary` text NULL COMMENT '案件处理情况',
  `execute_summary` text NULL COMMENT '案件执行情况',
  `review_summary` text NULL COMMENT '案件复议情况',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  unique(case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件结案';

-- ----------------------------
-- Table structure for tcase_finish
-- ----------------------------
DROP TABLE IF EXISTS `tcase_finish`;
CREATE TABLE `tcase_finish` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `total_page` int DEFAULT 0 COMMENT '合计页数',
  `word_page` int DEFAULT 0 COMMENT '文字页数',
  `diagram_page` int DEFAULT 0 COMMENT '图样页数',
  `photo_page` int DEFAULT 0 COMMENT '照片页数',
  `other_page` int DEFAULT 0 COMMENT '其他页数',
  `handle_summary` text NULL COMMENT '说明',
  `finish_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '备考表时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  unique(case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件结束';

-- ----------------------------
-- Table structure for tcase_serious
-- ----------------------------
DROP TABLE IF EXISTS `tcase_serious`;
CREATE TABLE `tcase_serious` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `meeting_date_from` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `meeting_date_to` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `meeting_address` varchar(64) DEFAULT NULL COMMENT '地点',
  `master` varchar(64) DEFAULT NULL COMMENT '主持人',
  `voter` varchar(128) DEFAULT NULL COMMENT '参会人员',
  `voter_add` varchar(128) DEFAULT NULL COMMENT '参会人员补充',
  `attendee` varchar(128) DEFAULT NULL COMMENT '列席人员',
  `attendee_add` varchar(128) DEFAULT NULL COMMENT '列席人员补充',
  `recorder` varchar(64) DEFAULT NULL COMMENT '记录人员',
  `case_summary` text NULL COMMENT '执法机构汇报案情',
  `punish_proposal` text NULL COMMENT '执法机构处罚建议',
  `check_opinion` text NULL COMMENT '审查小组审查意见',
  `meeting_record` text NULL COMMENT '会议记录',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  unique(case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='重大行政处罚';

-- ----------------------------
-- Table structure for tcase_cancel
-- ----------------------------
DROP TABLE IF EXISTS `tcase_cancel`;
CREATE TABLE `tcase_cancel` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_id` varchar(32) NOT NULL COMMENT '案件编号',
  `content` text NULL COMMENT '内容',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  unique(case_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案件撤销';

-- ----------------------------
-- Table structure for tsitecheck
-- ----------------------------
DROP TABLE IF EXISTS `tsitecheck`;
CREATE TABLE `tsitecheck` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `develop_org` varchar(100) NOT NULL COMMENT '建设单位',
  `develop_contact` varchar(32) NOT NULL COMMENT '建设单位联系人',
  `develop_phone` varchar(32) NOT NULL COMMENT '建设单位联系人电话',
  `construction_org` varchar(100) NOT NULL COMMENT '施工单位',
  `construction_contact` varchar(32) NOT NULL COMMENT '施工单位联系人',
  `construction_phone` varchar(32) NOT NULL COMMENT '施工单位联系人电话',
  `project_name` varchar(100) NOT NULL COMMENT '工程名称',
  `project_address` varchar(100) NOT NULL COMMENT '工程地址',
  `site_situation` varchar(500) NOT NULL COMMENT '现场检查工程情况',
  `site_picture` varchar(500) NOT NULL COMMENT '现场踏勘示意图',
  `site_check_result` varchar(500) NOT NULL COMMENT '现场踏勘情况',
  `checker` varchar(32) NOT NULL COMMENT '勘查人',
  `check_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '勘查时间',
  `checker_sig` varchar(64) NULL COMMENT '勘查人签名',
  `party_sig` varchar(64) NULL COMMENT '当事人签名',  
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='现场踏勘';

-- ----------------------------
-- Table structure for tsiterecord
-- ----------------------------
DROP TABLE IF EXISTS `tsiterecord`;
CREATE TABLE `tsiterecord` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `develop_org` varchar(100) NOT NULL COMMENT '建设单位',
  `develop_contact` varchar(32) NOT NULL COMMENT '建设单位联系人',
  `develop_phone` varchar(32) NOT NULL COMMENT '建设单位联系人电话',
  `construction_org` varchar(100) NOT NULL COMMENT '施工单位',
  `construction_contact` varchar(32) NOT NULL COMMENT '施工单位联系人',
  `construction_phone` varchar(32) NOT NULL COMMENT '施工单位联系人电话',
  `project_name` varchar(100) NOT NULL COMMENT '工程名称',
  `project_address` varchar(100) NOT NULL COMMENT '工程地址',
  `site_situation` varchar(500) NOT NULL COMMENT '现场检查工程情况',
  `site_picture` varchar(500) NOT NULL COMMENT '现场踏勘示意图',
  `site_check_result` varchar(500) NOT NULL COMMENT '现场踏勘情况',
  `checker` varchar(32) NOT NULL COMMENT '勘查人',
  `check_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '勘查时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='现场巡查笔录';

-- ----------------------------
-- Table structure for tsite_check_record
-- ----------------------------
DROP TABLE IF EXISTS `tsite_check_record`;
CREATE TABLE `tsite_check_record` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `party_type` varchar(32) NOT NULL COMMENT '当事人类型',
  `org_name` varchar(100) NULL COMMENT '名称',
  `org_agent` varchar(32) NULL COMMENT '法定代表人',
  `org_code` varchar(32) NULL COMMENT '统一社会信用代码',
  `org_responsible_person` varchar(100) NULL COMMENT '负责人',
  `org_responsible_person_post` varchar(100) NULL COMMENT '职务',
  `org_address` varchar(100) NULL COMMENT '住址',
  `org_phone` varchar(32) NULL COMMENT '联系电话', 
  `psn_name` varchar(32) NULL COMMENT '姓名',
  `psn_organization` varchar(100) NULL COMMENT '工作单位',
  `psn_code` varchar(32) NULL COMMENT '身份证',
  `psn_birthday` datetime NULL COMMENT '出生年月',
  `psn_post` varchar(100) NULL COMMENT '职务',
  `psn_address` varchar(100) NULL COMMENT '住址',
  `psn_phone` varchar(32) NULL COMMENT '联系电话', 
  `psn_sex` varchar(8) NULL COMMENT '性别', 
  `site_situation` text NOT NULL COMMENT '现场检查记录情况',
  `site_picture` varchar(500) NOT NULL COMMENT '现场勘察示意图',
  `site_picture_memo` varchar(500) NOT NULL COMMENT '示意图说明',
  `party_sig` varchar(64) NULL COMMENT '当事人签名',
  `witness_sig` varchar(64) NULL COMMENT '见证人签名',  
  `checker_sig` varchar(64) NULL COMMENT '勘查人签名',
  `recorder_sig` varchar(64) NULL COMMENT '记录人签名', 
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='现场检查笔录';

-- ----------------------------
-- Table structure for tquestionanswer
-- ----------------------------
DROP TABLE IF EXISTS `tquestionanswer`;
CREATE TABLE `tquestionanswer` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_cause` varchar(200) NOT NULL COMMENT '案由',  
  `from_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `to_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `location` varchar(100) NOT NULL COMMENT '地点',
  `quizzer` varchar(32) DEFAULT NULL COMMENT '调查询问人',
  `recorder` varchar(32) DEFAULT NULL COMMENT '记录人',
  `answerer` varchar(32) DEFAULT NULL COMMENT '被询问人',
  `answerer_sex` varchar(8) NOT NULL COMMENT '性别', 
  `answerer_code` varchar(32) NOT NULL COMMENT '身份证',
  `answerer_organization` varchar(100) NOT NULL COMMENT '工作单位',
  `answerer_post` varchar(100) NOT NULL COMMENT '职务',
  `answerer_birthday` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '出生年月',
  `answerer_address` varchar(100) NOT NULL COMMENT '住址',
  `answerer_phone` varchar(32) NOT NULL COMMENT '联系电话',   
  `zip_code` varchar(32) NOT NULL COMMENT '邮政编码',
  `qa_content` text DEFAULT NULL COMMENT '问答',
  `q_sig` varchar(64) NOT NULL COMMENT '询问人签名',
  `a_sig` varchar(64) NOT NULL COMMENT '被询问人签名',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='询问笔录';

-- ----------------------------
-- Table structure for tmaterial
-- ----------------------------
DROP TABLE IF EXISTS `tmaterial`;
CREATE TABLE `tmaterial` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `get_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '收集时间',
  `material_type` varchar(32) NOT NULL COMMENT '收集方式',
  `get_location` varchar(100) NOT NULL COMMENT '收集地点',
  `party_sig` varchar(32) DEFAULT NULL COMMENT '当事人签名',
  `getter_sig` varchar(32) DEFAULT NULL COMMENT '收集人签名',
  `material_path` varchar(300) DEFAULT NULL COMMENT '资料路径',
  `material_comment` varchar(500) DEFAULT NULL COMMENT '资料说明',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视听资料';

-- ----------------------------
-- Table structure for tsignature
-- ----------------------------
DROP TABLE IF EXISTS `tsignature`;
CREATE TABLE `tsignature` (
  `id` varchar(64) NOT NULL COMMENT 'uuid',
  `title` varchar(100) NOT NULL COMMENT '头',
  `signature` text NOT NULL COMMENT '签名',
  `proc_inst_id` varchar(64) NULL COMMENT '受理流程号',
  `task_name` varchar(64) DEFAULT NULL COMMENT '任务名称',
  `approve_opinion` varchar(500) DEFAULT NULL COMMENT '审核意见',  
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签名记录';

-- ----------------------------
-- Table structure for tsignature_lib
-- ----------------------------
DROP TABLE IF EXISTS `tsignature_lib`;
CREATE TABLE `tsignature_lib` (
  `id` varchar(64) NOT NULL COMMENT 'uuid',
  `login_name` varchar(32) NOT NULL COMMENT '拥有者',
  `title` varchar(100) NOT NULL COMMENT '头',
  `signature` text NOT NULL COMMENT '签名', 
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE(login_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='签名库';

-- ----------------------------
-- Table structure for topinion_template
-- ----------------------------
DROP TABLE IF EXISTS `topinion_template`;
CREATE TABLE `topinion_template` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `owner` varchar(32) NULL COMMENT '拥有者',
  `opinion` varchar(100) NOT NULL COMMENT '常用批语',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='常用批语';

-- ----------------------------
-- Table structure for tpunish_lib
-- ----------------------------
DROP TABLE IF EXISTS `tpunish_lib`;
CREATE TABLE `tpunish_lib` (
  `id` int(11) NOT NULL auto_increment  COMMENT '流水号',
  `seq` varchar(32) DEFAULT NULL COMMENT '编号',
  `behavior` varchar(200) NULL COMMENT '行为名称',
  `law_basis` text NULL COMMENT '法律依据',
  `punish_type` varchar(200) NULL COMMENT '处罚种类',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='处罚基准库';

-- ----------------------------
-- Table structure for tpunish_lib_range
-- ----------------------------
DROP TABLE IF EXISTS `tpunish_lib_range`;
CREATE TABLE `tpunish_lib_range` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `lib_id` varchar(64) NOT NULL COMMENT '处罚编号',
  `situation` varchar(200) NOT NULL COMMENT '情形描述',
  `punish_range` varchar(200) NOT NULL COMMENT '裁量幅度',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自由裁量权';

-- ----------------------------
-- Table structure for tcase_doc_type
-- ----------------------------
DROP TABLE IF EXISTS `tcase_doc_type`;
CREATE TABLE `tcase_doc_type` (
  `id` int(11) NOT NULL auto_increment  COMMENT '编号',
  `case_stage` varchar(64) NOT NULL COMMENT '阶段',
  `document_type` varchar(64) NOT NULL COMMENT '文档类型',
  `mandatory` varchar(8) NOT NULL COMMENT '是否必须',
  `upload_method` varchar(16) NOT NULL COMMENT '上传方式',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档类型';

alter table sys_user add `allow_load_history_signature` char(1) DEFAULT '0' COMMENT '是否允许从历史库读取签名';
