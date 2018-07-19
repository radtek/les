/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.modules.act.utils.ProcessUtils;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.task.service.CaseTaskService;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.utils.ProcessCommonUtils;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;

import com.google.common.collect.Lists;

/**
 * 案件Service
 * @author GLQ
 * @version 2018-07-09
 */
@Service
@Transactional(readOnly = true)
public class TcaseService extends CrudService<TcaseDao, Tcase> {
	@Autowired
	private	CaseProcessDao caseProcessDao;
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;

	public Tcase get(String id) {
		Tcase tcase = super.get(id);
		
		//fill current process info
		CaseProcess process = this.getCurrentCommonCaseProcess(tcase);
		if(process != null){
			tcase.setCaseProcess(process);
			tcase.getCurrentCaseProcesses().add(process);
		}
		
		List<CaseProcess> independentProcesses = this.getCurrentIndependentCaseProcess(tcase);
		if(independentProcesses.size()>0){
			tcase.getCurrentCaseProcesses().addAll(independentProcesses);
		}
		
		
		return tcase;
	}
	
	/**
	 * 获取当前的非独立流程
	 * @param tcase
	 * @return
	 */
	public CaseProcess getCurrentCommonCaseProcess(Tcase tcase){
		CaseProcess process = null;
		if(tcase!=null){
			CaseProcess caseProcessParam = new CaseProcess();
			caseProcessParam.setCaseId(tcase.getId());
			List<CaseProcess> processes = caseProcessDao.findList(caseProcessParam);
			
			logger.debug("processes.size():{}",processes.size());
			
			for(CaseProcess caseProcess : processes){
				if(!caseProcess.isIndependentFlow()){
					if(process == null){
						//fill the first one
						process = caseProcess;
					}
					if(!"0".equals(caseProcess.getCaseStageStatus())){
						process = caseProcess;
					}
				}
			}			
		}
		return process;
	}
	
	/**
	 * 获取当前的独立流程
	 * @param tcase
	 * @return
	 */
	public List<CaseProcess> getCurrentIndependentCaseProcess(Tcase tcase){
		List<CaseProcess> processes = Lists.newArrayList();
		if(tcase!=null){
			CaseProcess caseProcessParam = new CaseProcess();
			caseProcessParam.setCaseId(tcase.getId());
			List<CaseProcess> list = caseProcessDao.findList(caseProcessParam);
			
			for(CaseProcess caseProcess : list){
				if(caseProcess.isIndependentFlow() && !"0".equals(caseProcess.getCaseStageStatus())){
					processes.add(caseProcess);
				}
			}			
		}	
		return processes;
	}
	
	public List<Tcase> findList(Tcase tcase) {

		List<Tcase> rst = Lists.newArrayList();
		List<Tcase> list = super.findList(tcase);
		
		//get current process list
		List<CaseProcess> currentProcesses = caseProcessDao.findCurrentProcesses(new CaseProcess());
		for(Tcase entity : list){
			logger.debug("entity.getCurrentCaseProcess().size():{}", entity.getCurrentCaseProcesses().size()+"");
			for(CaseProcess process : currentProcesses){
				if(process.getCaseId().equals(entity.getId())){
					entity.getCurrentCaseProcesses().add(process);
				}
			}
			logger.debug("after handle, entity.getCurrentCaseProcess().size():{}", entity.getCurrentCaseProcesses().size()+"");
			rst.add(entity);
		}
		
		return rst;
	}
	
	public Page<Tcase> findPage(Page<Tcase> page, Tcase tcase) {
		tcase.setPage(page);
		page.setList(this.findList(tcase));
		return page;		
	}
	
	@Transactional(readOnly = false)
	public void save(Tcase tcase) {
		boolean isNew = tcase.getIsNewRecord();
		if(isNew){
			tcase.setCaseSeq(SequenceUtils.fetchCaseSeqStr());
			logger.debug("tcase.getCaseSeq():{}",tcase.getCaseSeq());
		}
		super.save(tcase);
		
		//init process
		if(isNew){
			CaseProcess caseProcess = new CaseProcess();
			caseProcess.setCaseId(tcase.getId());
			caseProcessDao.initProcess(caseProcess);
			

			/*
			//load tcase again for Case
			Tcase tcaseNew = this.get(tcase.getId());
			if(tcaseNew != null && tcaseNew.getCaseProcess() != null){
				tcase.getCaseProcess().setId(tcaseNew.getCaseProcess().getId());
				tcase.getCaseProcess().setCaseId(tcaseNew.getCaseProcess().getCaseId());
				tcase.getCaseProcess().setCaseStage(tcaseNew.getCaseProcess().getCaseStage());	
				tcase.getCaseProcess().setCaseStageStatus(tcaseNew.getCaseProcess().getCaseStageStatus());
			}
			*/

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
		
		String processDefKey = ProcessCommonUtils.getProcessDefKeyByStage(tcase.getCaseProcess().getCaseStage());
		
		String businesskey = tcase.getId()+":"+tcase.getCaseProcess().getId();
		
		String userid = UserUtils.getUser().getLoginName();
		
		Map<String,Object> variables=new HashMap<String, Object>();
		variables.put("starter", userid);
		
		List<String> assigneeList = tcase.getCaseProcess().getCaseHandlerList(); //分配任务的人员
		
		variables.put("assigneeList", assigneeList);
		
		identityService.setAuthenticatedUserId(userid);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefKey,businesskey,variables);
		
		//set process instance id
		String procInstId = instance.getId();
		tcase.getCaseProcess().setProcInstId(procInstId);
		caseProcessDao.updateProcInstId(tcase.getCaseProcess());
		
		return instance;		
	}
	
	public Tcase getRelateCaseByTask(Task task){

		ProcessUtils pu = new ProcessUtils();
		String businesskey = pu.getBusinesskeyByProcInstId(task.getProcessInstanceId());
		String[] strs = businesskey.split(":");
		String caseId = strs[0];	
		
		return this.get(caseId);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Tcase tcase) {
		super.delete(tcase);
	}
	
}