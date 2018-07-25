/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.dao.CaseDecisionDao;

/**
 * 案件决定书Service
 * @author GLQ
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class CaseDecisionService extends CrudService<CaseDecisionDao, CaseDecision> {

	public CaseDecision get(String caseId) {
		return super.get(caseId);
	}
	
	public List<CaseDecision> findList(CaseDecision caseDecision) {
		return super.findList(caseDecision);
	}
	
	public Page<CaseDecision> findPage(Page<CaseDecision> page, CaseDecision caseDecision) {
		return super.findPage(page, caseDecision);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseDecision caseDecision) {
		boolean isNew = caseDecision.getIsNewRecord();
		if(isNew){
			caseDecision.setSeq(SequenceUtils.fetchSeq("DecisionSeq_"+caseDecision.getDecisionType())+"");
		}
		super.save(caseDecision);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseDecision caseDecision) {
		super.delete(caseDecision);
	}
	
}