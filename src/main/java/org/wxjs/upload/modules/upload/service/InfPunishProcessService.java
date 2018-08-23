/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.upload.modules.upload.entity.InfPunishProcess;
import org.wxjs.upload.modules.upload.dao.InfPunishProcessDao;

/**
 * inf_punish_processService
 * @author GLQ
 * @version 2018-08-21
 */
@Service
@Transactional(readOnly = true)
public class InfPunishProcessService extends CrudService<InfPunishProcessDao, InfPunishProcess> { 

	public InfPunishProcess get(String id) {
		return super.get(id);
	}
	
	public List<InfPunishProcess> findList(InfPunishProcess infPunishProcess) {
		return super.findList(infPunishProcess);
	}
	
	public Page<InfPunishProcess> findPage(Page<InfPunishProcess> page, InfPunishProcess infPunishProcess) {
		return super.findPage(page, infPunishProcess);
	}
	
	@Transactional(readOnly = false)
	public void save(InfPunishProcess infPunishProcess) {
		dao.insert(infPunishProcess);
	}
	
	@Transactional(readOnly = false)
	public void delete(InfPunishProcess infPunishProcess) {
		super.delete(infPunishProcess);
	}
	
	@Transactional(readOnly = false)
	public void deleteRelated(InfPunishProcess infPunishProcess) {
		super.delete(infPunishProcess);
	}
	
}