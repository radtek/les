/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.tcase.dao.CaseAttachDao;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.utils.ProcessCommonUtils;


/**
 * 案件结案Service
 * @author GLQ
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class CaseTransferService extends CrudService<TcaseDao, Tcase>  {
	
	@Autowired
	protected CaseProcessDao caseProcessDao;
	
	@Autowired
	protected	CaseAttachDao caseAttachDao;	
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;

	@Transactional(readOnly = false)
	public void save(Tcase tcase) {
		boolean isNew = tcase.getIsNewRecord();
		if(isNew){
			tcase.setCaseSeq(SequenceUtils.fetchCaseTransferSeqStr());
			tcase.setCaseTransfer("1");
			
			logger.debug("tcase.getCaseSeq():{}",tcase.getCaseSeq());
		}
		super.save(tcase);
		
		//init process
		if(isNew){
			tcase.getCaseProcess().setCaseId(tcase.getId());
			
			//初始化
			caseProcessDao.initProcessCaseTransfer(tcase.getCaseProcess());

		}else{
			caseProcessDao.update(tcase.getCaseProcess());			
		}
		
	}
	
	@Transactional(readOnly = false)
	public void saveAndStartFlow(Tcase tcase) {
		this.save(tcase);
		
		//start flow
		this.startWorkflow(tcase);
		
	}
	
	@Transactional(readOnly = false)
	public ProcessInstance startWorkflow(Tcase tcase){
		
		User user = UserUtils.getUser();
		
		String roleEnname = user.getRoleEnname();
		
		String processDefKey = ProcessCommonUtils.getProcessDefKeyByStage(tcase.getCaseProcess().getCaseStage(), roleEnname);
		
		String businesskey = tcase.getId()+":"+tcase.getCaseProcess().getId();
		
		logger.debug("businesskey:{}", businesskey);
		
		String userid = user.getLoginName();
		
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("starter", userid);
		
		List<String> assigneeList = tcase.getCaseProcess().getCaseHandlerList(); //分配任务的人员
		
		variables.put("assigneeList", assigneeList);
		
		identityService.setAuthenticatedUserId(userid);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefKey,businesskey,variables);
		
		//set process instance id
		String procInstId = instance.getId();
		String procDefId = instance.getProcessDefinitionId();
		tcase.getCaseProcess().setProcInsId(procInstId);
		tcase.getCaseProcess().setProcDefId(procDefId);
		caseProcessDao.updateProcInfo(tcase.getCaseProcess());
		
		return instance;		
	}
	
	@Transactional(readOnly = false)
	public void doTransfer(Tcase tcase) {
		
		//save case
		String caseId = tcase.getId();
		tcase.setId("");
		tcase.setIsNewRecord(true);
		tcase.setCaseSource("移交");
		
		User user = UserUtils.getUser();
		tcase.setAccepter(user.getName());
		
		tcase.setAcceptDate(Calendar.getInstance().getTime());
		
		tcase.getCaseProcess().setId("");
		tcase.getCaseProcess().setCaseHandler("");
		tcase.getCaseProcess().setCaseStage(Global.CASE_STAGE_ACCEPTANCE);
		tcase.getCaseProcess().setCaseStageStatus("0");
		tcase.getCaseProcess().setProcDefId("");
		tcase.getCaseProcess().setProcInsId("");
		
		this.save(tcase);
		
		//update new case id for original transfer
		Tcase case0 = new Tcase();
		case0.setId(caseId);
		case0.setTransferCaseId(tcase.getId());
		dao.updateTransferCaseId(case0);
		
		//transfer attach
		tcase.setOldCaseId(caseId);
		caseAttachDao.attachTransfer(tcase);
		
	}
	
}