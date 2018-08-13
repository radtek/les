/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.entity.CaseDocType;
import org.wxjs.les.modules.base.dao.CaseDocTypeDao;

/**
 * 文档类型Service
 * @author GLQ
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class CaseDocTypeService extends CrudService<CaseDocTypeDao, CaseDocType> {

	public CaseDocType get(String id) {
		return super.get(id);
	}
	
	public List<CaseDocType> findList(CaseDocType caseDocType) {
		return super.findList(caseDocType);
	}
	
	public Page<CaseDocType> findPage(Page<CaseDocType> page, CaseDocType caseDocType) {
		return super.findPage(page, caseDocType);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseDocType caseDocType) {
		super.save(caseDocType);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseDocType caseDocType) {
		super.delete(caseDocType);
	}
	
}