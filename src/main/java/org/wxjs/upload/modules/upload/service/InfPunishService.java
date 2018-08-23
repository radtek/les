/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.upload.modules.upload.entity.InfPunish;
import org.wxjs.upload.modules.upload.dao.InfPunishDao;

/**
 * inf_punishService
 * @author GLQ
 * @version 2018-08-21
 */
@Service
@Transactional(readOnly = true)
public class InfPunishService extends CrudService<InfPunishDao, InfPunish> {

	public InfPunish get(String id) { 
		return super.get(id);
	}
	
	public List<InfPunish> findList(InfPunish infPunish) {
		return super.findList(infPunish);
	}
	
	public Page<InfPunish> findPage(Page<InfPunish> page, InfPunish infPunish) {
		return super.findPage(page, infPunish);
	}
	
	@Transactional(readOnly = false)
	public void save(InfPunish infPunish) {
		dao.insert(infPunish);
	}
	
	@Transactional(readOnly = false)
	public void delete(InfPunish infPunish) {
		super.delete(infPunish);
	}
	
}