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
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.dao.TcaseDao;

/**
 * 案件Service
 * @author GLQ
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly = true)
public class TcaseService extends CrudService<TcaseDao, Tcase> {

	public Tcase get(String id) {
		return super.get(id);
	}
	
	public List<Tcase> findList(Tcase tcase) {
		return super.findList(tcase);
	}
	
	public Page<Tcase> findPage(Page<Tcase> page, Tcase tcase) {
		return super.findPage(page, tcase);
	}
	
	@Transactional(readOnly = false)
	public void save(Tcase tcase) {
		if(tcase.getIsNewRecord()){
			tcase.setCaseId(SequenceUtils.fetchCaseSeqStr());
			logger.debug("tcase.getCaseId():{}",tcase.getCaseId());
		}
		super.save(tcase);
	}
	
	@Transactional(readOnly = false)
	public void delete(Tcase tcase) {
		super.delete(tcase);
	}
	
}