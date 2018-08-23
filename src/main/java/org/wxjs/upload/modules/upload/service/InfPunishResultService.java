/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.upload.modules.upload.entity.InfPunishResult;
import org.wxjs.upload.modules.upload.dao.InfPunishResultDao;

/**
 * inf_punish_resultService
 * @author GLQ
 * @version 2018-08-21
 */
@Service
@Transactional(readOnly = true)
public class InfPunishResultService extends CrudService<InfPunishResultDao, InfPunishResult> {

	public InfPunishResult get(String id) {
		return super.get(id);
	}
	
	public List<InfPunishResult> findList(InfPunishResult infPunishResult) {
		return super.findList(infPunishResult);
	}
	
	public Page<InfPunishResult> findPage(Page<InfPunishResult> page, InfPunishResult infPunishResult) {
		return super.findPage(page, infPunishResult);
	}
	
	@Transactional(readOnly = false)
	public void save(InfPunishResult infPunishResult) {
		dao.insert(infPunishResult);
	}
	
	@Transactional(readOnly = false)
	public void delete(InfPunishResult infPunishResult) {
		super.delete(infPunishResult);
	}
	
}