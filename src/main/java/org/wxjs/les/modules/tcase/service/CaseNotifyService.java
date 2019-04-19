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
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.dao.CaseNotifyDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;

/**
 * 案件告知书Service
 * @author GLQ
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly = true)
public class CaseNotifyService extends CrudService<CaseNotifyDao, CaseNotify> {
	
	@Autowired
	private SequencePoolDao sequencePoolDao;
	
	@Autowired
	private TcaseDao tcaseDao;

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
			caseNotify.setSeq(this.fetchNumber(caseNotify));
		}
		
		super.save(caseNotify);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseNotify caseNotify) {
		super.delete(caseNotify);
	}
	
	@Transactional(readOnly = false)
	public void recallNumber(CaseNotify caseNotify) {
		
		//fill handleOrg, areaId
		Tcase tcase = tcaseDao.get(caseNotify.getCaseId());
		caseNotify.setHandleOrg(tcase.getHandleOrg());
		caseNotify.setAreaId(tcase.getAreaId());
		
		dao.recallNumber(caseNotify);
		
		//put the recalled id to pool
		SequencePool sequencePool = new SequencePool();
		sequencePool.setName(caseNotify.getSeqKey());
		sequencePool.setReuseid(caseNotify.getSeq());
		sequencePoolDao.insert(sequencePool);
	}
	
	public synchronized String fetchNumber(CaseNotify caseNotify){
		String seq = "";
		//get from sequecne pool
		
		//fill handleOrg, areaId
		Tcase tcase = tcaseDao.get(caseNotify.getCaseId());
		caseNotify.setHandleOrg(tcase.getHandleOrg());
		caseNotify.setAreaId(tcase.getAreaId());
		
		SequencePool sequencePoolParam = new SequencePool();
		sequencePoolParam.setName(caseNotify.getSeqKey());
		
		List<SequencePool> sequencePools = sequencePoolDao.findList(sequencePoolParam);
		if(sequencePools.size()>0){
			SequencePool sequencePool = sequencePools.get(0);
			seq = sequencePool.getReuseid();
			sequencePoolDao.delete(sequencePool);
		}
		
		//get new
		if(StringUtils.isEmpty(seq)){
			seq = SequenceUtils.fetchSeq(caseNotify.getSeqKey()) + "";
		}
		return seq;
	}
	
}