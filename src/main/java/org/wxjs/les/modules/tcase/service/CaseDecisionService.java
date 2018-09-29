/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.base.dao.SequencePoolDao;
import org.wxjs.les.modules.base.entity.SequencePool;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.dao.CaseDecisionDao;

/**
 * 案件决定书Service
 * @author GLQ
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class CaseDecisionService extends CrudService<CaseDecisionDao, CaseDecision> {
	
	@Autowired
	private SequencePoolDao sequencePoolDao;

	public CaseDecision get(String caseId) {
		return super.get(caseId);
	}
	
	public List<CaseDecision> findList(CaseDecision caseDecision) {
		return super.findList(caseDecision);
	}
	
	public Page<CaseDecision> findPage(Page<CaseDecision> page, CaseDecision caseDecision) {
		return super.findPage(page, caseDecision);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseDecision caseDecision) {
		boolean isNew = caseDecision.getIsNewRecord();
		if(isNew){
			caseDecision.setSeq(this.fetchNumber(caseDecision));
		}
		super.save(caseDecision);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseDecision caseDecision) {
		super.delete(caseDecision);
	}
	
	@Transactional(readOnly = false)
	public void recallNumber(CaseDecision caseDecision) {
		dao.recallNumber(caseDecision);
		
		//put the recalled id to pool
		SequencePool sequencePool = new SequencePool();
		sequencePool.setName(caseDecision.getSeqKey());
		sequencePool.setReuseid(caseDecision.getSeq());
		sequencePoolDao.insert(sequencePool);
	}
	
	public synchronized String fetchNumber(CaseDecision caseDecision){
		String seq = "";
		//get from sequecne pool
		SequencePool sequencePoolParam = new SequencePool();
		sequencePoolParam.setName(caseDecision.getSeqKey());
		
		List<SequencePool> sequencePools = sequencePoolDao.findList(sequencePoolParam);
		if(sequencePools.size()>0){
			SequencePool sequencePool = sequencePools.get(0);
			seq = sequencePool.getReuseid();
			sequencePoolDao.delete(sequencePool);
		}
		
		//get new
		if(StringUtils.isEmpty(seq)){
			seq = SequenceUtils.fetchSeq(caseDecision.getSeqKey()) + "";
		}
		return seq;
	}
	
}