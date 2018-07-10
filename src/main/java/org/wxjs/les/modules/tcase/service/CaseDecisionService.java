/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.dao.CaseDecisionDao;

/**
 * 案件决定书Service
 * @author GLQ
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly = true)
public class CaseDecisionService extends CrudService<CaseDecisionDao, CaseDecision> {

	public CaseDecision get(String id) {
		return super.get(id);
	}
	
	public List<CaseDecision> findList(CaseDecision caseDecision) {
		return super.findList(caseDecision);
	}
	
	public Page<CaseDecision> findPage(Page<CaseDecision> page, CaseDecision caseDecision) {
		return super.findPage(page, caseDecision);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseDecision caseDecision) {
		super.save(caseDecision);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseDecision caseDecision) {
		super.delete(caseDecision);
	}
	
}