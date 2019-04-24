/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.act.service.ProcessService;
import org.wxjs.les.modules.base.entity.ActTask;
import org.wxjs.les.modules.sys.dao.UserDao;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.CaseSerious;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.utils.ProcessCommonUtils;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.CaseSeriousDao;

import com.google.common.collect.Lists;

/**
 * 重大行政处罚Service
 * @author GLQ
 * @version 2018-07-27
 */
@Service
@Transactional(readOnly = true)
public class CaseSeriousService extends CrudService<CaseSeriousDao, CaseSerious> {
	
	@Autowired
	private	UserDao userDao;
	
	@Autowired
	private	CaseProcessDao caseProcessDao;
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;

	public CaseSerious get(String id) {
		CaseSerious entity = super.get(id);
		
		//fill name
		if(entity!=null){
			List<User> list = userDao.findAllList(new User());
			String masters = "," + entity.getMaster().getLoginName()+",";
			String voters = "," + entity.getVoter().getLoginName()+",";
			String recorders = "," + entity.getRecorder().getLoginName()+",";
			String attendees = "," + entity.getAttendee().getLoginName()+",";
			
			StringBuffer masterBuffer = new StringBuffer();
			StringBuffer voterBuffer = new StringBuffer();
			StringBuffer recorderBuffer = new StringBuffer();
			StringBuffer attendeeBuffer = new StringBuffer();
			
			for(User user: list){

				if(masters.contains("," + user.getLoginName()+",")){
					masterBuffer.append(",").append(user.getName());
				}
				if(voters.contains("," + user.getLoginName()+",")){
					voterBuffer.append(",").append(user.getName());
				}
				if(recorders.contains("," + user.getLoginName()+",")){
					recorderBuffer.append(",").append(user.getName());
				}
				if(attendees.contains("," + user.getLoginName()+",")){
					attendeeBuffer.append(",").append(user.getName());
				}

			}
			
			if(masterBuffer.length()>0){
				entity.getMaster().setName(masterBuffer.substring(1));
			}
			if(voterBuffer.length()>0){
				entity.getVoter().setName(voterBuffer.substring(1));
			}
			if(recorderBuffer.length()>0){
				entity.getRecorder().setName(recorderBuffer.substring(1));
			}
			if(attendeeBuffer.length()>0){
				entity.getAttendee().setName(attendeeBuffer.substring(1));
			}

		}
		
		return entity;
	}
	
	public List<CaseSerious> findList(CaseSerious caseSerious) {
		return super.findList(caseSerious);
	}
	
	public Page<CaseSerious> findPage(Page<CaseSerious> page, CaseSerious caseSerious) {
		return super.findPage(page, caseSerious);
	}
	
	@Transactional(readOnly = false)
	public void save(CaseSerious caseSerious) {
		super.save(caseSerious);
	}
	
	@Transactional(readOnly = false)
	public void delete(CaseSerious caseSerious) {
		super.delete(caseSerious);
	}
	
	@Transactional(readOnly = false)
	public ProcessInstance startWorkflow(Tcase tcase){
		
		User user = UserUtils.getUser();
		
		String processDefKey = ProcessCommonUtils.getProcessDefKeyByStage(Global.CASE_STAGE_SERIOUS);
		
		logger.debug("processDefKey:{}", processDefKey);
		
		String businesskey = tcase.getId()+":"+tcase.getCaseProcess().getId();
		
		String userid = user.getLoginName();
		
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("starter", userid);
		
		String assignee = tcase.getCaseProcess().getCaseHandler(); //分配任务的人员
		
		variables.put("fgcblrAssignee", assignee);
		
		identityService.setAuthenticatedUserId(userid);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefKey, businesskey, variables);
		
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