/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.web;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wxjs.les.modules.act.utils.ProcessUtils;
import org.wxjs.les.modules.base.entity.ActTask; 
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.service.SystemService;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.task.service.CaseTaskService;
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseFinish;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.CaseSettle;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.service.CaseAttachService;
import org.wxjs.les.modules.tcase.service.CaseDecisionService;
import org.wxjs.les.modules.tcase.service.CaseFinishService;
import org.wxjs.les.modules.tcase.service.CaseHandleService;
import org.wxjs.les.modules.tcase.service.CaseNotifyService;
import org.wxjs.les.modules.tcase.service.CaseProcessService;
import org.wxjs.les.modules.tcase.service.CaseSettleService;
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
	private CaseHandleService caseHandleService;
	
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
	@RequestMapping(value = {"listTransfer"})
	public String listTransfer(Tcase tcase, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		tcase.setCaseTransfer("1"); //案源移交标记
		
		if(tcase.getAcceptDateFrom() == null){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, -180);
			tcase.setCreateDateFrom(cal.getTime()); 
		}
		
		if(tcase.getAcceptDateTo() == null){
			Calendar cal=Calendar.getInstance();
			tcase.setCreateDateTo(cal.getTime());			
		}
		
		Page<Tcase> page = tcaseService.findPage(new Page<Tcase>(request, response), tcase); 
		model.addAttribute("page", page);
		return "modules/tcase/tcaseListTransfer";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tcase tcase, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		tcase.setCaseTransfer("0");
		
		if(tcase.getAcceptDateFrom() == null){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, -180);
			tcase.setAcceptDateFrom(cal.getTime());
		}
		
		if(tcase.getAcceptDateTo() == null){
			Calendar cal=Calendar.getInstance();
			tcase.setAcceptDateTo(cal.getTime());			
		}
		
		Page<Tcase> page = tcaseService.findPage(new Page<Tcase>(request, response), tcase); 
		model.addAttribute("page", page);
		return "modules/tcase/tcaseList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(Tcase tcase, Model model) {
		if(tcase.getIsNewRecord()){
			tcase.setPartyType("单位");
			tcase.setAcceptDate(Calendar.getInstance().getTime());
			tcase.setPsnSex("男");
			//tcase.setCaseStage("10");
			//tcase.setCaseStageStatus("0");
		}
		model.addAttribute("tcase", tcase);
		return "modules/tcase/tcaseForm";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "infoTabTransfer")
	public String infoTabTransfer(CaseAct caseAct, Model model) {
		
		caseAct.setCaseTransfer("1");
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		logger.debug("businesskey:{}", businesskey);
		
		List<User> availableHandlers = this.getCaseHandler4Start(tcase);
		tcase.getCaseProcess().setAvailableHandlers(availableHandlers);	
		
		caseAct.setTcase(tcase);
		
		return "modules/tcase/tcaseInfoTabTransfer";
	}	
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "infoTab")
	public String infoTab(CaseAct caseAct, Model model) {
		
		caseAct.setCaseTransfer("0");
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		logger.debug("businesskey:{}", businesskey);
		
		List<User> availableHandlers = this.getCaseHandler4Start(tcase);
		tcase.getCaseProcess().setAvailableHandlers(availableHandlers);	
		
		caseAct.setTcase(tcase);
		
		return "modules/tcase/tcaseInfoTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "handleTab")
	public String handleTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		logger.debug("businesskey:{}", businesskey);
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		caseAct.setTcase(tcase);
		
		CaseHandle caseHandle = this.caseHandleService.get(tcase.getId());
		
		if(caseHandle==null){
			caseHandle = CaseHandle.getInstance(tcase);
		}
		
		model.addAttribute("caseHandle", caseHandle);
		
		return "modules/tcase/tcaseHandleTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "notifyTab")
	public String notifyTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		logger.debug("businesskey:{}", businesskey);
		
		caseAct.setTcase(tcase);
		
		CaseNotify caseNotify = this.caseNotifyService.get(tcase.getId());
		
		if(caseNotify==null){
			caseNotify = CaseNotify.getInstance(tcase);
		}
		
		model.addAttribute("caseNotify", caseNotify );
		
		return "modules/tcase/tcaseNotifyTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "decisionTab")
	public String decisionTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		logger.debug("businesskey:{}", businesskey);
		
		caseAct.setTcase(tcase);
		
		CaseDecision caseDecision = this.caseDecisionService.get(tcase.getId());
		
		if(caseDecision==null){
			caseDecision = CaseDecision.getInstance(tcase);
			
		}
		
		model.addAttribute("caseDecision", caseDecision);
		
		return "modules/tcase/tcaseDecisionTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "settleTab")
	public String settleTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		logger.debug("businesskey:{}", businesskey);
		
		caseAct.setTcase(tcase);
		
		CaseSettle caseSettle = this.caseSettleService.get(tcase.getId());
		
		if(caseSettle==null){
			caseSettle = CaseSettle.getInstance(tcase);
		}
		
		model.addAttribute("caseSettle", caseSettle);
		
		return "modules/tcase/tcaseSettleTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "finishTab")
	public String finishTab(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		logger.debug("businesskey:{}", businesskey);
		
		caseAct.setTcase(tcase);
		
		CaseFinish caseFinish = this.caseFinishService.get(tcase.getId());
		
		if(caseFinish==null){
			caseFinish = CaseFinish.getInstance(tcase);
		}
		
		model.addAttribute("caseFinish", caseFinish);
		
		return "modules/tcase/tcaseFinishTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toStartAcceptance")
	public String toStartAcceptance(CaseAct caseAct, Model model) {
		caseAct.setCaseTransfer("0");
		
		caseAct.setOperateType("start");
		
		Tcase tcase = new Tcase();
		
		tcase.setIsNewRecord(true);
		tcase.setPartyType("单位");
		tcase.setAcceptDate(Calendar.getInstance().getTime());
		tcase.setPsnSex("男");
		
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseStage(Global.CASE_STAGE_ACCEPTANCE);
		tcase.setCaseProcess(caseProcess);
		
		if("start".equals(caseAct.getOperateType())){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase);
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);			
		}
		
		caseAct.setTcase(tcase);
		
		return "modules/tcase/tcaseInfoTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toStartTransfer")
	public String toStartTransfer(CaseAct caseAct, Model model) {
		
		caseAct.setCaseTransfer("1");
		
		caseAct.setOperateType("start");
		
		Tcase tcase = new Tcase();
		
		tcase.setIsNewRecord(true);
		tcase.setPartyType("单位");
		tcase.setAcceptDate(Calendar.getInstance().getTime());
		tcase.setPsnSex("男");
		
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseStage(Global.CASE_STAGE_TRANSFER);
		tcase.setCaseProcess(caseProcess);
		
		if("start".equals(caseAct.getOperateType())){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase);
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);			
		}
		
		caseAct.setTcase(tcase);
		
		return "modules/tcase/tcaseInfoTabTransfer";
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
		
		List<User> availableHandlers = this.getCaseHandler4Handle(caseAct.getTaskId());
		tcase.getCaseProcess().setAvailableHandlers(availableHandlers);

		model.addAttribute("operateType", "handle");
		model.addAttribute("tcase", tcase);

		return "modules/tcase/tcaseInfoTab";
	}
	
	private List<User> getCaseHandler4Start(Tcase tcase){

		String caseStage = tcase.getCaseProcess().getCaseStage();
		
		String group = ProcessCommonUtils.getFirstTaskGroupByStage(caseStage);
		
		logger.debug("caseStage:{}, group:{}", caseStage, group);
		
		return this.getCaseHandlerByGroup(group);
	}
	
	private List<User> getCaseHandler4Handle(String taskId){
		
		List<User> list = Lists.newArrayList();
		
		ProcessUtils pu = new ProcessUtils();
		String group = pu.getNextTaskGroupText(taskId);
		
		logger.debug("group:{}", group);
		
		if(!StringUtils.isEmpty(group)){
			list = this.getCaseHandlerByGroup(group);
		}
		
		return list;
	}
	
	private List<User> getCaseHandlerByGroup(String group){
		return systemService.findUserByRoleEname(group);	
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
				availableHandlers = this.getCaseHandler4Start(tcase);
				tcase.getCaseProcess().setMultiple(true);			
			}else{
				availableHandlers = this.getCaseHandler4Handle(caseAct.getTaskId());	
				tcase.getCaseProcess().setMultiple(false);	
			}
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);	
			
			if(availableHandlers.size() > 0){
				//set default value
				tcase.getCaseProcess().setCaseHandler(availableHandlers.get(0).getLoginName());
				
				logger.debug("getCaseHandler():{}", tcase.getCaseProcess().getCaseHandler());
			}
			
			Task task = taskService.createTaskQuery().taskId(caseAct.getTaskId()).singleResult();
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
			
			actTask.setProcInsId(caseAct.getTcase().getCaseProcess().getProcInstId());
			
			actTask.setNextHandlers(tcase.getCaseProcess().getCaseHandler());
			
			//set NextConditionTexts to control the button display
			ProcessUtils pu = new ProcessUtils();
			actTask.setNextConditionTexts(pu.getConditionTexts(caseAct.getTaskId()));
			
			logger.debug("actTask.getNextHandlers():{}", actTask.getNextHandlers());
			model.addAttribute("actTask", actTask);			
		}
		
		//load process info
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseId(tcase.getId());
		List<CaseProcess> processlist = caseProcessService.findList(caseProcess);
		
		
		//fill task
		
		List<CaseProcess> punishProcesslist = Lists.newArrayList();
		List<CaseProcess> independentProcesslist = Lists.newArrayList();
		List<CaseProcess> transferProcesslist = Lists.newArrayList();
		
		for(CaseProcess process : processlist){
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

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAct)){
			return infoTab(caseAct, model);
		}
		Tcase tcase = caseAct.getTcase();
		tcaseService.save(tcase);
		
		model.addAttribute("operateType", caseAct.getOperateType());
		
		String businesskey = tcase.getId()+":"+tcase.getCaseProcess().getId();
		
		logger.debug("caseAct.getOperateType():{}, businesskey:{}", caseAct.getOperateType(), businesskey);
		
		
		addMessage(redirectAttributes, "保存案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?businesskey="+businesskey+"&operateType="+caseAct.getOperateType();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveAndStart")
	public String saveAndStart(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAct)){
			return infoTab(caseAct, model);
		}
		tcaseService.saveAndStartFlow(caseAct.getTcase());
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
	@RequestMapping(value = "saveTransfer")
	public String saveTransfer(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAct)){
			return infoTabTransfer(caseAct, model);
		}
		Tcase tcase = caseAct.getTcase();
		tcaseService.saveTransfer(tcase);
		
		model.addAttribute("operateType", caseAct.getOperateType());
		
		String businesskey = tcase.getId()+":"+tcase.getCaseProcess().getId();
		
		logger.debug("caseAct.getOperateType():{}, businesskey:{}", caseAct.getOperateType(), businesskey);
		
		
		addMessage(redirectAttributes, "保存案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTabTransfer?businesskey="+businesskey+"&operateType="+caseAct.getOperateType();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveAndStartTransfer")
	public String saveAndStartTransfer(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAct)){
			return infoTabTransfer(caseAct, model);
		}
		tcaseService.saveAndStartFlowTransfer(caseAct.getTcase());
		addMessage(redirectAttributes, "事件启动成功");
		return "redirect:"+Global.getAdminPath()+"/task/todo";
	}
	

	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "handletask")	
	public String handletask(ActTask actTask, Model model) {
		
		String userid = UserUtils.getUser().getLoginName();
		
		Map<String,Object> variables = actTask.getVariables();
		
		//设置处理结果
		ProcessUtils pu = new ProcessUtils();
		String variable = pu.getConditionVariable(actTask.getTaskId());
		
		logger.debug("handletask variable:{}", variable);
		
		variables.put(variable, actTask.getApprove());
		
		logger.debug("handletask actTask.getTaskid():{}", actTask.getTaskId());
		
		//设置下一关
		TaskDefinition nextTaskDef = pu.getNextTaskDefinition(actTask.getTaskId());
		String nextHandlersStr = actTask.getNextHandlers();
		if(!StringUtils.isEmpty(nextHandlersStr)){
			String[] strs = nextHandlersStr.split(",");
			if(strs.length==1){
				String group = pu.getNextTaskGroupText(actTask.getTaskId());
				variables.put(group+"Assignee", strs[0]);				
			}else if(strs.length>11){
				List<String> assigneeList = Lists.newArrayList();
				for(String str : strs){
					assigneeList.add(str);
				}
				variables.put("assigneeList", assigneeList);
			}
			
		}
		
		logger.debug("actTask.getApprove():{}", actTask.getApprove());
		
		StringBuffer comment = new StringBuffer();
		if("pass".equals(actTask.getApprove())){
			comment.append("[通过]");
		}else if("return".equals(actTask.getApprove())){
			comment.append("[退回]");
		}else if("cancel".equals(actTask.getApprove())){
			comment.append("[不通过]");
		}
		comment.append(actTask.getApproveOpinion());
		//add signature
		comment.append(Global.SignatureTag);
		comment.append(actTask.getSignature().getId());
		
		taskService.addComment(actTask.getTaskId(), actTask.getProcInsId(), comment.toString());
		
		taskService.claim(actTask.getTaskId(), userid);
		taskService.complete(actTask.getTaskId(), variables);
		
		//update stage status if necessary
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setProcInstId(actTask.getProcInsId());
		if("pass".equals(actTask.getApprove())){
			logger.debug("nextTaskDef==null:{}",(nextTaskDef==null));
			if(nextTaskDef == null){ //if nextTaskDef is null, it is the last userTask 
				caseProcess.setCaseStageStatus(Global.CASE_STAGE_STATUS_FINISHED);
				this.caseProcessService.updateStageStatus(caseProcess);
				
				//handle case transfer
				Tcase tcase = tcaseService.getCaseAndProcess(actTask.getBusinesskey());
				if(Global.CASE_STAGE_TRANSFER.equals(caseProcess.getCaseStage())){
					tcaseService.doTransfer(tcase);
				}
			}
		}else if("cancel".equals(actTask.getApprove())){
			caseProcess.setCaseStageStatus(Global.CASE_STAGE_STATUS_CANCELED);
			this.caseProcessService.updateStageStatus(caseProcess);
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

}