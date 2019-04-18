/*add field to save attachment*/
alter table tsitecheck add `attachment` varchar(500) NULL COMMENT '附件';
alter table tsitecheck add `case_status` varchar(8) NULL COMMENT '事项状态';
alter table tsitecheck add `proc_inst_id` varchar(64) NULL COMMENT '受理流程号';
alter table tsitecheck add `proc_def_id` varchar(64) NULL COMMENT '流程定义号'; 