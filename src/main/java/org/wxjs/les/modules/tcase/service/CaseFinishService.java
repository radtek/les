/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.tcase.entity.CaseFinish;
import org.wxjs.les.modules.tcase.dao.CaseFinishDao;

/**
 * 案件结束Service
 * @author GLQ
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class CaseFinishService extends CrudService<CaseFinishDao, CaseFinish> {

	public CaseFinish get(String caseId) {
		return super.get(caseId);
	}
	
	public List<CaseFinish> findList(CaseFinish caseFinish) {
		return super.findList(caseFinish);
	}
	
	public Page<CaseFinish> findPage(Page<CaseFinish> page, CaseFinish caseFinish) {
		return super.findPage(page, caseFinish);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseFinish caseFinish) {
		super.save(caseFinish);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseFinish caseFinish) {
		super.delete(caseFinish);
	}
	
}