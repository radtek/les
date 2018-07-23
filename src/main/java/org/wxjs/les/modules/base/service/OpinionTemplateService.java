/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.entity.OpinionTemplate;
import org.wxjs.les.modules.base.dao.OpinionTemplateDao;

/**
 * 常用批语Service
 * @author GLQ
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class OpinionTemplateService extends CrudService<OpinionTemplateDao, OpinionTemplate> {

	public OpinionTemplate get(String id) {
		return super.get(id);
	}
	
	public List<OpinionTemplate> findList(OpinionTemplate opinionTemplate) {
		return super.findList(opinionTemplate);
	}
	
	public Page<OpinionTemplate> findPage(Page<OpinionTemplate> page, OpinionTemplate opinionTemplate) {
		return super.findPage(page, opinionTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(OpinionTemplate opinionTemplate) {
		super.save(opinionTemplate);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpinionTemplate opinionTemplate) {
		super.delete(opinionTemplate);
	}
	
}