delete from sys_dict where id like 'party_type_%';
INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`,`create_date`,`update_by`,`update_date`) 
VALUES 
('party_type_1', '单位', '单位', 'party_type', 'party_type', 10, '0','1',now(),'1',now()),
('party_type_2', '个人', '个人', 'party_type', 'party_type', 20, '0','1',now(),'1',now());





