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
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.tcase.dao.CaseAttachDao;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.utils.ProcessCommonUtils;

import com.google.common.collect.Lists;


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
	protected CaseAttachDao caseAttachDao;	
	
	@Autowired
	protected TcaseService tcaseService;
	
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
		
		//update status
		tcase.setStatus(Global.CASE_STATUS_STARTED);
		tcaseService.updateStatus(tcase);
		
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
		tcase.setStatus("1:10");
		
		User user = UserUtils.getUser();
		tcase.setAccepter(user.getLoginName());
		
		tcase.setAcceptDate(Calendar.getInstance().getTime());
		
		/*
		tcase.getCaseProcess().setId("");
		tcase.getCaseProcess().setCaseHandler("");
		tcase.getCaseProcess().setCaseStage(Global.CASE_STAGE_ACCEPTANCE);
		//移交，跳过受理，直接设置为已办结
		tcase.getCaseProcess().setCaseStageStatus(Global.CASE_STAGE_STATUS_FINISHED);
		tcase.getCaseProcess().setProcDefId("");
		tcase.getCaseProcess().setProcInsId("");
		*/
		
		tcaseService.save(tcase);
		//自动设置受理已办结
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseId(tcase.getId());
		caseProcess.setCaseStage(Global.CASE_STAGE_ACCEPTANCE);
		caseProcess.setCaseStageStatus(Global.CASE_STAGE_STATUS_FINISHED);
		caseProcess.setCaseHandler(user.getLoginName());
		caseProcess.setCaseSummary(tcase.getCaseProcess().getCaseSummary());
		
		caseProcessDao.update(caseProcess);
		
	
		//update new case id for original transfer
		Tcase case0 = new Tcase();
		case0.setId(caseId);
		case0.setTransferCaseId(tcase.getId());
		dao.updateTransferCaseId(case0);
		
		//set case transfer finished
		case0.setStatus(Global.CASE_STATUS_FINISHED);
		tcaseService.updateStatus(case0);
		
		//transfer attach
		tcase.setOldCaseId(caseId);
		caseAttachDao.attachTransfer(tcase);
	
		
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<Tcase> findList(Tcase entity) {
		List<Tcase> rst = Lists.newArrayList();
		List<Tcase> list = dao.findList(entity);
		
		//filter by office
		User user = UserUtils.getUser();
		String officeId = user.getOffice().getId();
		if(officeId.startsWith("01") || officeId.startsWith("02")){
			rst.addAll(list);
		}else{
			String deptId = officeId.substring(0, 2);
			for(Tcase e : list){
				if(e.getCreateBy().getOffice().getId().startsWith(deptId)){
					rst.add(e);
				}
			}
		}
		
		return rst;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<Tcase> findPage(Page<Tcase> page, Tcase entity) {
		entity.setPage(page);
		page.setList(this.findList(entity));
		return page;
	}
	
}