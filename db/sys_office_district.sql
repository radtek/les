update `sys_office` set sort='90' where id='05';

INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,address,zip_code,master,phone,fax,email,USEABLE) VALUES ('07', '0', '0,', '区县', '50', '2', '07', '2', '1', null, null, null, null, null, null, '1');

INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,address,zip_code,master,phone,fax,email,USEABLE) VALUES ('320205', '07', '0,07,', '锡山区', '10', '2', '07', '2', '1', null, null, null, null, null, null, '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,address,zip_code,master,phone,fax,email,USEABLE) VALUES ('320206', '07', '0,07,', '惠山区', '20', '2', '07', '2', '1', null, null, null, null, null, null, '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,address,zip_code,master,phone,fax,email,USEABLE) VALUES ('320207', '07', '0,07,', '梁溪区', '30', '2', '07', '2', '1', null, null, null, null, null, null, '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,address,zip_code,master,phone,fax,email,USEABLE) VALUES ('320208', '07', '0,07,', '新吴区', '40', '2', '07', '2', '1', null, null, null, null, null, null, '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,address,zip_code,master,phone,fax,email,USEABLE) VALUES ('320211', '07', '0,07,', '滨湖区', '50', '2', '07', '2', '1', null, null, null, null, null, null, '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,address,zip_code,master,phone,fax,email,USEABLE) VALUES ('320281', '07', '0,07,', '江阴市', '60', '2', '07', '2', '1', null, null, null, null, null, null, '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,address,zip_code,master,phone,fax,email,USEABLE) VALUES ('320282', '07', '0,07,', '宜兴市', '70', '2', '07', '2', '1', null, null, null, null, null, null, '1');

#宜兴
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('32028201', '320282', '0,07,320282,', '宜兴市工程建设监察支队', '10', '320282', '01', '2', '1', '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('3202820101', '32028201', '0,07,320282,32028201,', '队长、书记室', '10', '320282', null, '2', '1', '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('3202820102', '32028201', '0,07,320282,32028201,', '综合科', '20', '320282', null, '2', '1', '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('3202820103', '32028201', '0,07,320282,32028201,', '监督一科', '30', '320282', null, '2', '1', '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('3202820104', '32028201', '0,07,320282,32028201,', '监督二科', '40', '320282', null, '2', '1', '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('3202820105', '32028201', '0,07,320282,32028201,', '审理科', '50', '320282', '01005', '2', '1', '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('32028202', '320282', '0,07,320282,', '建设局机关', '20', '320282', '02', '2', '1', '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('3202820202', '32028202', '0,07,320282,32028202,', '宜兴市建设局机关局领导', '10', '320282', null, '2', '1', '1');
INSERT INTO `sys_office`(id,parent_id,parent_ids,name,sort,area_id,code,type,grade,USEABLE) VALUES ('3202820203', '32028202', '0,07,320282,32028202,', '宜兴市建设局机关政策法规处', '20', '320282', null, '2', '1', '1');