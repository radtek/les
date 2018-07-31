/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.dao.CaseHandleDao;

/**
 * 案件审理Service
 * @author GLQ
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class CaseHandleService extends CrudService<CaseHandleDao, CaseHandle> {

	public CaseHandle get(String caseId) {
		return super.get(caseId);
	}
	
	public List<CaseHandle> findList(CaseHandle caseHandle) {
		return super.findList(caseHandle);
	}
	
	public Page<CaseHandle> findPage(Page<CaseHandle> page, CaseHandle caseHandle) {
		return super.findPage(page, caseHandle);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseHandle caseHandle) {
		super.save(caseHandle);
	}
	
	@Transactional(readOnly = false)
	public void saveUploadInfo(CaseHandle entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.updateUploadInfo(entity); 
		}
	}

	@Transactional(readOnly = false)
	public void saveReport(CaseHandle entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.updateReport(entity);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseHandle caseHandle) {
		super.delete(caseHandle);
	}
	
}