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

public class CaseFinishListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2293194672706571664L;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	TcaseDao caseDao = SpringContextHolder.getBean(TcaseDao.class);
	@Autowired
	CaseProcessDao caseProcessDao = SpringContextHolder.getBean(CaseProcessDao.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
		
		String businesskey = execution.getProcessBusinessKey();
		
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
				
				if(Global.CASE_STAGE_ACCEPTANCE.equals(caseStage)){
					//no doc to 
				}else if(Global.CASE_STAGE_INITIAL.equals(caseStage)){
					//export 立案审批表
					
				}else if(Global.CASE_STAGE_HANDLE.equals(caseStage)){
					//export 调查报告
					//export 处理审批表
					
				}else if(Global.CASE_STAGE_NOTIFY.equals(caseStage)){
					//export 告知书
					
				}else if(Global.CASE_STAGE_DECISION.equals(caseStage)){
					//export 发文稿
					
					//export 处罚决定书
					
				}else if(Global.CASE_STAGE_SETTLE.equals(caseStage)){
					//export 结案审批表
					
				}else if(Global.CASE_STAGE_FINISH.equals(caseStage)){
					//export 备考表
					
					//update status for case
					Tcase tcase = new Tcase();
					tcase.setId(caseId);
					tcase.setStatus(Global.CASE_STATUS_FINISHED);
					caseDao.updateStatus(tcase);					
				}
			}
			
		}
		
	}

}