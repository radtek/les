/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.web;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wxjs.les.modules.act.service.ProcessService;
import org.wxjs.les.modules.base.dao.SignatureLibDao;
import org.wxjs.les.modules.base.entity.ActTask; 
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.entity.SignatureLib;
import org.wxjs.les.modules.base.service.SignatureService;
import org.wxjs.les.modules.base.utils.DocumentHeadUtils;
import org.wxjs.les.modules.message.DbMessageSender;
import org.wxjs.les.modules.message.MessageSender;
import org.wxjs.les.modules.sys.utils.DictUtils;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.common.utils.excel.ExportExcel;
import org.wxjs.les.modules.sys.entity.Dict;
import org.wxjs.les.modules.sys.entity.Role;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.service.SystemService;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.task.service.CaseTaskService;
import org.wxjs.les.modules.task.utils.CaseActUtils;
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.CaseCancel;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseFinish;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib;
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.CaseSerious;
import org.wxjs.les.modules.tcase.entity.CaseSettle;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseInitialExport;
import org.wxjs.les.modules.tcase.service.CaseAttachService;
import org.wxjs.les.modules.tcase.service.CaseCancelService;
import org.wxjs.les.modules.tcase.service.CaseDecisionService;
import org.wxjs.les.modules.tcase.service.CaseFinishService;
import org.wxjs.les.modules.tcase.service.CaseHandlePunishLibService;
import org.wxjs.les.modules.tcase.service.CaseHandleService;
import org.wxjs.les.modules.tcase.service.CaseNotifyService;
import org.wxjs.les.modules.tcase.service.CaseProcessService;
import org.wxjs.les.modules.tcase.service.CaseSeriousService;
import org.wxjs.les.modules.tcase.service.CaseSettleService;
import org.wxjs.les.modules.tcase.service.CaseTransferService;
import org.wxjs.les.modules.tcase.service.TcaseService;
import org.wxjs.les.modules.tcase.utils.ProcessCommonUtils;

import com.google.common.collect.Lists;

/**
 * 案件Controller
 * @author GLQ
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/case/tcase")
public class TcaseController extends BaseController {
	
	private static final String[] NoneZhiduiZhanRoles = {"jld", "fgcblr", "fgcfzr", "csblr", "csfzr"}; //非支队、站角色
	
	@Autowired
	private SystemService systemService;

	@Autowired
	private TcaseService tcaseService;
	
	@Autowired
	private CaseDecisionService caseDecisionService;
	
	@Autowired
	private CaseNotifyService caseNotifyService;
	
	@Autowired
	private CaseSettleService caseSettleService;
	
	@Autowired
	private CaseFinishService caseFinishService;
	
	@Autowired
	private CaseSeriousService caseSeriousService;
	
	@Autowired
	private CaseCancelService caseCancelService;
	
	@Autowired
	private CaseHandleService caseHandleService;
	
	@Autowired
	private CaseHandlePunishLibService caseHandlePunishLibService;
	
	@Autowired
	private CaseTransferService caseTransferService;
	
	@Autowired
	private CaseProcessService caseProcessService;
	
	@Autowired
	private CaseAttachService caseAttachService;
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	private CaseTaskService actTaskService;
	
	@Autowired
	private SignatureService signatureService;
	
	@Autowired
	private SignatureLibDao signatureLibDao;
	
	@Autowired
	ProcessService processService;
	
	@ModelAttribute
	public Tcase get(@RequestParam(required=false) String id) {
		Tcase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tcaseService.get(id);
		}
		if (entity == null){
			entity = new Tcase();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tcase tcase, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		tcase.setCaseTransfer("0");
		
		//set data range by user org
		this.setHandleOrg(tcase);
		
		if(tcase.getAcceptDateFrom() == null){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, -180);
			tcase.setAcceptDateFrom(cal.getTime());
		}
		
		if(tcase.getAcceptDateTo() == null){
			Calendar cal=Calendar.getInstance();
			tcase.setAcceptDateTo(cal.getTime());			
		}
		
		Page<Tcase> page = null; 
		if(tcase.getMyCaseFlag()){
			page = tcaseService.findPage4My(new Page<Tcase>(request, response), tcase); 
		}else{
			page = tcaseService.findPage(new Page<Tcase>(request, response), tcase); 
		}
		model.addAttribute("page", page);
		return "modules/tcase/tcaseList";
	}	
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"mylist"})
	public String mylist(Tcase tcase, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		tcase.setCaseTransfer("0");
		
		tcase.setMyCaseFlag(true);
		
		Page<Tcase> page = tcaseService.findPage4My(new Page<Tcase>(request, response), tcase); 
		model.addAttribute("page", page);
		return "modules/tcase/mycaseList";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"query"})
	public String query(Tcase tcase, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(StringUtils.isEmpty(tcase.getStatus())){
			//tcase.setStatus(Global.CASE_STATUS_FINISHED);
		}
		
		tcase.setUnfinishedFlag("0");
		
		//set data range by user org
		this.setHandleOrg(tcase);
		
		List<Dict> yearList = Lists.newArrayList();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		for(int i=currentYear;i>2017;i--){
			Dict dict = new Dict();
			dict.setValue(i+"");
			dict.setLabel(i+"");
			yearList.add(dict);
		}
		
		model.addAttribute("yearList", yearList);

		Page<Tcase> page = tcaseService.findPage(new Page<Tcase>(request, response), tcase); 
		
		for(Tcase entity : page.getList()){
			CaseAttach caseAttach = new CaseAttach();
			caseAttach.setCaseId(entity.getId());
			caseAttach.setMandatory("1");
			List<CaseAttach> attaches = this.caseAttachService.findList(caseAttach);
			int total = attaches.size();
			int generated = 0;
			StringBuffer detailBuffer = new StringBuffer();
			for(CaseAttach attach: attaches){
				detailBuffer.append(",");
				if(StringUtils.isEmpty(attach.getFilepath())){
					detailBuffer.append(attach.getAttachType()).append(Global.AttachInexistsFlag);
				}else{
					detailBuffer.append(attach.getAttachType()).append(Global.AttachExistsFlag);
					generated++;
				}
			}
			entity.setAttachLocalProgress(generated + "/" + total);
			if(detailBuffer.length()>0){
				entity.setAttachLocalDetail(detailBuffer.substring(1));
			}
		}
		
		model.addAttribute("page", page);
		return "modules/tcase/queryCaseList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(Tcase tcase, Model model) {
		if(tcase.getIsNewRecord()){
			tcase.setPartyType(Global.PartyTypeOrg);
			tcase.setAcceptDate(Calendar.getInstance().getTime());
			tcase.setPsnSex("男");
			//tcase.setCaseStage("10");
			//tcase.setCaseStageStatus("0");
		}
		model.addAttribute("tcase", tcase);
		return "modules/tcase/tcaseForm";
	}	
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "infoTab")
	public String infoTab(CaseAct caseAct, Model model) {
		
		caseAct.setCaseTransfer("0");
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		return "modules/tcase/tcaseInfoTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "acceptanceTab")
	public String acceptanceTab(CaseAct caseAct, Model model) {
		
		caseAct.setCaseTransfer("0");
		
		String businesskey = caseAct.getBusinesskey();
		String[] strs = businesskey.split(":");
		
		Tcase tcase = tcaseService.getCaseAndProcess(strs[0], Global.CASE_STAGE_ACCEPTANCE);
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseAcceptanceTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "initialTab")
	public String initialTab(CaseAct caseAct, Model model) {
		
		caseAct.setCaseTransfer("0");
		
		String businesskey = caseAct.getBusinesskey();
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		Tcase tcase = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_INITIAL);
		
		//获取前一环节案情摘要
		Tcase tcasePrevious = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_ACCEPTANCE);
		if(StringUtils.isEmpty(tcase.getCaseProcess().getCaseSummary())){
			//copy from previous
			tcase.getCaseProcess().setCaseSummary(tcasePrevious.getCaseProcess().getCaseSummary());
			tcase.getCaseProcess().setCaseHandler(tcasePrevious.getCaseProcess().getCaseHandler());
			tcaseService.saveProcess(tcase);
		}
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseInitialTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "handleTab")
	public String handleTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		Tcase tcase = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_HANDLE);
		
		//获取前一环节案情摘要
		Tcase tcasePrevious = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_INITIAL);
		if(StringUtils.isEmpty(tcase.getCaseProcess().getCaseSummary())){
			//copy from previous
			tcase.getCaseProcess().setCaseSummary(tcasePrevious.getCaseProcess().getCaseSummary());
			tcase.getCaseProcess().setCaseHandler(tcasePrevious.getCaseProcess().getCaseHandler());
			tcaseService.saveProcess(tcase);
		}
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		CaseHandle caseHandle = this.caseHandleService.get(tcase.getId());
		
		if(caseHandle==null){
			caseHandle = CaseHandle.getInstance(tcase);
		}
		
		model.addAttribute("caseHandle", caseHandle);
		
		CaseHandlePunishLib caseHandlePunishLib = new CaseHandlePunishLib();
		caseHandlePunishLib.setCaseId(tcase.getId());
		
		List<CaseHandlePunishLib> libList = this.caseHandlePunishLibService.findList(caseHandlePunishLib);
		
		model.addAttribute("caseHandlePunishLib", caseHandlePunishLib);
		model.addAttribute("libList", libList);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseHandleTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "notifyTab")
	public String notifyTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		Tcase tcase = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_NOTIFY);
		
		/*
		//获取前一环节案情摘要
		Tcase tcasePrevious = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_HANDLE);
		if(StringUtils.isEmpty(tcase.getCaseProcess().getCaseSummary())){
			//copy from previous
			tcase.getCaseProcess().setCaseSummary(tcasePrevious.getCaseProcess().getCaseSummary());
			tcase.getCaseProcess().setCaseHandler(tcasePrevious.getCaseProcess().getCaseHandler());
			tcaseService.saveProcess(tcase);
		}		
		*/
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		//add caseHandle for reference
		model.addAttribute("caseHandle", this.caseHandleService.get(tcase.getId()));
		
		CaseNotify caseNotify = this.caseNotifyService.get(tcase.getId());
		
		if(caseNotify==null){
			caseNotify = CaseNotify.getInstance(tcase);
		}
		
		model.addAttribute("caseNotify", caseNotify );
		
		List<Dict> documentHeads = DocumentHeadUtils.getDocumentHeads(tcase.getAreaId(), tcase.getHandleOrg(), "notify");
		
		model.addAttribute("documentHeads", documentHeads);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseNotifyTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "decisionTab")
	public String decisionTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		Tcase tcase = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_DECISION);
		
		//获取前一环节处理人
		Tcase tcasePrevious = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_NOTIFY);
		if(StringUtils.isEmpty(tcase.getCaseProcess().getCaseHandler())){
			//copy from previous
			tcase.getCaseProcess().setCaseHandler(tcasePrevious.getCaseProcess().getCaseHandler());
			tcaseService.saveProcess(tcase);
		}
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		//add caseHandle for reference
		model.addAttribute("caseHandle", this.caseHandleService.get(tcase.getId()));
		
		CaseDecision caseDecision = this.caseDecisionService.get(tcase.getId());
		
		if(caseDecision==null){
			caseDecision = CaseDecision.getInstance(tcase);
			
		}
		
		model.addAttribute("caseDecision", caseDecision);
		
		List<Dict> documentHeads = DocumentHeadUtils.getDocumentHeads(tcase.getAreaId(), tcase.getHandleOrg(), "decision");
		
		model.addAttribute("documentHeads", documentHeads);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseDecisionTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "settleTab")
	public String settleTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		Tcase tcase = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_SETTLE);
		
		//获取前一环节案情摘要
		Tcase tcasePrevious = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_HANDLE);
		if(StringUtils.isEmpty(tcase.getCaseProcess().getCaseSummary())){
			//copy from previous
			tcase.getCaseProcess().setCaseSummary(tcasePrevious.getCaseProcess().getCaseSummary());
			tcase.getCaseProcess().setCaseHandler(tcasePrevious.getCaseProcess().getCaseHandler());
			tcaseService.saveProcess(tcase);
		}		
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		//add caseHandle for reference
		model.addAttribute("caseHandle", this.caseHandleService.get(tcase.getId()));
		
		CaseSettle caseSettle = this.caseSettleService.get(tcase.getId());
		
		if(caseSettle==null){
			caseSettle = CaseSettle.getInstance(tcase);
		}
		
		model.addAttribute("caseSettle", caseSettle);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseSettleTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "finishTab")
	public String finishTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		Tcase tcase = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_FINISH);
		
		//获取前一环节案情摘要
		Tcase tcasePrevious = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_SETTLE);
		if(StringUtils.isEmpty(tcase.getCaseProcess().getCaseSummary())){
			//copy from previous
			tcase.getCaseProcess().setCaseSummary(tcasePrevious.getCaseProcess().getCaseSummary());
			tcase.getCaseProcess().setCaseHandler(tcasePrevious.getCaseProcess().getCaseHandler());
			tcaseService.saveProcess(tcase);
		}
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		CaseFinish caseFinish = this.caseFinishService.get(tcase.getId());
		
		if(caseFinish==null){
			caseFinish = CaseFinish.getInstance(tcase);
		}
		
		model.addAttribute("caseFinish", caseFinish);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseFinishTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "seriousTab")
	public String seriousTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		logger.debug("businesskey:{}", businesskey);
		
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		Tcase tcase = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_SERIOUS);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		//add caseHandle for reference
		//model.addAttribute("caseHandle", this.caseHandleService.get(tcase.getId()));
		
		CaseSerious caseSerious = this.caseSeriousService.get(tcase.getId());
		
		if(caseSerious==null){
			caseSerious = CaseSerious.getInstance(tcase);
			
			//copy fact
			CaseHandle caseHandle = caseHandleService.get(caseSerious.getCaseId());

			caseSerious.setCaseSummary(caseHandle.getFact());
			
			//copy opinion
			CaseProcess caseProcess = new CaseProcess();
			caseProcess.setCaseId(caseHandle.getCaseId());
			caseProcess.setCaseStage(Global.CASE_STAGE_HANDLE);
			List<CaseProcess> caseProcessList = caseProcessService.findList(caseProcess);
			
			String procInstId = "";
			if(caseProcessList.size()>0){
				procInstId = caseProcessList.get(0).getProcInsId();
			}
			
			String opinion = "";
			if(StringUtils.isNotEmpty(procInstId)){
				Signature signatureParam = new Signature(false);
				signatureParam.setProcInstId(procInstId);
				signatureParam.setTaskName("办案人意见");
				List<Signature> signatureList = signatureService.findList4Export(signatureParam);
				for(Signature sig : signatureList){
					if(sig.getApproveOpinion().length()>opinion.length()){
						opinion = sig.getApproveOpinion();
					}
				}
				caseSerious.setPunishProposal(opinion);
			}			
		}
		
		model.addAttribute("caseSerious", caseSerious);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseSeriousTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "cancelTab")
	public String cancelTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		logger.debug("businesskey:{}", businesskey);
		
		String[] strs = businesskey.split(":");
		String caseId = strs[0];
		Tcase tcase = tcaseService.getCaseAndProcess(caseId, Global.CASE_STAGE_CANCEL);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);				
		}
		
		caseAct.setTcase(tcase);
		
		//add caseHandle for reference
		//model.addAttribute("caseHandle", this.caseHandleService.get(tcase.getId()));
		
		CaseCancel caseCancel = this.caseCancelService.get(tcase.getId());
		
		if(caseCancel==null){
			caseCancel = CaseCancel.getInstance(tcase);
		}
		
		model.addAttribute("caseCancel", caseCancel);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseCancelTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toStartAcceptance")
	public String toStartAcceptance(CaseAct caseAct, Model model) {
		caseAct.setCaseTransfer("0");
		
		caseAct.setOperateType("start");
		
		Tcase tcase = new Tcase();
		
		this.setHandleOrg(tcase);
		
		tcase.setIsNewRecord(true);
		tcase.setPartyType(Global.PartyTypeOrg);
		tcase.setAcceptDate(Calendar.getInstance().getTime());
		tcase.setPsnSex("男");
		
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseStage(Global.CASE_STAGE_ACCEPTANCE);
		tcase.setCaseProcess(caseProcess);
		
		if("start".equals(caseAct.getOperateType())){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);			
		}
		
		caseAct.setTcase(tcase);
		
		return "modules/tcase/tcaseInfoTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toStart")
	public String toStart(CaseAct caseAct, Model model) {
		caseAct.setOperateType("start");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?"+caseAct.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toView")
	public String toView(CaseAct caseAct, Model model) {
		caseAct.setOperateType("view");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?"+caseAct.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toHandle")
	public String toHandle(CaseAct caseAct, Model model) {

		String businesskey = caseAct.getId();
		//String caseId = getCaseIdFromBusinessKey(businessKey);
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		List<User> availableHandlers = this.getCaseHandler4Handle(caseAct.getTaskId(), tcase.getHandleOrg(), tcase.getAreaId());
		tcase.getCaseProcess().setAvailableHandlers(availableHandlers);

		model.addAttribute("operateType", "handle");
		model.addAttribute("tcase", tcase);

		return "modules/tcase/tcaseInfoTab";
	}
	
	protected List<User> getCaseHandler4Start(CaseProcess caseProcess, String handleOrg, String area){

		String caseStage = caseProcess.getCaseStage();
		
		User user = UserUtils.getUser();
		
		String roleEnname = user.getRoleEnname();
		
		String group = ProcessCommonUtils.getFirstTaskGroupByStage(caseStage, roleEnname);
		
		logger.debug("caseStage:{}, group:{}, roleEnname:{}", caseStage, group, roleEnname);
		
		return this.getCaseHandlerByGroup(group, handleOrg, area);
	}
	
	protected List<User> getCaseHandler4Handle(String taskId, String handleOrg, String area){
		
		List<User> list = Lists.newArrayList();

		String group = processService.getNextTaskGroupText(taskId);
		
		logger.debug("group:{}", group);
		
		if(!StringUtils.isEmpty(group)){
			list = this.getCaseHandlerByGroup(group, handleOrg, area);
		}
		
		return list;
	}
	
	private List<User> getCaseHandlerByGroup(String group, String handleOrg, String area){
		List<User> rst = Lists.newArrayList();
		
		List<User> list = systemService.findUserByRoleEname(group);	
		
		//filter by office
		/*
		if(!group.contains("syblr") && !group.contains("syfzr")){
			rst.addAll(list);
		}else{
			User user = UserUtils.getUser();
			String officeId = user.getOffice().getId();
			if(officeId.startsWith("01") || officeId.startsWith("02")){
				rst.addAll(list);
			}else{
				String deptId = officeId.substring(0, 2);
				for(User e : list){
					if(e.getOffice().getId().startsWith(deptId)){
						rst.add(e);
					}
				}
			}			
		}
		*/
		
		if(Arrays.asList(NoneZhiduiZhanRoles).contains(group)){
			//rst.addAll(list);
			for(User e : list){
				if(e.getOffice().getAreaId().equals(area)){
					rst.add(e);
				}
			}
		}else{
			logger.debug("handleOrg:{}, area:{}", handleOrg, area);
			for(User e : list){
				logger.debug("e.getOffice().getType():{},e.getOffice().getAreaId():{}", e.getOffice().getType(), e.getOffice().getAreaId());
				
				if(e.getOffice().getType().equals(handleOrg) && e.getOffice().getAreaId().equals(area)){
					rst.add(e);
				}
			}			
		}
		
		return rst;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "attachTab")
	public String attachTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		//List<User> availableHandlers = this.getCaseHandler4Handle(caseAct.getTaskId());
		//tcase.getCaseProcess().setAvailableHandlers(availableHandlers);		
		
		caseAct.setTcase(tcase);
		
		//load attach info
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setCaseId(tcase.getId());
		List<CaseAttach> attachlist = caseAttachService.findList(caseAttach);
		model.addAttribute("attachlist", attachlist);
		return "modules/tcase/tcaseAttachTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "processTab")
	public String processTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);	
		
		if(!StringUtils.isEmpty(caseAct.getTaskId())){
			List<User> availableHandlers = Lists.newArrayList();
			if("0".equals(tcase.getCaseProcess().getCaseStageStatus())){
				availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess(), tcase.getHandleOrg(), tcase.getAreaId());
				tcase.getCaseProcess().setMultiple(true);			
			}else{
				availableHandlers = this.getCaseHandler4Handle(caseAct.getTaskId(), tcase.getHandleOrg(), tcase.getAreaId());	
				tcase.getCaseProcess().setMultiple(false);	
			}
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);	
			
			if(availableHandlers.size() == 1 ){
				//set default value
				tcase.getCaseProcess().setCaseHandler(availableHandlers.get(0).getLoginName());
				
				logger.debug("getCaseHandler():{}", tcase.getCaseProcess().getCaseHandler());
			}
			
			Task task = taskService.createTaskQuery().taskId(caseAct.getTaskId()).singleResult();
			
			logger.debug("task.getExecutionId():{}", task!=null?task.getExecutionId():"");
			
			caseAct.setTask(task);			
		}
		
		caseAct.setTcase(tcase);
		
		
		model.addAttribute("tcase", tcase);
		
		if(Global.OperateType_Handle.equals(caseAct.getOperateType())){
			ActTask actTask = new ActTask();
			
			actTask.setBusinesskey(businesskey);
			
			actTask.initialSignature();
			
			actTask.setTaskId(caseAct.getTaskId());
			if(caseAct.getTask()!=null){
				actTask.setTaskName(caseAct.getTask().getName());
			}
			
			actTask.setProcInsId(caseAct.getTcase().getCaseProcess().getProcInsId());
			
			actTask.setNextHandlers(tcase.getCaseProcess().getCaseHandler());
			
			//set NextConditionTexts to control the button display
			actTask.setNextConditionTexts(processService.getConditionTexts(caseAct.getTaskId()));
			
			logger.debug("actTask.getNextHandlers():{}", actTask.getNextHandlers());
			model.addAttribute("actTask", actTask);			
		}
		
		//load process info
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseId(tcase.getId());
		List<CaseProcess> processlist = caseProcessService.findList(caseProcess);
		
		List<CaseProcess> punishProcesslist = Lists.newArrayList();
		List<CaseProcess> independentProcesslist = Lists.newArrayList();
		List<CaseProcess> transferProcesslist = Lists.newArrayList();
		
		for(CaseProcess process : processlist){
			//fill task
			if(StringUtils.isNotEmpty(process.getProcInsId())){
				//Task task = taskService.createTaskQuery().processInstanceId(process.getProcInsId()).active().singleResult();
				//Task task = taskService.createTaskQuery().processInstanceId(process.getProcInsId()).singleResult();
				/*
				String executionId = process.getProcInsId();
				if(task!=null){
					executionId = task.getExecutionId();
				}
				*/
				String executionId = process.getProcInsId();
				process.setExecutionId(executionId);
			}
			
			if(Global.CASE_STAGE_SERIOUS.equals(process.getCaseStage()) 
			|| Global.CASE_STAGE_CANCEL.equals(process.getCaseStage())){
				independentProcesslist.add(process);
			}else if(Global.CASE_STAGE_TRANSFER.equals(process.getCaseStage())){
				transferProcesslist.add(process);
			}else{
				punishProcesslist.add(process);
			}
		}
		
		model.addAttribute("processlist", punishProcesslist);
		model.addAttribute("independentProcesslist", independentProcesslist);
		model.addAttribute("transferProcesslist", transferProcesslist);
		
		return "modules/tcase/tcaseProcessTab";
	}
	
	protected void prepare4Approve(CaseAct caseAct, Model model){
		String businesskey = caseAct.getBusinesskey();
		String[] strs = businesskey.split(":");
		CaseProcess process = null;
		if(strs.length > 1){
			String caseProcessId = strs[1];
			process = caseProcessService.get(caseProcessId);
		}else{
			return;
		}
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);	
		
		if(!StringUtils.isEmpty(caseAct.getTaskId())){
			List<User> availableHandlers = Lists.newArrayList();
			if("0".equals(process.getCaseStageStatus())){
				availableHandlers = this.getCaseHandler4Start(process, tcase.getHandleOrg(), tcase.getAreaId());
				process.setMultiple(true);			
			}else{
				availableHandlers = this.getCaseHandler4Handle(caseAct.getTaskId(), tcase.getHandleOrg(), tcase.getAreaId());	
				process.setMultiple(false);	
			}
			process.setAvailableHandlers(availableHandlers);	
			
			if(availableHandlers.size() == 1 ){
				//set default value
				process.setCaseHandler(availableHandlers.get(0).getLoginName());
				
				logger.debug("getCaseHandler():{}", process.getCaseHandler());
			}
			
			Task task = taskService.createTaskQuery().taskId(caseAct.getTaskId()).singleResult();
			
			logger.debug("task.getExecutionId():{}", task!=null?task.getExecutionId():"");
			
			caseAct.setTask(task);	
			//the process being handled
			model.addAttribute("handlingProcess", process);
		}
		
		if(Global.OperateType_Handle.equals(caseAct.getOperateType())){
			ActTask actTask = new ActTask();
			
			actTask.setBusinesskey(businesskey);
			
			actTask.initialSignature();
			
			actTask.setTaskId(caseAct.getTaskId());
			if(caseAct.getTask()!=null){
				actTask.setTaskName(caseAct.getTask().getName());
			}
			
			actTask.setProcInsId(process.getProcInsId());
			
			actTask.setNextHandlers(process.getCaseHandler());
			
			//set NextConditionTexts to control the button display
			actTask.setNextConditionTexts(processService.getConditionTexts(caseAct.getTaskId()));
			
			logger.debug("actTask.getNextHandlers():{}", actTask.getNextHandlers());
			model.addAttribute("actTask", actTask);			
		}
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAct)){
			return infoTab(caseAct, model);
		}
		Tcase tcase = caseAct.getTcase();
		tcaseService.save(tcase);
		
		model.addAttribute("operateType", caseAct.getOperateType());
		
		String businesskey = tcase.getId();
		
		logger.debug("caseAct.getOperateType():{}, businesskey:{}", caseAct.getOperateType(), businesskey);
		
		
		addMessage(redirectAttributes, "保存案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?businesskey="+businesskey+"&operateType="+caseAct.getOperateType();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveProcess")
	public String saveProcess(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		Tcase tcase = caseAct.getTcase();
		
		logger.debug("caseAct.getTcase().getCaseProcess().getCaseHandler():{}", caseAct.getTcase().getCaseProcess().getCaseHandler());
		
		tcaseService.saveProcess(tcase);
		
		model.addAttribute("operateType", caseAct.getOperateType());
		
		String businesskey = tcase.getId();
		
		addMessage(redirectAttributes, "保存成功");
		String tabControl = this.getTabControlByStage(tcase.getCaseProcess().getCaseStage());
		return "redirect:"+Global.getAdminPath()+ tabControl+"?businesskey="+businesskey+"&operateType="+caseAct.getOperateType();
	}
	
	private String getTabControlByStage(String caseStage){
		return CaseActUtils.getTabControl(caseStage);
	}
	
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveAndStart")
	public String saveAndStart(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAct)){
			return infoTab(caseAct, model);
		}
		//tcaseService.saveAndStartFlow(caseAct.getTcase());
		
		logger.debug("caseAct.getTcase().getCaseProcess().getCaseHandler():{}", caseAct.getTcase().getCaseProcess().getCaseHandler());
		
		tcaseService.saveProcess(caseAct.getTcase());
		tcaseService.startWorkflow(caseAct.getTcase());
		addMessage(redirectAttributes, "事件启动成功");
		return "redirect:"+Global.getAdminPath()+"/task/todo";
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "startWorkFlow")
	public String startWorkFlow(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		tcaseService.startWorkflow(caseAct.getTcase());
		addMessage(redirectAttributes, "流程启动成功");
		return "redirect:"+Global.getAdminPath()+"/task/todo";
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "handletask")	
	public String handletask(ActTask actTask, Model model, RedirectAttributes redirectAttributes) {
		
		String userid = UserUtils.getUser().getLoginName();
		
		Map<String,Object> variables = actTask.getVariables();
		
		//设置处理结果
		String variable = processService.getConditionVariable(actTask.getTaskId());
		
		logger.debug("handletask variable:{}", variable);
		
		variables.put(variable, actTask.getApprove());
		
		logger.debug("handletask actTask.getTaskid():{}", actTask.getTaskId());
		
		//设置下一关
		TaskDefinition nextTaskDef = processService.getNextTaskDefinition(actTask.getTaskId());
		String nextHandlersStr = actTask.getNextHandlers();
		if(!StringUtils.isEmpty(nextHandlersStr)){
			String[] strs = nextHandlersStr.split(",");
			if(strs.length==1){
				String group = processService.getNextTaskGroupText(actTask.getTaskId());
				variables.put(group+"Assignee", strs[0]);				
			}else if(strs.length>1){
				List<String> assigneeList = Lists.newArrayList();
				for(String str : strs){
					assigneeList.add(str);
				}
				variables.put("assigneeList", assigneeList);
			}
			
		}
		
		logger.debug("actTask.getApprove():{}", actTask.getApprove());
		
		String signatureStr = "";
		
		StringBuffer comment = new StringBuffer();
		if("pass".equals(actTask.getApprove())){
			comment.append("[通过]");
			
			//check whether signature exists
			SignatureLib signatureLibParam = new SignatureLib();
			signatureLibParam.setUser(UserUtils.getUser());
			SignatureLib signatureLib = signatureLibDao.get(signatureLibParam);

			if(signatureLib!=null && StringUtils.isNotEmpty(signatureLib.getSignature())){
				signatureStr = signatureLib.getSignature();
			}else{
				addMessage(redirectAttributes, "操作失败！请先设置您的签名！");
				
				logger.debug("approve fail, signature not found, loginName:{}", userid);
				
				return "redirect:"+Global.getAdminPath()+"/task/todo";
			}
			
		}else if("return".equals(actTask.getApprove())){
			comment.append("[退回]");
		}else if("cancel".equals(actTask.getApprove())){
			comment.append("[不通过]");
		}
		comment.append(actTask.getApproveOpinion());
		//add signature
		//comment.append(Global.SignatureTag);
		//comment.append(actTask.getSignature().getId());
		
		taskService.addComment(actTask.getTaskId(), actTask.getProcInsId(), comment.toString());
		
		taskService.claim(actTask.getTaskId(), userid);
		taskService.complete(actTask.getTaskId(), variables);
		
		//update stage status if necessary
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setProcInsId(actTask.getProcInsId());
		if("pass".equals(actTask.getApprove())){
			
			//update opinion to signature
			Signature signature = new Signature(true);
			signature.setTitle(Signature.DefaultTitle);
			
			signature.setSignature(signatureStr);
			signatureService.save(signature);
			
			signature.setProcInstId(actTask.getProcInsId());
			signature.setTaskId(actTask.getTaskId());
			signature.setTaskName(actTask.getTaskName());
			signature.setApproveOpinion(actTask.getApproveOpinion());
			signatureService.updateOpinion(signature);
			
			logger.debug("nextTaskDef==null:{}",(nextTaskDef==null));
			if(nextTaskDef == null){ //if nextTaskDef is null, it is the last userTask 
				caseProcess.setCaseStageStatus(Global.CASE_STAGE_STATUS_FINISHED);
				this.caseProcessService.updateStageStatus(caseProcess);
				
				//handle case transfer
				Tcase tcase = tcaseService.getCaseAndProcess(actTask.getBusinesskey());
				logger.debug("actTask.getBusinesskey():{}",(actTask.getBusinesskey()));
				if(Global.CASE_STAGE_TRANSFER.equals(tcase.getCaseProcess().getCaseStage())){
					logger.debug("tcase.getCaseProcess().getCaseStage():{}",(tcase.getCaseProcess().getCaseStage()));
					caseTransferService.doTransfer(tcase);
				}
			}
			
			//send message
			MessageSender sender = new DbMessageSender();
			Tcase tcase = tcaseService.getCaseAndProcess(actTask.getBusinesskey());
			try {
				sender.send(nextHandlersStr, "综合行政执法系统中您有1个待办事项。项目名称："+tcase.getCaseCause()
				+", 事项："+DictUtils.getDictLabel(tcase.getCaseProcess().getCaseStage(), "case_stage", "")+"。");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("cancel".equals(actTask.getApprove())){
			//update case process status
			caseProcess.setCaseStageStatus(Global.CASE_STAGE_STATUS_CANCELED);
			this.caseProcessService.updateStageStatus(caseProcess);
			
			//update case status
			String businesskey = actTask.getBusinesskey();
			String[] strs = businesskey.split(":");
			if(strs.length>0){
				String caseId = strs[0];
				Tcase tcase = new Tcase();
				tcase.setId(caseId);
				tcase.setStatus(Global.CASE_STATUS_CANCELED);
				this.tcaseService.updateStatus(tcase);
			}

		}		
		
		return "redirect:"+Global.getAdminPath()+"/task/todo";
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(Tcase tcase, RedirectAttributes redirectAttributes) {
		tcaseService.delete(tcase);
		addMessage(redirectAttributes, "删除案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?repage";
	}
	
	@RequestMapping(value = "exportPDF")
	public String exportPDF(CaseAct caseAct, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		Tcase tcase = tcaseService.get(caseAct.getTcase().getId());
		
		CaseProcess caseProcess = this.caseProcessService.get(caseAct.getTcase().getId(), Global.CASE_STAGE_INITIAL);
		
		tcase.setCaseProcess(caseProcess);
		
		try {
            String fileName = "案件立案审批表"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseInitialExport export = new CaseInitialExport(tcase);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?"+caseAct.getParamUri();
	}
	
	@RequestMapping(value = "exportExcel")
	public void exportExcel(Tcase tcase,  HttpServletRequest request, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		try {
            String fileName = "案件统计汇总表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Tcase> page = tcaseService.findPage(new Page<Tcase>(request, response, -1), tcase);
    		new ExportExcel("案件统计汇总表", Tcase.class).setDataList(page.getList()).write(response, fileName).dispose();
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
	}
	
	@RequestMapping(value = "upload")
	public String upload(Tcase tcase, HttpServletRequest request, HttpServletResponse response, Model model){
		
		tcaseService.upload(tcase.getCaseIds());
		
		model.addAttribute("tcase", tcase);
		
		return "redirect:"+Global.getAdminPath()+"/case/tcase/query?repage";
	}
	
	@RequestMapping(value = "deleteCase")
	public String deleteCase(String caseId, HttpServletRequest request, HttpServletResponse response, Model model){
		
		tcaseService.deleteCase(caseId);
		
		return "redirect:"+Global.getAdminPath()+"/case/tcase/list?repage";
	}
	
	@RequestMapping(value = "deleteCaseStage")
	public String deleteCaseStage(String caseId, String caseStage, HttpServletRequest request, HttpServletResponse response, Model model){
		
		tcaseService.deleteCaseStage(caseId, caseStage);
		
		return "redirect:"+Global.getAdminPath()+"/case/tcase/list?repage";
	}
	
	protected void setHandleOrg(Tcase tcase){
		User user = UserUtils.getUser();
		String orgId = user.getOffice().getType();
		
		logger.debug("orgId:{}", orgId);
		
		if(orgId.startsWith("02")){
			//no filter
		}else if(orgId.startsWith("03")){
			tcase.setHandleOrg("03");
		}else if(orgId.startsWith("04")){
			tcase.setHandleOrg("04");
		}else{
			tcase.setHandleOrg("01");
		}
	}

}