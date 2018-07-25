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
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.dao.CaseNotifyDao;

/**
 * 案件告知书Service
 * @author GLQ
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly = true)
public class CaseNotifyService extends CrudService<CaseNotifyDao, CaseNotify> {

	public CaseNotify get(String caseId) {
		return super.get(caseId);
	}
	
	public List<CaseNotify> findList(CaseNotify caseNotify) {
		return super.findList(caseNotify);
	}
	
	public Page<CaseNotify> findPage(Page<CaseNotify> page, CaseNotify caseNotify) {
		return super.findPage(page, caseNotify);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseNotify caseNotify) {
		
		boolean isNew = caseNotify.getIsNewRecord();
		if(isNew){
			caseNotify.setSeq(SequenceUtils.fetchSeq("NotifySeq_"+caseNotify.getNotifyType())+"");
		}
		
		super.save(caseNotify);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseNotify caseNotify) {
		super.delete(caseNotify);
	}
	
}