/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.tcase.entity.CaseCancel;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.utils.ProcessCommonUtils;
import org.wxjs.les.modules.tcase.dao.CaseCancelDao;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;

/**
 * 案件撤销Service
 * @author GLQ
 * @version 2018-08-08
 */
@Service
@Transactional(readOnly = true)
public class CaseCancelService extends CrudService<CaseCancelDao, CaseCancel> {
	
	@Autowired
	private	CaseProcessDao caseProcessDao;
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;

	public CaseCancel get(String id) {
		return super.get(id);
	}
	
	public List<CaseCancel> findList(CaseCancel caseCancel) {
		return super.findList(caseCancel);
	}
	
	public Page<CaseCancel> findPage(Page<CaseCancel> page, CaseCancel caseCancel) {
		return super.findPage(page, caseCancel);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseCancel caseCancel) {
		super.save(caseCancel);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseCancel caseCancel) {
		super.delete(caseCancel);
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
		
		//
		String caseHandler = tcase.getCaseProcess().getCaseHandler();
		if(processDefKey.startsWith(Global.PN_caseCancelProcess1)){
			List<String> assigneeList = tcase.getCaseProcess().getCaseHandlerList(); //分配任务的人员
			variables.put("assigneeList", assigneeList);
		}else if(processDefKey.startsWith(Global.PN_caseCancelProcess2)){
			variables.put("jcksfzrAssignee", caseHandler);
		}else if(processDefKey.startsWith(Global.PN_caseCancelProcess3)){
			variables.put("zdfzrAssignee", caseHandler);
		}else if(processDefKey.startsWith(Global.PN_caseCancelProcess4)){
			variables.put("jldAssignee", caseHandler);
		}
		
		identityService.setAuthenticatedUserId(userid);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefKey,businesskey,variables);
		
		//set process instance id
		String procInstId = instance.getId();
		String procDefId = instance.getProcessDefinitionId();
		tcase.getCaseProcess().setCaseStageStatus(Global.CASE_STAGE_STATUS_STARTED);
		tcase.getCaseProcess().setProcInsId(procInstId);
		tcase.getCaseProcess().setProcDefId(procDefId);
		caseProcessDao.updateProcInfo(tcase.getCaseProcess());
		
		return instance;		
	}
	
}