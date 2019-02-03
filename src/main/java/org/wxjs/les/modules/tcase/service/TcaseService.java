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
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.FileUtils;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.act.service.ActTaskService;
import org.wxjs.les.modules.base.dao.SignatureDao;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.service.SignatureService;
import org.wxjs.les.modules.base.utils.PathUtils;
import org.wxjs.les.modules.sys.dao.UserDao;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.DictUtils;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib;
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.utils.ProcessCommonUtils;
import org.wxjs.les.modules.tcase.dao.CaseAttachDao;
import org.wxjs.les.modules.tcase.dao.CaseCancelDao;
import org.wxjs.les.modules.tcase.dao.CaseDecisionDao;
import org.wxjs.les.modules.tcase.dao.CaseFinishDao;
import org.wxjs.les.modules.tcase.dao.CaseHandleDao;
import org.wxjs.les.modules.tcase.dao.CaseHandlePunishLibDao;
import org.wxjs.les.modules.tcase.dao.CaseNotifyDao;
import org.wxjs.les.modules.tcase.dao.CaseProcessDao;
import org.wxjs.les.modules.tcase.dao.CaseSeriousDao;
import org.wxjs.les.modules.tcase.dao.CaseSettleDao;
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
	protected	CaseCancelDao caseCancelDao;
	
	@Autowired
	protected	CaseFinishDao caseFinishDao;
	
	@Autowired
	protected	CaseNotifyDao caseNotifyDao;
	
	@Autowired
	protected	CaseSeriousDao caseSeriousDao;
	
	@Autowired
	protected	CaseSettleDao caseSettleDao;
	
	@Autowired
	protected	CaseHandleDao caseHandleDao;
	
	@Autowired
	protected	CaseDecisionDao caseDecisionDao;
	
	@Autowired
	protected	TcaseDao tcaseDao;
	
	@Autowired
	protected	SignatureDao signatureDao;
	
	@Autowired
	protected	SignatureService signatureService;
	
	@Autowired
	protected	UserDao userDao;
	
	@Autowired
	protected	CaseHandlePunishLibDao caseHandlePunishLibDao;
	
	@Autowired
	protected	CaseDecisionService caseDecisionService;
	
	@Autowired
	protected	CaseNotifyService caseNotifyService;
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ActTaskService actTaskService;
	
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
		List<Tcase> list = dao.findList(tcase);
		
		logger.debug("entity.getUnfinishedFlag():{}", tcase.getUnfinishedFlag());
		
		//get current process list
		List<CaseProcess> currentProcesses = caseProcessDao.findCurrentProcesses(new CaseProcess());
		for(Tcase entity : list){
			
			if("1".equals(tcase.getUnfinishedFlag())){
				//filter finished items
				if(Global.CASE_STATUS_FINISHED.equals(entity.getStatus())){
					continue;
				}
			}
			
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
	
	public List<Tcase> findList4My(Tcase tcase) {

		List<Tcase> rst = Lists.newArrayList();
		List<Tcase> list = dao.findList(tcase);
		
		List<String> myCaseIds = Lists.newArrayList(); //我相关的案件
		if(tcase.getMyCaseFlag()){
			//prepare data to filter
			List<String> myHistoryProcIds = actTaskService.myProcIds();
			CaseProcess caseProcess = new CaseProcess();
			caseProcess.setProcInsIds(myHistoryProcIds);
			
			myCaseIds = caseProcessDao.findListByProcInsIds(caseProcess);
		}
		
		logger.debug("entity.getUnfinishedFlag():{}", tcase.getUnfinishedFlag());
		
		//get current process list
		List<CaseProcess> currentProcesses = caseProcessDao.findCurrentProcesses(new CaseProcess());
		for(Tcase entity : list){
			
			if("1".equals(tcase.getUnfinishedFlag())){
				//filter finished items
				if(Global.CASE_STATUS_FINISHED.equals(entity.getStatus())){
					continue;
				}
			}
			
			logger.debug("entity.getCurrentCaseProcess().size():{}", entity.getCurrentCaseProcesses().size()+"");
			for(CaseProcess process : currentProcesses){
				if(process.getCaseId().equals(entity.getId())){
					entity.getCurrentCaseProcesses().add(process);
				}
			}
			logger.debug("after handle, entity.getCurrentCaseProcess().size():{}", entity.getCurrentCaseProcesses().size()+"");
			if(tcase.getMyCaseFlag()){
				//filter those not related
				if(myCaseIds.contains(entity.getId())){
					rst.add(entity);
				}
			}else{
				//no filter
				rst.add(entity);
			}
			
		}
		
		return rst;
	}
	
	public Page<Tcase> findPage(Page<Tcase> page, Tcase tcase) {
		tcase.setPage(page);
		
		List<Tcase> list = this.findList(tcase);
		
		//fill names
		List<User> userList = userDao.findList(new User());
		Map<String, String> userMap = new HashMap<String, String>();
		for(User user : userList){
			userMap.put(user.getLoginName(), user.getName());
		}
		
		for(Tcase entity : list){
			String handler = entity.getInitialHandler();
			if(StringUtils.isEmpty(handler)){
				continue;
			}
			
			String[] strs = handler.split(",");
			StringBuffer buffer = new StringBuffer();
			for(String str: strs){
				if(str.length()>0){
					buffer.append(userMap.get(str)).append(",");
				}
			}
			entity.setInitialHandlerName(buffer.toString());

		}
		
		page.setList(list);

		return page;		
	}
	
	public Page<Tcase> findPage4My(Page<Tcase> page, Tcase tcase) {
		//tcase.setPage(page);
		
		logger.debug("1>>count：{}， size：{}， no:{}, page.getTotalPage():{}", page.getCount(),page.getPageSize(),page.getPageNo(), page.getTotalPage());
		
		List<Tcase> list = this.findList4My(tcase);
		
		logger.debug("2>>list.size():{}, count：{}， size：{}， no:{}, page.getTotalPage():{}", list.size(), page.getCount(),page.getPageSize(),page.getPageNo(), page.getTotalPage());
		
		//fill names
		List<User> userList = userDao.findList(new User());
		Map<String, String> userMap = new HashMap<String, String>();
		for(User user : userList){
			userMap.put(user.getLoginName(), user.getName());
		}
		
		for(Tcase entity : list){
			String handler = entity.getInitialHandler();
			if(StringUtils.isEmpty(handler)){
				continue;
			}
			
			String[] strs = handler.split(",");
			StringBuffer buffer = new StringBuffer();
			for(String str: strs){
				if(str.length()>0){
					buffer.append(userMap.get(str)).append(",");
				}
			}
			entity.setInitialHandlerName(buffer.toString());

		}
		List<Tcase> sublist = this.getSubList(list, page);
		
		page = new Page<Tcase>(page.getPageNo(), page.getPageSize(), sublist.size(), sublist);
		page.initialize();

		logger.debug("3>>list.size():{}, count：{}， size：{}， no:{}, page.getTotalPage():{}", list.size(), page.getCount(),page.getPageSize(),page.getPageNo(), page.getTotalPage());
		
		return page;		
	}
	
	private List<Tcase> getSubList(List<Tcase> list, Page<Tcase> page){
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int total = list.size();
		
		List<Tcase> sublist = Lists.newArrayList();
		
		if(total<= pageSize){
			sublist.addAll(list);
		}else{
			int fromIndex = pageNo * pageSize;
			int toIndex =pageNo * pageSize + pageSize;
			
			if(total < toIndex){
				toIndex = total;
			}
			
			sublist = list.subList(fromIndex, toIndex);			
		}
		
		return sublist;
	}
	
	@Transactional(readOnly = false)
	public void save(Tcase tcase) {
		boolean isNew = tcase.getIsNewRecord();
		if(isNew){
			User user = UserUtils.getUser();
			String handleOrg = user.getOffice().getParentId();
			
			tcase.setCaseSeq(SequenceUtils.fetchCaseSeqStr(handleOrg));
			tcase.setCaseTransfer("0");
			
			logger.debug("tcase.getCaseSeq():{}",tcase.getCaseSeq());
			//set handleOrg

			tcase.setHandleOrg(handleOrg);
			
			logger.debug("handleOrg:{}",handleOrg);
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
	
	@Transactional(readOnly = false)
	public void upload(String caseIds){
		String[] strs = caseIds.split(",");
		
		logger.debug("caseIds:{}", caseIds);
		
		for(String str:strs){
			
			if(StringUtils.isEmpty(str)){
				continue;
			}
			//upload data
			Tcase tcase = this.uploadOne(str);
			
			//update upload status
			this.updateUploadStatus(tcase);
		}
	}
	
	@Transactional(readOnly = false)
	private void updateUploadStatus(Tcase tcase){
		dao.updateUploadStatus(tcase);
	}
	
	@Transactional(readOnly = false)
	private Tcase uploadOne(String caseId){
		
		final String punishLibSeparator = "||";
		
		logger.debug("caseId:{}", caseId);
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
		
		logger.debug("libsStr:{}, libs.size:{}", libsStr, libs.size());

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
			attachUploadDetail.append(",");
			if(StringUtils.isEmpty(attach.getFilepath())){
				attachUploadDetail.append(attach.getAttachType()).append(Global.AttachInexistsFlag);
				continue;
			}else{
				attachUploadDetail.append(attach.getAttachType()).append(Global.AttachExistsFlag);
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
		
		InfPunish infPunishOld = this.infPunishDao.get(infPunish);
		String itemVersion = "1";
		if(infPunishOld != null){
			itemVersion = (Util.getInteger(infPunishOld.getItemVersion(), 1) + 1) + "";
		}
		
		this.infPunishDao.delete(infPunish);
		//fill data
		this.fillInfPunish(infPunish, tcase, caseHandle, libsStr);
		infPunish.setItemVersion(itemVersion);
		
		//save
		this.infPunishDao.insert(infPunish);
		
		//upload inf_punish_process
		InfPunishProcess infPunishProcess = new InfPunishProcess();
		infPunishProcess.setInternalNo(internalNo);
		this.infPunishProcessDao.delete(infPunishProcess);
		
		//stage 20
		infPunishProcess = new InfPunishProcess();
		infPunishProcess.setInternalNo(internalNo);
		this.fillInfPunishProcess(infPunishProcess, tcase, libsStr, Global.CASE_STAGE_INITIAL, attaches20);
		
		attachuploaded += attaches20.size();
		
		this.infPunishProcessDao.insert(infPunishProcess);
		
		//stage 30
		infPunishProcess = new InfPunishProcess();
		infPunishProcess.setInternalNo(internalNo);
		this.fillInfPunishProcess(infPunishProcess, tcase, libsStr, Global.CASE_STAGE_HANDLE, attaches30);
		
		attachuploaded += attaches30.size();
		
		this.infPunishProcessDao.insert(infPunishProcess);
		
		//stage 40
		infPunishProcess = new InfPunishProcess();
		infPunishProcess.setInternalNo(internalNo);
		this.fillInfPunishProcess(infPunishProcess, tcase, libsStr, Global.CASE_STAGE_NOTIFY, attaches40);
		
		attachuploaded += attaches40.size();
		
		this.infPunishProcessDao.insert(infPunishProcess);
		
		//stage 50
		infPunishProcess = new InfPunishProcess();
		infPunishProcess.setInternalNo(internalNo);
		this.fillInfPunishProcess(infPunishProcess, tcase, libsStr, Global.CASE_STAGE_DECISION, attaches50);
		
		attachuploaded += attaches50.size();
		
		this.infPunishProcessDao.insert(infPunishProcess);
		
		//stage 60
		infPunishProcess = new InfPunishProcess();
		infPunishProcess.setInternalNo(internalNo);
		this.fillInfPunishProcess(infPunishProcess, tcase, libsStr, Global.CASE_STAGE_SETTLE, attaches60);
		
		attachuploaded += attaches60.size();
		
		this.infPunishProcessDao.insert(infPunishProcess);
		
		//upload inf_PunishResult_result
		InfPunishResult infPunishResult = new InfPunishResult();
		infPunishResult.setInternalNo(internalNo);
		this.infPunishResultDao.delete(infPunishResult);
		
		//fill data
		CaseDecision caseDecision = this.caseDecisionDao.get(caseId);
		this.fillInfPunishResult(infPunishResult, tcase, caseHandle, caseDecision, libsStr);
		
		//save
		this.infPunishResultDao.insert(infPunishResult);
		
		tcase.setUploadStatus("1");
		if(attachuploaded == attachNeeded){
			tcase.setUploadStatus("2");
		}
		tcase.setAttachUploadProgress(attachuploaded + "/" +attachNeeded);
		tcase.setAttachUploadDetail(attachUploadDetail.substring(1));
		return tcase;
	}
	
	private void fillInfPunish(InfPunish infPunish, Tcase tcase, CaseHandle caseHandle, String libsStr){
		int no = 1000 + Util.getInteger(tcase.getId());
		infPunish.setNo(Global.UploadOrgId + no);
		infPunish.setOrgId(Global.UploadOrgId);
		//infPunish.setInternalNo(tcase.getCaseSeq());
		infPunish.setItemId(libsStr);
		infPunish.setDepartment(Global.getConfig("CBBM"));
		infPunish.setAjAddr(tcase.getCaseHappenAddress());
		
		logger.debug("tcase.getCaseHappenDate():{}", tcase.getCaseHappenDate());
		
		//infPunish.setAjOccurDate(DateUtils.parseDate(tcase.getCaseHappenDate()));
		infPunish.setAjOccurDate(tcase.getInitialDate());
		
		infPunish.setSource("0");
		infPunish.setFact(caseHandle.getFact());
		infPunish.setTargetType(tcase.getPartyType());
		infPunish.setPunishTarget(tcase.getPartyDisplay());
		infPunish.setTargetCode(tcase.getPartyCode());
		infPunish.setTargetPaperType("8");
		infPunish.setTargetPhone(tcase.getPartyPhone());
		infPunish.setTargetAddress(tcase.getPartyAddress());
		infPunish.setPromise("90");
		infPunish.setPromiseType("1");
		infPunish.setIsrisk("0");
		
		Calendar cal = Calendar.getInstance();
		infPunish.setCreateDate(cal.getTime());
		infPunish.setUpdateDate(cal.getTime());
	}
	
	private void fillInfPunishProcess(InfPunishProcess infPunishProcess, Tcase tcase, String libsStr, String stage, List<CaseAttach> attaches){
		String no = (1000 + Util.getInteger(tcase.getId())) + "-" + stage;
		infPunishProcess.setNo(Global.UploadOrgId + no);
		infPunishProcess.setNoOrd(this.getTacheCode(stage));
		infPunishProcess.setOrgId(Global.UploadOrgId);
		infPunishProcess.setItemId(libsStr);
		infPunishProcess.setTacheCode(this.getTacheCode(stage));
		infPunishProcess.setTacheName(this.getTacheName(stage));
		infPunishProcess.setDepartment(Global.getConfig("CBBM"));
		infPunishProcess.setUserStaffCode("99");
		
		CaseProcess caseProcess = this.getProcess(tcase.getId(), stage);
		
		String procInsId = caseProcess.getProcInsId();
		Signature signature = new Signature(false);
		signature.setProcInstId(procInsId);
		List<Signature> sigList = signatureService.findList4Export(signature);
		
		logger.debug("procInsId:{}, sigList.size():{}", procInsId, sigList.size());
		
		String userName = "";
		Signature sigFirst = null;
		Signature sigLast = null;
		if(sigList.size()>0){
			sigFirst = sigList.get(0);
			userName = sigFirst.getCreateBy().getName();
			infPunishProcess.setUserName(userName);
			
			infPunishProcess.setNote(sigFirst.getApproveOpinion());
			
			sigLast = sigList.get(sigList.size()-1);
			
			infPunishProcess.setProcessStartTime(sigFirst.getCreateDate());
			infPunishProcess.setProcessEndTime(sigLast.getCreateDate());
		}
		
		infPunishProcess.setStatus("4");
		infPunishProcess.setPromise("15");
		infPunishProcess.setPromiseType("3");
		infPunishProcess.setPromiseStartSign("1");
		
		String attachesContent = this.loadAttach2Str(attaches);
		infPunishProcess.setAttachment(attachesContent);
		
		//logger.debug("stage:{}, attachesContent:{}", stage, attachesContent);
		
		Calendar cal = Calendar.getInstance();
		infPunishProcess.setCreateDate(cal.getTime());
		infPunishProcess.setUpdateDate(cal.getTime());
		
	}
	
	private String getTacheCode(String stage){
		String tacheCode = "";
		if(Global.CASE_STAGE_INITIAL.equals(stage)){
			tacheCode = "1";
		}else if(Global.CASE_STAGE_HANDLE.equals(stage)){
			tacheCode = "2";
		}else if(Global.CASE_STAGE_NOTIFY.equals(stage)){
			tacheCode = "4";
		}else if(Global.CASE_STAGE_DECISION.equals(stage)){
			tacheCode = "7";
		}else if(Global.CASE_STAGE_SETTLE.equals(stage)){
			tacheCode = "8";
		}
		return tacheCode;
	}
	
	private String getTacheName(String stage){
		String tacheName = "";
		if(Global.CASE_STAGE_INITIAL.equals(stage)){
			tacheName = "立案";
		}else if(Global.CASE_STAGE_HANDLE.equals(stage)){
			tacheName = "案件审理";
		}else if(Global.CASE_STAGE_NOTIFY.equals(stage)){
			tacheName = "发告知书";
		}else if(Global.CASE_STAGE_DECISION.equals(stage)){
			tacheName = "发决定书";
		}else if(Global.CASE_STAGE_SETTLE.equals(stage)){
			tacheName = "结案书";
		}		
		return tacheName;
	}
	
	private void fillInfPunishResult(InfPunishResult infPunishResult, Tcase tcase, CaseHandle caseHandle, CaseDecision caseDecision, String libsStr){
		String no = (1000 + Util.getInteger(tcase.getId())) +"";
		infPunishResult.setNo(Global.UploadOrgId + no);
		infPunishResult.setOrgId(Global.UploadOrgId);
		infPunishResult.setItemId(libsStr);
		infPunishResult.setProgram("1");
		infPunishResult.setStandard(caseDecision.getContent());
		infPunishResult.setAccordance(caseDecision.getContent());
		infPunishResult.setPunishResult(caseDecision.getContent());
		infPunishResult.setPunishDeside("1");
		infPunishResult.setPunishClass("2");
		infPunishResult.setPunishSort(" ");
		infPunishResult.setPunishResultFine(caseDecision.getPunishMoney());
		infPunishResult.setPunishResultFinePeople("1");
		
		infPunishResult.setFinishTime(tcase.getSettleDate());
		
		//get settle process finish time
		/*
		CaseProcess caseProcess = this.getProcess(tcase.getId(), Global.CASE_STAGE_SETTLE);
		
		String procInsId = caseProcess.getProcInsId();
		Signature signature = new Signature();
		signature.setProcInstId(procInsId);
		List<Signature> sigList = signatureDao.findList(signature);
		Signature sigLast = null;
		if(sigList.size()>0){
			sigLast = sigList.get(sigList.size()-1);
			infPunishResult.setFinishTime(sigLast.getCreateDate());
		}
		*/

		Calendar cal = Calendar.getInstance();
		infPunishResult.setCreateDate(cal.getTime());
		infPunishResult.setUpdateDate(cal.getTime());
		
		
	}
	
	private String loadAttach2Str(List<CaseAttach> attaches){
		if(attaches.size()==0){
			return "";
		}
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
	
	private CaseProcess getProcess(String caseId, String caseStage) {
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseId(caseId);
		caseProcess.setCaseStage(caseStage);
		List<CaseProcess> list = this.caseProcessDao.findList(caseProcess);
		CaseProcess rst = null;
		if(list!=null && list.size()>0){
			rst = list.get(0);
		}
		return rst;
	}
	
	/**
	 * delete case
	 * @param caseId
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean deleteCase(String caseId) {
		boolean rst = true;
		
		Tcase tcase = new Tcase();
		tcase.setId(caseId);
		
		//delete flow, signature
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseId(caseId);
		List<CaseProcess> caseProcesses = caseProcessDao.findList(caseProcess);
		for(CaseProcess entity: caseProcesses){
			if(StringUtils.isNotEmpty(entity.getProcInsId())){
				
				try{
					String procId = entity.getProcInsId();
					long count = runtimeService.createProcessInstanceQuery().processInstanceId(procId).count();
					if(count>0){
						runtimeService.deleteProcessInstance(procId, "管理员手动删除");
					}
				}catch(Exception ex){
					logger.error("delete process error", ex);
				}
				
				Signature signature = new Signature(false);
				signature.setProcInstId(entity.getProcInsId());
				signatureDao.deleteByProcInsId(signature);
			}
		}
		
		//delete case related
		this.caseAttachDao.deleteByCaseId(caseId);
		this.caseCancelDao.deleteByCaseId(caseId);
		
		//delete decision
		//recall no
		CaseDecision caseDecisionParam = new CaseDecision();
		caseDecisionParam.setCaseId(caseId);
		
		CaseDecision caseDecision = caseDecisionService.get(caseId);
		if(caseDecision != null && StringUtils.isNotEmpty(caseDecision.getSeq())){
			this.caseDecisionService.recallNumber(caseDecision);
		}
		this.caseDecisionDao.deleteByCaseId(caseId);
		
		this.caseFinishDao.deleteByCaseId(caseId);
		this.caseHandleDao.deleteByCaseId(caseId);
		
		CaseHandlePunishLib punishLib = new CaseHandlePunishLib();
		punishLib.setCaseId(caseId);
		this.caseHandlePunishLibDao.deleteByCaseId(punishLib);
		
		//delete notify
		//recall no
		CaseNotify caseNotifyParam = new CaseNotify();
		caseNotifyParam.setCaseId(caseId);
		CaseNotify caseNotify = caseNotifyService.get(caseId);
		if(caseNotify != null && StringUtils.isNotEmpty(caseNotify.getSeq())){
			this.caseNotifyService.recallNumber(caseNotify);
		}
		this.caseNotifyDao.deleteByCaseId(caseId);
		
		this.caseProcessDao.deleteByCaseId(caseId);
		this.caseSeriousDao.deleteByCaseId(caseId);
		this.caseSettleDao.deleteByCaseId(caseId);
		
		//delete case
		this.delete(tcase);
		
		//log
		User user = UserUtils.getUser();
		logger.info("delete case {}, operator: {}", caseId, user.getLoginName());
		
		return rst;
	}
	
	/**
	 * delete case stage
	 * @param caseId
	 * @param caseStage
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean deleteCaseStage(String caseId, String caseStage) {
		boolean rst = true;
		
		Tcase tcase = new Tcase();
		tcase.setId(caseId);
		
		//delete flow, signature
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseId(caseId);
		List<CaseProcess> caseProcesses = caseProcessDao.findList(caseProcess);
		for(CaseProcess entity: caseProcesses){
			if(caseStage.equals(entity.getCaseStage())){
				
				this.caseProcessDao.clearProcess(entity);
				
				try{
					String procId = entity.getProcInsId();
					long count = runtimeService.createProcessInstanceQuery().processInstanceId(procId).count();
					if(count>0){
						runtimeService.deleteProcessInstance(procId, "管理员手动删除");
					}
				}catch(Exception ex){
					logger.error("delete process error", ex);
				}
				
				Signature signature = new Signature(false);
				signature.setProcInstId(entity.getProcInsId());
				signatureDao.deleteByProcInsId(signature);
			}
		}
		
		//delete case related
		if(caseStage.equals(Global.CASE_STAGE_ACCEPTANCE)){
			
		}else if(caseStage.equals(Global.CASE_STAGE_CANCEL)){
			this.caseCancelDao.deleteByCaseId(caseId);
		}else if(caseStage.equals(Global.CASE_STAGE_DECISION)){
			//recall no
			CaseDecision caseDecisionParam = new CaseDecision();
			caseDecisionParam.setCaseId(caseId);
			
			CaseDecision caseDecision = caseDecisionService.get(caseId);
			if(caseDecision != null && StringUtils.isNotEmpty(caseDecision.getSeq())){
				this.caseDecisionService.recallNumber(caseDecision);
			}
			this.caseDecisionDao.deleteByCaseId(caseId);
		}else if(caseStage.equals(Global.CASE_STAGE_FINISH)){
			this.caseFinishDao.deleteByCaseId(caseId);
		}else if(caseStage.equals(Global.CASE_STAGE_HANDLE)){
			this.caseHandleDao.deleteByCaseId(caseId);		
			
			CaseHandlePunishLib punishLib = new CaseHandlePunishLib();
			punishLib.setCaseId(caseId);
			this.caseHandlePunishLibDao.deleteByCaseId(punishLib);
		}else if(caseStage.equals(Global.CASE_STAGE_INITIAL)){
			this.caseSettleDao.deleteByCaseId(caseId);
		}else if(caseStage.equals(Global.CASE_STAGE_NOTIFY)){
			//delete notify
			//recall no
			CaseNotify caseNotifyParam = new CaseNotify();
			caseNotifyParam.setCaseId(caseId);
			CaseNotify caseNotify = caseNotifyService.get(caseId);
			if(caseNotify != null && StringUtils.isNotEmpty(caseNotify.getSeq())){
				this.caseNotifyService.recallNumber(caseNotify);
			}
			this.caseNotifyDao.deleteByCaseId(caseId);
		}else if(caseStage.equals(Global.CASE_STAGE_SERIOUS)){
			this.caseSeriousDao.deleteByCaseId(caseId);
		}

		//delete case
		//this.updateStatus(tcase);
		
		//log
		User user = UserUtils.getUser();
		logger.info("delete case stage, caseId: {}, stage: {}, operator: {}", caseId, caseStage, user.getLoginName());
		
		return rst;
	}
	
}