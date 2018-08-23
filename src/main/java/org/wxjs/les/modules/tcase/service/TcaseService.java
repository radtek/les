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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.service.CrudService;
import org.wxjs.les.common.utils.FileUtils;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.base.utils.PathUtils;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.utils.ProcessCommonUtils;
import org.wxjs.les.modules.tcase.dao.CaseAttachDao;
import org.wxjs.les.modules.tcase.dao.CaseHandleDao;
import org.wxjs.les.modules.tcase.dao.CaseHandlePunishLibDao;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.TcaseDao;
import org.wxjs.upload.modules.upload.dao.InfPunishDao;
import org.wxjs.upload.modules.upload.dao.InfPunishProcessDao;
import org.wxjs.upload.modules.upload.dao.InfPunishResultDao;
import org.wxjs.upload.modules.upload.entity.InfPunish;
import org.wxjs.upload.modules.upload.entity.InfPunishProcess;
import org.wxjs.upload.modules.upload.entity.InfPunishResult;

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
	protected CaseProcessDao caseProcessDao;
	
	@Autowired
	protected	CaseAttachDao caseAttachDao;	
	
	@Autowired
	protected	CaseHandleDao caseHandleDao;
	
	@Autowired
	protected	CaseHandlePunishLibDao caseHandlePunishLibDao;
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	InfPunishDao infPunishDao;
	
	@Autowired
	InfPunishProcessDao infPunishProcessDao;
	
	@Autowired
	InfPunishResultDao infPunishResultDao;

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
	
	public Tcase getCaseAndProcess(String businesskey) {
		
		if(businesskey.endsWith(":")){
			businesskey = businesskey.substring(0, businesskey.indexOf(":"));
		}
		
		logger.debug("businesskey:{}", businesskey);
		
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		
		Tcase entity = super.get(caseId);
		
		CaseProcess processParam = new CaseProcess();
		processParam.setCaseId(caseId);
		
		List<CaseProcess> processList = caseProcessDao.findList(processParam);
		entity.setCaseProcesses(processList);
		
		if(strs.length > 1){
			String caseProcessId = strs[1];
			
			CaseProcess process = caseProcessDao.get(caseProcessId);
			entity.setCaseProcess(process);			
			
			logger.debug("caseProcessId:{}", caseProcessId);
		}else if(strs.length == 1){
			logger.debug("entity.getCaseTransfer():{}", entity.getCaseTransfer());
			//for case transfer
			if("1".equals(entity.getCaseTransfer())){
				CaseProcess process = new CaseProcess();
				process.setCaseId(caseId);
				List<CaseProcess> list = caseProcessDao.findList(process);
				if(list!=null && list.size()>0){
					entity.setCaseProcess(list.get(0));	
				}
			}
		}
		
		return entity;
	}
	
	public Tcase getCaseAndProcess(String caseId, String caseStage) {
		
		Tcase entity = super.get(caseId);
		
		List<CaseProcess> processList = Lists.newArrayList();
		
		CaseProcess processParam = new CaseProcess();
		processParam.setCaseId(caseId);
		
		processList = caseProcessDao.findList(processParam);
		entity.setCaseProcesses(processList);
		
		processParam.setCaseStage(caseStage);
		processList = caseProcessDao.findList(processParam);
		
		if(processList!=null && processList.size()>0){
			entity.setCaseProcess(processList.get(0));	
		}

		return entity;
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
			tcase.setCaseTransfer("0");
			
			logger.debug("tcase.getCaseSeq():{}",tcase.getCaseSeq());
		}
		super.save(tcase);
		
		
		if(isNew){
			CaseProcess caseProcess = new CaseProcess();

			caseProcess.setCaseId(tcase.getId());

			//init process
			caseProcessDao.initProcess(caseProcess);
			
			//init attaches
			caseAttachDao.initialDefaultItems(tcase);
			
			//初始化
			//caseProcessDao.initProcessExcludeAcceptance(caseProcess);

		}else{
			//caseProcessDao.update(tcase.getCaseProcess());
		}
		
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(Tcase tcase){
		dao.updateStatus(tcase);
		
	}
	
	@Transactional(readOnly = false)
	public void saveProcess(Tcase tcase) {

		caseProcessDao.update(tcase.getCaseProcess());
		
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
	
	public Tcase getRelateCaseByBusinesskey(String businesskey){

		String[] strs = businesskey.split(":");
		String caseId = strs[0];	
		
		return this.get(caseId);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(Tcase tcase) {
		super.delete(tcase);
	}
	
	public void upload(String caseIds){
		String[] strs = caseIds.split(",");
		for(String str:strs){
			//upload data
			Tcase tcase = this.uploadOne(str);
			
			//update upload status
			dao.updateUploadStatus(tcase);
		}
	}
	
	@Transactional(readOnly = false)
	private Tcase uploadOne(String caseId){
		
		final String punishLibSeparator = "||";
		//get case
		Tcase tcase = super.get(caseId);
		
		String internalNo = tcase.getCaseSeq();
		
		CaseHandle caseHandle = this.caseHandleDao.get(caseId);
		
		CaseHandlePunishLib caseHandlePunishLib = new CaseHandlePunishLib();
		caseHandlePunishLib.setCaseId(caseId);
		List<CaseHandlePunishLib> libs = this.caseHandlePunishLibDao.findList(caseHandlePunishLib);
		String libsStr = "";
		StringBuffer buffer = new StringBuffer();
		if(libs.size()>0){
			for(CaseHandlePunishLib lib:libs){
				buffer.append(punishLibSeparator).append(lib.getPunishLib().getSeq());
			}	
			libsStr = buffer.substring(2);
		}

		int attachNeeded = 0;
		int attachuploaded = 0;
		
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setCaseId(caseId);
		caseAttach.setMandatory("1");
		List<CaseAttach> attaches = caseAttachDao.findList(caseAttach);
		
		// classify by stage
		List<CaseAttach> attaches20 = Lists.newArrayList();
		List<CaseAttach> attaches30 = Lists.newArrayList();
		List<CaseAttach> attaches40 = Lists.newArrayList();
		List<CaseAttach> attaches50 = Lists.newArrayList();
		List<CaseAttach> attaches60 = Lists.newArrayList();
		
		attachNeeded = attaches.size();
		StringBuffer attachUploadDetail = new StringBuffer();
		//✔✖
		for(CaseAttach attach:attaches){
			if(StringUtils.isEmpty(attach.getFilepath())){
				attachUploadDetail.append(",").append(attach.getAttachType()).append("(✖)");
				continue;
			}else{
				attachUploadDetail.append(",").append(attach.getAttachType()).append("(✔)");
			}
			if(Global.CASE_STAGE_INITIAL.equals(attach.getFlowNode())){
				attaches20.add(attach);
			}else if(Global.CASE_STAGE_HANDLE.equals(attach.getFlowNode())){
				attaches30.add(attach);
			}else if(Global.CASE_STAGE_NOTIFY.equals(attach.getFlowNode())){
				attaches40.add(attach);
			}else if(Global.CASE_STAGE_DECISION.equals(attach.getFlowNode())){
				attaches50.add(attach);
			}else if(Global.CASE_STAGE_SETTLE.equals(attach.getFlowNode())){
				attaches60.add(attach);
			}
		}
		
		//upload inf_punish
		InfPunish infPunish = new InfPunish();
		infPunish.setInternalNo(internalNo);
		this.infPunishDao.delete(infPunish);
		//fill data
		
		
		//save
		this.infPunishDao.insert(infPunish);
		
		//upload inf_punish_process
		InfPunishProcess infPunishProcess = new InfPunishProcess();
		infPunishProcess.setInternalNo(internalNo);
		this.infPunishProcessDao.delete(infPunishProcess);
		//stage 20
		
		this.infPunishProcessDao.insert(infPunishProcess);
		//stage 30
		
		this.infPunishProcessDao.insert(infPunishProcess);
		//stage 40
		
		this.infPunishProcessDao.insert(infPunishProcess);
		//stage 50
		
		this.infPunishProcessDao.insert(infPunishProcess);
		//stage 60
		this.infPunishProcessDao.insert(infPunishProcess);
		
		//upload inf_PunishResult_result
		InfPunishResult infPunishResult = new InfPunishResult();
		infPunishResult.setInternalNo(internalNo);
		this.infPunishResultDao.delete(infPunishResult);
		
		//fill data
		
		//save
		this.infPunishResultDao.insert(infPunishResult);
		
		tcase.setUploadStatus("1");
		tcase.setAttachUploadProgress(attachuploaded + "/" +attachNeeded);
		tcase.setAttachUploadDetail(attachUploadDetail.substring(1));
		return tcase;
	}
	
	private void fillInfPunish(InfPunish infPunish, Tcase tcase, CaseHandle caseHandle, String libsStr){
		int no = 1000 + Util.getInteger(tcase.getId());
		infPunish.setNo("JS020000JS" + no);
		infPunish.setOrgId("JS020000JS");
		infPunish.setInternalNo(tcase.getCaseSeq());
		infPunish.setItemId(libsStr);
		infPunish.s
	}
	
	private void fillInfPunishResult(InfPunishProcess infPunishProcess, Tcase tcase){
		
	}
	
	private void fillInfPunishResult(InfPunishResult infPunishResult, Tcase tcase){
		
	}
	
	private String loadAttach2Str(List<CaseAttach> attaches){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"gbk\"?>");
        sb.append("<DOCUMENTDATA>");
        for (CaseAttach attach: attaches)
        {
            sb.append("<DOCUMENT>");
            //DOCUMENT_ID
            sb.append("<DOCUMENT_ID>");
            sb.append(attach.getId());
            sb.append("</DOCUMENT_ID>");
            //DOCUMENT_NAME
            sb.append("<DOCUMENT_NAME>");
            sb.append(attach.getAttachType());
            sb.append("</DOCUMENT_NAME>");
            //FILE_NAME
            sb.append("<FILE_NAME>");
            sb.append(attach.getFilename());
            sb.append("</FILE_NAME>");
            //FILE_CONTENT
            sb.append("<FILE_CONTENT>CDATA[");
            sb.append(FileUtils.encryptToBase64(PathUtils.getRealPath(attach.getFilepath())));
            sb.append("]</FILE_CONTENT>");

            sb.append("</DOCUMENT>");
        }
        sb.append("</DOCUMENTDATA>");	
        return sb.toString();
	}
	
}