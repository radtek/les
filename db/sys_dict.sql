delete from sys_dict where `type`= 'party_type';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('party_type_1', '1', '单位', 'party_type', 'party_type', 10, '0','1',now(),'1',now()),
('party_type_2', '0', '个人', 'party_type', 'party_type', 20, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'case_stage';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_stage_1', '10', '案件来源及受理', 'case_stage', 'case_stage', 10, '0','1',now(),'1',now()),
('case_stage_2', '20', '立案', 'case_stage', 'case_stage', 20, '0','1',now(),'1',now()),
('case_stage_3', '30', '案件审理', 'case_stage', 'case_stage', 30, '0','1',now(),'1',now()),
('case_stage_4', '40', '发告知书', 'case_stage', 'case_stage', 40, '0','1',now(),'1',now()),
('case_stage_5', '50', '发决定书', 'case_stage', 'case_stage', 50, '0','1',now(),'1',now()),
('case_stage_6', '60', '结案书', 'case_stage', 'case_stage', 60, '0','1',now(),'1',now()),
('case_stage_7', '70', '案件结束', 'case_stage', 'case_stage', 70, '0','1',now(),'1',now()),
('case_stage_11', '110', '重大行政处罚流程', 'case_stage', 'case_stage', 110, '0','1',now(),'1',now()),
('case_stage_12', '120', '撤销流程', 'case_stage', 'case_stage', 120, '0','1',now(),'1',now()),
('case_stage_21', '210', '案源移交流程', 'case_stage', 'case_stage', 210, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'case_stage_status';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_stage_status_1', '0', '未启动', 'case_stage_status', 'case_stage_status', 10, '0','1',now(),'1',now()),
('case_stage_status_2', '1', '流转中', 'case_stage_status', 'case_stage_status', 20, '0','1',now(),'1',now()),
('case_stage_status_3', '2', '已办结', 'case_stage_status', 'case_stage_status', 30, '0','1',now(),'1',now()),
('case_stage_status_4', '9', '已终止', 'case_stage_status', 'case_stage_status', 40, '0','1',now(),'1',now());

delete from sys_dict where id like 'case_stage_filetype_%';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_stage_filetype_1', '1', '询问笔录', 'case_stage_filetype', 'case_stage_filetype', 10, '0','1',now(),'1',now()),
('case_stage_filetype_2', '2', '告知书', 'case_stage_filetype', 'case_stage_filetype', 20, '0','1',now(),'1',now()),
('case_stage_filetype_3', '3', '决定书', 'case_stage_filetype', 'case_stage_filetype', 30, '0','1',now(),'1',now()),
('case_stage_filetype_4', '4', '企业资料', 'case_stage_filetype', 'case_stage_filetype', 40, '0','1',now(),'1',now());


delete from sys_dict where `type`= 'process_type';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('process_type_1', 'caseAcceptanceProcess', '案件来源及受理', 'process_type', '流程类型', 10, '0','1',now(),'1',now()),
('process_type_2', 'caseInitialProcess', '立案', 'process_type', '流程类型', 20, '0','1',now(),'1',now()),
('process_type_3', 'caseHandleProcess', '案件审理', 'process_type', '流程类型', 30, '0','1',now(),'1',now()),
('process_type_4', 'caseNotifyProcess', '发告知书', 'process_type', '流程类型', 40, '0','1',now(),'1',now()),
('process_type_5', 'caseDecisionProcess', '发决定书', 'process_type', '流程类型', 50, '0','1',now(),'1',now()),
('process_type_6', 'caseSettleProcess', '结案书', 'process_type', '流程类型', 60, '0','1',now(),'1',now()),
('process_type_7', 'caseFinishProcess', '案件结束', 'process_type', '流程类型', 70, '0','1',now(),'1',now()),
('process_type_11', 'caseSeriousProcess', '重大行政处罚流程', 'process_type', '流程类型', 110, '0','1',now(),'1',now()),
('process_type_12', 'caseCancelProcess', '撤销流程', 'process_type', '流程类型', 120, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'case_decision_type';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_decision_type_1', '1', '锡建监罚字', 'case_decision_type', 'case_decision_type', 10, '0','1',now(),'1',now()),
('case_decision_type_2', '2', '锡建监不罚字', 'case_decision_type', 'case_decision_type', 20, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'case_notify_type';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_notify_type_1', '1', '锡建监权告字', 'case_notify_type', 'case_notify_type', 10, '0','1',now(),'1',now()),
('case_notify_type_2', '2', '锡建监听告字', 'case_notify_type', 'case_notify_type', 20, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'sys_user_type';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('sys_user_type_1', '1', '普通用户', 'sys_user_type', '用户类型', 10, '0','1',now(),'1',now()),
('sys_user_type_9', '9', '系统管理', 'sys_user_type', '用户类型', 90, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'upload_method';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('upload_method_1', '1', '自动生成', 'upload_method', '上传方式', 10, '0','1',now(),'1',now()),
('upload_method_2', '2', '手动上传', 'upload_method', '上传方式', 20, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'case_status';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_status_1', '0', '未启动', 'case_status', 'case_status', 10, '0','1',now(),'1',now()),
('case_status_2', '1', '流转中', 'case_status', 'case_status', 20, '0','1',now(),'1',now()),
('case_status_3', '2', '已办结', 'case_status', 'case_status', 30, '0','1',now(),'1',now()),
('case_status_4', '9', '已终止', 'case_status', 'case_status', 40, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'upload_status';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('upload_status_1', '0', '未上传', 'upload_status', 'upload_status', 10, '0','1',now(),'1',now()),
('upload_status_2', '1', '已上传', 'upload_status', 'upload_status', 20, '0','1',now(),'1',now());





