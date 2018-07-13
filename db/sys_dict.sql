delete from sys_dict where id like 'party_type_%';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('party_type_1', '单位', '单位', 'party_type', 'party_type', 10, '0','1',now(),'1',now()),
('party_type_2', '个人', '个人', 'party_type', 'party_type', 20, '0','1',now(),'1',now());

delete from sys_dict where id like 'case_stage_%';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_stage_1', '10', '案件来源及受理', 'case_stage', 'case_stage', 10, '0','1',now(),'1',now()),
('case_stage_2', '20', '立案', 'case_stage', 'case_stage', 20, '0','1',now(),'1',now()),
('case_stage_3', '30', '案件审理', 'case_stage', 'case_stage', 30, '0','1',now(),'1',now()),
('case_stage_4', '40', '发告知书', 'case_stage', 'case_stage', 40, '0','1',now(),'1',now()),
('case_stage_5', '50', '发决定书', 'case_stage', 'case_stage', 50, '0','1',now(),'1',now()),
('case_stage_6', '60', '结案书', 'case_stage', 'case_stage', 60, '0','1',now(),'1',now()),
('case_stage_7', '70', '案件结束', 'case_stage', 'case_stage', 70, '0','1',now(),'1',now()),
('case_stage_11', '110', '重大行政处罚', 'case_stage', 'case_stage', 110, '0','1',now(),'1',now()),
('case_stage_12', '120', '案件撤销', 'case_stage', 'case_stage', 120, '0','1',now(),'1',now())

delete from sys_dict where id like 'case_stage_status_%';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('case_stage_status_1', '0', '未启动', 'case_stage_status', 'case_stage_status', 10, '0','1',now(),'1',now()),
('case_stage_status_2', '1', '流转中', 'case_stage_status', 'case_stage_status', 20, '0','1',now(),'1',now()),
('case_stage_status_3', '2', '已办结', 'case_stage_status', 'case_stage_status', 30, '0','1',now(),'1',now()),
('case_stage_status_4', '9', '已终止', 'case_stage_status', 'case_stage_status', 40, '0','1',now(),'1',now());





