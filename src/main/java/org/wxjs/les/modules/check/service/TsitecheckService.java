/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.check.service;

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
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.check.entity.Tsitecheck;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;

import org.wxjs.les.modules.act.service.ActTaskService;
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
	TaskService taskService;
	
	@Autowired
	ActTaskService actTaskService;

	
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
		
		//start flow
		this.startWorkflow(tsitecheck);
		
	}
	
	@Transactional(readOnly = false)
	public ProcessInstance startWorkflow(Tsitecheck tsitecheck){
		
		User user = UserUtils.getUser();
		
		//String roleEnname = user.getRoleEnname();
		
		String processDefKey = Global.PN_siteCheckProcess;
		
		String businesskey = Global.TsitecheckBusinessKeyPrefix + ":" + tsitecheck.getId();
		
		logger.debug("businesskey:{}", businesskey);
		
		String userid = user.getLoginName();
		
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("starter", userid);
		
		variables.put("jcbarAssignee", userid);	
		
		identityService.setAuthenticatedUserId(userid);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefKey, businesskey, variables);
		
		//set process instance id
		String procInstId = instance.getId();
		String procDefId = instance.getProcessDefinitionId();
		tsitecheck.setCaseStatus(Global.CASE_STAGE_STATUS_STARTED);
		tsitecheck.setProcInsId(procInstId);
		tsitecheck.setProcDefId(procDefId);
		dao.updateProcInfo(tsitecheck);
		
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