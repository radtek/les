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
('case_notify_type_1', '1', '锡建监权告字', 'case_notify_type', 'case_notify_type', 20, '0','1',now(),'1',now()),
('case_notify_type_2', '2', '锡建监听告字', 'case_notify_type', 'case_notify_type', 10, '0','1',now(),'1',now());

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
('upload_status_2', '1', '部分上传', 'upload_status', 'upload_status', 20, '0','1',now(),'1',now()),
('upload_status_3', '2', '全部上传', 'upload_status', 'upload_status', 30, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'entity_type';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('entity_type_1', 'sg', '施工单位', 'entity_type', 'entity_type', 10, '0','1',now(),'1',now()),
('entity_type_2', 'kc', '勘察、设计单位', 'entity_type', 'entity_type', 20, '0','1',now(),'1',now()),
('entity_type_3', 'js', '建设单位', 'entity_type', 'entity_type', 30, '0','1',now(),'1',now()),
('entity_type_4', 'jl', '监理单位', 'entity_type', 'entity_type', 40, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'case_source';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_source_1', '100', '检查', 'case_source', 'case_source', 10, '0','1',now(),'1',now()),
('case_source_2', '200', '信用考评', 'case_source', 'case_source', 20, '0','1',now(),'1',now()),
('case_source_3', '300', '转办', 'case_source', 'case_source', 30, '0','1',now(),'1',now()),
('case_source_4', '400', '交办', 'case_source', 'case_source', 40, '0','1',now(),'1',now()),
('case_source_5', '500', '投诉举报', 'case_source', 'case_source', 50, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'punish_type';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('punish_type_1', '100', '文明施工', 'punish_type', 'punish_type', 10, '0','1',now(),'1',now()),
('punish_type_2', '200', '市场行为', 'punish_type', 'punish_type', 20, '0','1',now(),'1',now()),
('punish_type_3', '300', '招投标活动', 'punish_type', 'punish_type', 30, '0','1',now(),'1',now()),
('punish_type_4', '400', '工程质量', 'punish_type', 'punish_type', 40, '0','1',now(),'1',now()),
('punish_type_5', '500', '安全生产', 'punish_type', 'punish_type', 50, '0','1',now(),'1',now()),
('punish_type_7', '700', '其他', 'punish_type', 'punish_type', 70, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'punish_type_sz';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('punish_type_8', '800', '文明施工', 'punish_type_sz', 'punish_type', 10, '0','1',now(),'1',now()),
('punish_type_9', '900', '市场行为', 'punish_type_sz', 'punish_type', 20, '0','1',now(),'1',now()),
('punish_type_10', '1000', '招投标活动', 'punish_type_sz', 'punish_type', 30, '0','1',now(),'1',now()),
('punish_type_11', '1100', '工程质量', 'punish_type_sz', 'punish_type', 40, '0','1',now(),'1',now()),
('punish_type_12', '1200', '工程安全', 'punish_type_sz', 'punish_type', 50, '0','1',now(),'1',now()),
('punish_type_14', '1400', '其他', 'punish_type_sz', 'punish_type', 70, '0','1',now(),'1',now());

delete from sys_dict where `type`= 'project_type';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('project_type_1', 'FJ', '房建', 'project_type', 'project_type', 10, '0','1',now(),'1',now()),
('project_type_2', 'SZ', '市政', 'project_type', 'project_type', 20, '0','1',now(),'1',now()),
('project_type_3', 'KC', '勘察', 'project_type', 'project_type', 30, '0','1',now(),'1',now()),
('project_type_4', 'SJ', '设计', 'project_type', 'project_type', 40, '0','1',now(),'1',now()),
('project_type_9', 'QT', '其他', 'project_type', 'project_type', 90, '0','1',now(),'1',now());





