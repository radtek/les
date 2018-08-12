package org.wxjs.les.modules.tcase.listener;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.utils.SpringContextHolder;
import org.wxjs.les.modules.tcase.dao.TcaseDao;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.service.CaseProcessService;

public class CaseCancelPassListener implements ExecutionListener { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4998216406176706138L;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RuntimeService runtimeService = SpringContextHolder.getBean(RuntimeService.class);
	
	@Autowired
	CaseProcessDao caseProcessDao = SpringContextHolder.getBean(CaseProcessDao.class);
	
	@Autowired
	TcaseDao caseDao = SpringContextHolder.getBean(TcaseDao.class);


	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		String businesskey = execution.getProcessBusinessKey();
		
		String caseId = "";
		String[] strs = businesskey.split(":");
		if(strs.length>0){
			caseId = strs[0];
			CaseProcess param = new CaseProcess();
			param.setCaseId(caseId);
			List<CaseProcess> caseProcessList = caseProcessDao.findList(param);
			
			for(CaseProcess entity : caseProcessList){
				if(Global.CASE_STAGE_CANCEL.equals(entity.getCaseStage())){
					continue;
				}
				if(Global.CASE_STAGE_STATUS_STARTED.equals(entity.getCaseStageStatus())){
					runtimeService.suspendProcessInstanceById(entity.getProcInsId());				
					entity.setCaseStageStatus(Global.CASE_STAGE_STATUS_CANCELED);
					caseProcessDao.updateStageStatus(entity);
					
				}
			}
			
		}
		
		//update status for cancel process
		String procInsId = execution.getProcessInstanceId();
		CaseProcess entity = new CaseProcess();
		entity.setProcInsId(procInsId);
		entity.setCaseStageStatus(Global.CASE_STAGE_STATUS_FINISHED);
		caseProcessDao.updateStageStatus(entity);
		
		//update status for case
		Tcase tcase = new Tcase();
		tcase.setId(caseId);
		tcase.setStatus(Global.CASE_STATUS_CANCELED);
		caseDao.updateStatus(tcase);
		
	}

}
