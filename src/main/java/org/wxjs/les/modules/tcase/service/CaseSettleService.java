/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.tcase.entity.CaseSettle;
import org.wxjs.les.modules.tcase.dao.CaseSettleDao;

/**
 * 案件结案Service
 * @author GLQ
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class CaseSettleService extends CrudService<CaseSettleDao, CaseSettle> {

	public CaseSettle get(String caseId) {
		return super.get(caseId);
	}
	
	public List<CaseSettle> findList(CaseSettle caseSettle) {
		return super.findList(caseSettle);
	}
	
	public Page<CaseSettle> findPage(Page<CaseSettle> page, CaseSettle caseSettle) {
		return super.findPage(page, caseSettle);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseSettle caseSettle) {
		super.save(caseSettle);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseSettle caseSettle) {
		super.delete(caseSettle);
	}
	
}