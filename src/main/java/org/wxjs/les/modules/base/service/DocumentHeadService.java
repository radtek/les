/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.entity.DocumentHead;
import org.wxjs.les.modules.base.dao.DocumentHeadDao;

/**
 * 发文字号Service
 * @author GLQ
 * @version 2019-03-14
 */
@Service
@Transactional(readOnly = true)
public class DocumentHeadService extends CrudService<DocumentHeadDao, DocumentHead> {

	public DocumentHead get(String id) {
		return super.get(id);
	}
	
	public List<DocumentHead> findList(DocumentHead documentHead) {
		return super.findList(documentHead);
	}
	
	public Page<DocumentHead> findPage(Page<DocumentHead> page, DocumentHead documentHead) {
		return super.findPage(page, documentHead);
	}
	
	@Transactional(readOnly = false)
	public void save(DocumentHead documentHead) {
		super.save(documentHead);
	}
	
	@Transactional(readOnly = false)
	public void delete(DocumentHead documentHead) {
		super.delete(documentHead);
	}
	
}