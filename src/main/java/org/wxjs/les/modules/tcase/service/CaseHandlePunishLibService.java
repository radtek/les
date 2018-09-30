/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.service.PunishLibService;
import org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib;
import org.wxjs.les.modules.tcase.dao.CaseHandlePunishLibDao;

/**
 * 案件裁量权Service
 * @author GLQ
 * @version 2018-07-30
 */
@Service
@Transactional(readOnly = true)
public class CaseHandlePunishLibService extends CrudService<CaseHandlePunishLibDao, CaseHandlePunishLib> {
	
	@Autowired
	private PunishLibService punishLibService;

	public CaseHandlePunishLib get(String id) {
		return super.get(id);
	}
	
	public List<CaseHandlePunishLib> findList(CaseHandlePunishLib caseHandlePunishLib) {
		
		List<CaseHandlePunishLib> list = super.findList(caseHandlePunishLib);
		
		for(CaseHandlePunishLib entity: list){
			entity.setPunishLib(punishLibService.get(entity.getPunishLib().getId()));
		}
		
		return list;
	}
	
	public Page<CaseHandlePunishLib> findPage(Page<CaseHandlePunishLib> page, CaseHandlePunishLib caseHandlePunishLib) {
		return super.findPage(page, caseHandlePunishLib);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseHandlePunishLib caseHandlePunishLib) {
		super.save(caseHandlePunishLib);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseHandlePunishLib caseHandlePunishLib) {
		super.delete(caseHandlePunishLib);
	}
	
	@Transactional(readOnly = false)
	public void deleteByCaseId(String caseId) {
		CaseHandlePunishLib caseHandlePunishLib = new CaseHandlePunishLib();
		caseHandlePunishLib.setCaseId(caseId);
		dao.deleteByCaseId(caseHandlePunishLib);
	}
	
	@Transactional(readOnly = false)
	public void updateRange(CaseHandlePunishLib caseHandlePunishLib) {
		dao.updateRange(caseHandlePunishLib);
	}
	
	
	
}