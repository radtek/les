package org.wxjs.les.modules.tcase.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.modules.tcase.dao.TcaseDao;

import org.wxjs.les.modules.tcase.entity.Tcase;

public class CaseFinishListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2293194672706571664L;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	TcaseDao caseDao;


	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		
		String businesskey = execution.getProcessBusinessKey();
		
		String caseId = "";
		String[] strs = businesskey.split(":");
		if(strs.length>0){
			
			//update status for case
			Tcase tcase = new Tcase();
			tcase.setId(caseId);
			tcase.setStatus(Global.CASE_STATUS_FINISHED);
			caseDao.updateStatus(tcase);			
		}
		
	}

}
