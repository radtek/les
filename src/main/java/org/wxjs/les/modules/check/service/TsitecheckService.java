/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.check.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.check.entity.Tsitecheck;
import org.wxjs.les.modules.message.DbMessageSender;
import org.wxjs.les.modules.message.MessageSender;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;

import org.wxjs.les.modules.act.service.ActTaskService;
import org.wxjs.les.modules.act.service.ProcessService;
import org.wxjs.les.modules.base.dao.SignatureLibDao;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.entity.SignatureLib;
import org.wxjs.les.modules.base.service.SignatureService;
import org.wxjs.les.modules.check.dao.TsitecheckDao;

/**
 * 现场踏勘Service
 * @author 千里目
 * @version 2018-07-10
 */
@Service
@Transactional(readOnly = true)
public class TsitecheckService extends CrudService<TsitecheckDao, Tsitecheck> {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ActTaskService actTaskService;
	
	@Autowired
	private SignatureService signatureService;
	
	@Autowired
	private SignatureLibDao signatureLibDao;

	
	public Tsitecheck get(String id) {
		return super.get(id);
	}
	
	public List<Tsitecheck> findList(Tsitecheck tsitecheck) {
		return super.findList(tsitecheck);
	}
	
	public Page<Tsitecheck> findPage(Page<Tsitecheck> page, Tsitecheck tsitecheck) {
		return super.findPage(page, tsitecheck);
	}
	
	
	@Transactional(readOnly = false)
	public void save(Tsitecheck tsitecheck) {
		super.save(tsitecheck);
	}
	
	@Transactional(readOnly = false)
	public void delete(Tsitecheck tsitecheck) {
		super.delete(tsitecheck);
	}
	
	@Transactional(readOnly = false)
	public void saveAndStartFlow(Tsitecheck tsitecheck) {
		this.save(tsitecheck);
		
		//start and handle flow
		this.startWorkAndHandleflow(tsitecheck);
		
	}
	
	@Transactional(readOnly = false)
	public ProcessInstance startWorkAndHandleflow(Tsitecheck tsitecheck){
		
		User user = UserUtils.getUser();
		
		//String roleEnname = user.getRoleEnname();
		
		String processDefKey = Global.PN_siteCheckProcess;
		
		String businesskey = Global.TsitecheckBusinessKeyPrefix + ":" + tsitecheck.getId();
		
		logger.debug("businesskey:{}", businesskey);
		
		String userid = user.getLoginName();
		
		//start task
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("starter", userid);
		
		variables.put("jcbarAssignee", userid);	
		
		identityService.setAuthenticatedUserId(userid);
		
		String procInstId = tsitecheck.getProcInsId();
		//procInstId 如果不为空，说明流程是已经启动了，接着往下处理就好，不需要重新启动
		ProcessInstance instance = null;
		if(StringUtils.isEmpty(procInstId)){
			instance = runtimeService.startProcessInstanceByKey(processDefKey, businesskey, variables);
			
			//set process instance id
			procInstId = instance.getId();
			String procDefId = instance.getProcessDefinitionId();
			tsitecheck.setCaseStatus(Global.CASE_STAGE_STATUS_STARTED);
			tsitecheck.setProcInsId(procInstId);
			tsitecheck.setProcDefId(procDefId);
			dao.updateProcInfo(tsitecheck);			
		}else{
			tsitecheck.setCaseStatus(Global.CASE_STAGE_STATUS_STARTED);
			dao.updateStatus(tsitecheck);	
		}
		
		//handle task
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userid).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();
		
		// 设置查询条件
		todoTaskQuery.processInstanceBusinessKey(businesskey);
		
		Task task = todoTaskQuery.singleResult();
		
		TaskDefinition nextTaskDef = processService.getNextTaskDefinition(task.getId());
		String group = processService.getNextTaskGroupText(task.getId());
		List<User> users = processService.getHandler4Handle(task.getId(), user.getOffice().getType(), user.getOffice().getAreaId());
		
		String nextHandler = users.get(0).getLoginName();
		
		variables.put(group+"Assignee", users.get(0).getLoginName());
		
		StringBuffer comment = new StringBuffer();
		
		comment.append("[通过]");
		
		String signatureStr = "";
		
		//check whether signature exists
		SignatureLib signatureLibParam = new SignatureLib();
		signatureLibParam.setUser(UserUtils.getUser());
		SignatureLib signatureLib = signatureLibDao.get(signatureLibParam);

		if(signatureLib!=null && StringUtils.isNotEmpty(signatureLib.getSignature())){
			signatureStr = signatureLib.getSignature();
		}
		comment.append(tsitecheck.getApproveOpinion());
		
		taskService.addComment(task.getId(), procInstId, comment.toString());
		
		taskService.claim(task.getId(), userid);
		taskService.complete(task.getId(), variables);
		
		//update opinion to signature
		Signature signature = new Signature(true);
		signature.setTitle(Signature.DefaultTitle);
		
		signature.setSignature(signatureStr);
		signatureService.save(signature);
		
		signature.setProcInstId(procInstId);
		signature.setTaskId(task.getId());
		signature.setTaskName(task.getName());
		signature.setApproveOpinion(tsitecheck.getApproveOpinion());
		signatureService.updateOpinion(signature);
		
		//send message
		MessageSender sender = new DbMessageSender();
		try {
			sender.send(nextHandler, "综合行政执法系统中您有1个待办事项。"
			+" 事项："+"现场踏勘（编号："+tsitecheck.getId()+"）"+"。");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return instance;		
	}
	
	@Transactional(readOnly = false)
	public void updateAttachment(Tsitecheck entity){
		dao.updateAttachment(entity);
	}
	
	@Transactional(readOnly = false)
	public void updateProcInfo(Tsitecheck entity){
		dao.updateProcInfo(entity);
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(Tsitecheck entity){
		dao.updateStatus(entity);
	}
	
}