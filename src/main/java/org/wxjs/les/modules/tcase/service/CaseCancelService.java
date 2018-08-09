/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.tcase.entity.CaseCancel;
import org.wxjs.les.modules.tcase.dao.CaseCancelDao;

/**
 * 案件撤销Service
 * @author GLQ
 * @version 2018-08-08
 */
@Service
@Transactional(readOnly = true)
public class CaseCancelService extends CrudService<CaseCancelDao, CaseCancel> {

	public CaseCancel get(String id) {
		return super.get(id);
	}
	
	public List<CaseCancel> findList(CaseCancel caseCancel) {
		return super.findList(caseCancel);
	}
	
	public Page<CaseCancel> findPage(Page<CaseCancel> page, CaseCancel caseCancel) {
		return super.findPage(page, caseCancel);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseCancel caseCancel) {
		super.save(caseCancel);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseCancel caseCancel) {
		super.delete(caseCancel);
	}
	
}