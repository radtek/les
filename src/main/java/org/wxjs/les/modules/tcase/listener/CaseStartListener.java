package org.wxjs.les.modules.tcase.listener;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wxjs.les.common.config.Global;
 
import org.wxjs.les.common.utils.SpringContextHolder;

import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;


public class CaseStartListener implements ExecutionListener { 
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6508666719956957755L;


	protected Logger logger = LoggerFactory.getLogger(getClass());

	
	@Autowired
	TcaseDao caseDao = SpringContextHolder.getBean(TcaseDao.class);
	
	@Autowired
	CaseProcessDao caseProcessDao = SpringContextHolder.getBean(CaseProcessDao.class);


	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		String businesskey = execution.getProcessBusinessKey();
		
		logger.debug("businesskey:{}", businesskey);
		
		String caseStage = "";
		
		String caseId = "";
		String caseProcessId = "";
		String[] strs = businesskey.split(":");
		if(strs.length>0){
			caseId = strs[0];
			if(strs.length>1){
				caseProcessId = strs[1];
				CaseProcess caseProcess = caseProcessDao.get(caseProcessId);
				caseStage = caseProcess.getCaseStage();
			}
			//update status for case
			Tcase tcase = new Tcase();
			tcase.setId(caseId);
			tcase.setStatus(Global.CASE_STATUS_STARTED+":"+caseStage);
			caseDao.updateStatus(tcase);			
		}
		
	}

}
