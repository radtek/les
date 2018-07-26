/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.dao.CaseAttachDao;

/**
 * 案件资料Service
 * @author GLQ
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly = true)
public class CaseAttachService extends CrudService<CaseAttachDao, CaseAttach> {

	public CaseAttach get(String id) {
		return super.get(id);
	}
	
	public List<CaseAttach> findList(CaseAttach caseAttach) {
		return super.findList(caseAttach);
	}
	
	public Page<CaseAttach> findPage(Page<CaseAttach> page, CaseAttach caseAttach) {
		return super.findPage(page, caseAttach);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseAttach caseAttach) {
		super.save(caseAttach);
	}
	
	@Transactional(readOnly = false)
	public void attachTransfer(String oldCaseId, String newCaseId){
		Tcase tcase = new Tcase();
		tcase.setId(newCaseId);
		tcase.setOldCaseId(oldCaseId);
		dao.attachTransfer(tcase);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseAttach caseAttach) {
		super.delete(caseAttach);
	}
	
}