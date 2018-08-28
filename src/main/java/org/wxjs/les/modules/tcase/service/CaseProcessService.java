/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.base.dao.SignatureDao;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;

/**
 * 案件流程Service
 * @author GLQ
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true)
public class CaseProcessService extends CrudService<CaseProcessDao, CaseProcess> {
	
	@Autowired
	private SignatureDao signatureDao;
	
	@Autowired
	private TcaseDao tcaseDao;

	public CaseProcess get(String id) {
		return super.get(id);
	}
	
	public CaseProcess get(String caseId, String caseStage) {
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseId(caseId);
		caseProcess.setCaseStage(caseStage);
		List<CaseProcess> list = this.findList(caseProcess);
		CaseProcess rst = null;
		if(list!=null && list.size()>0){
			rst = list.get(0);
		}
		return rst;
	}
	
	public List<CaseProcess> findList(CaseProcess caseProcess) {
		return super.findList(caseProcess);
	}
	
	public Page<CaseProcess> findPage(Page<CaseProcess> page, CaseProcess caseProcess) {
		return super.findPage(page, caseProcess);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseProcess caseProcess) {
		super.save(caseProcess);
	}
	
	@Transactional(readOnly = false)
	public void updateSignatureTime(CaseProcess caseProcess){
		Tcase tcase = new Tcase();
		tcase.setId(caseProcess.getCaseId());
		for(Signature signature: caseProcess.getSignatures()){
			signature.setUpdateDate(signature.getCreateDate());
			signatureDao.updateSignatureTime(signature);
			
			if(Global.CASE_STAGE_INITIAL.equals(caseProcess.getCaseStage())){
				if("局领导审核".equals(signature.getTaskName())){
					tcase.setInitialDate(signature.getCreateDate());
					tcaseDao.updateInitialDate(tcase);
				}
			}
			
			if(Global.CASE_STAGE_SETTLE.equals(caseProcess.getCaseStage())){
				if("局领导审核".equals(signature.getTaskName())){
					tcase.setSettleDate(signature.getCreateDate());
					tcaseDao.updateSettleDate(tcase);
				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void updateStageStatus(CaseProcess caseProcess){
		dao.updateStageStatus(caseProcess);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseProcess caseProcess) {
		super.delete(caseProcess);
	}
	
}