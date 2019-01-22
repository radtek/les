/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.tcase.entity.DeductionMatter;
import org.wxjs.les.modules.tcase.dao.DeductionMatterDao;

/**
 * 处罚扣分事项Service
 * @author GLQ
 * @version 2019-01-18
 */
@Service
@Transactional(readOnly = true)
public class DeductionMatterService extends CrudService<DeductionMatterDao, DeductionMatter> {

	public DeductionMatter get(String id) {
		return super.get(id);
	}
	
	public List<DeductionMatter> findList(DeductionMatter deductionMatter) {
		return super.findList(deductionMatter);
	}
	
	public Page<DeductionMatter> findPage(Page<DeductionMatter> page, DeductionMatter deductionMatter) {
		return super.findPage(page, deductionMatter);
	}
	
	@Transactional(readOnly = false)
	public void save(DeductionMatter deductionMatter) {
		super.save(deductionMatter);
	}
	
	@Transactional(readOnly = false)
	public void delete(DeductionMatter deductionMatter) {
		super.delete(deductionMatter);
	}
	
}