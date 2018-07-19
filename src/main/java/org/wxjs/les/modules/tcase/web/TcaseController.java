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
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.service.SystemService;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.task.service.CaseTaskService;
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.service.CaseAttachService;
import org.wxjs.les.modules.tcase.service.CaseProcessService;
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
	private CaseAttachService caseAttachService;
	
	@Autowired
	private CaseProcessService caseProcessService;
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	HistoryService historyService;
	
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
	@RequestMapping(value = "infoTab")
	public String infoTab(Tcase tcase, Model model) {
		if(tcase.getIsNewRecord()){
			tcase.setPartyType("单位");
			tcase.setAcceptDate(Calendar.getInstance().getTime());
			tcase.setPsnSex("男");
			//tcase.setCaseStage("10");
			//tcase.setCaseStageStatus("0");
		}
		
		model.addAttribute("tcase", tcase);
		
		return "modules/tcase/tcaseInfoTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toStart")
	public String toStart(Tcase tcase, Model model) {
		
		List<User> availableHandlers = this.getCaseHandler4Start(tcase);
		tcase.getCaseProcess().setAvailableHandlers(availableHandlers);

		model.addAttribute("operateType", "start");
		model.addAttribute("tcase", tcase);
		return "modules/tcase/tcaseInfoTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toHandle")
	public String toHandle(CaseAct caseAct, Model model) {
		ProcessUtils pu = new ProcessUtils();
		String businessKey = pu.getBusinesskeyByProcInstId(caseAct.getProcInsId());
		String caseId = getCaseIdFromBusinessKey(businessKey);
		
		Tcase tcase = this.get(caseId);
		
		List<User> availableHandlers = this.getCaseHandler4Handle(tcase);
		tcase.getCaseProcess().setAvailableHandlers(availableHandlers);

		model.addAttribute("operateType", "handle");
		model.addAttribute("tcase", tcase);

		return "modules/tcase/tcaseInfoTab";
	}
	
	private List<User> getCaseHandler4Start(Tcase tcase){

		String caseStage = tcase.getCaseProcess().getCaseStage();
		
		String group = ProcessCommonUtils.getFirstTaskGroupByStage(caseStage);
		
		return this.getCaseHandlerByGroup(group);
	}
	
	private List<User> getCaseHandler4Handle(Tcase tcase){
		
		List<User> list = Lists.newArrayList();
		
		if(tcase.getTask()==null){
			return list;
		}
		
		String group = "";

		try {
			ProcessUtils pu = new ProcessUtils();
			Set<Expression> exps = pu.getNextTaskGroup(tcase.getTask().getId());
			
			for(Expression exp : exps){
				group = exp.getExpressionText();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		list = this.getCaseHandlerByGroup(group);
		return list;
	}
	
	private List<User> getCaseHandler4Current(Tcase tcase){
		
		String group = "";

		try {
			ProcessUtils pu = new ProcessUtils();
			Set<Expression> exps = pu.getTaskGroup(tcase.getTask().getId());
			for(Expression exp : exps){
				group = exp.getExpressionText();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return this.getCaseHandlerByGroup(group);
	}
	
	private List<User> getCaseHandlerByGroup(String group){
		return systemService.findUserByRoleEname(group);	
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "attachTab")
	public String attachTab(Tcase tcase, Model model) {
		model.addAttribute("tcase", tcase);
		
		//load attach info
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setCaseId(tcase.getId());
		List<CaseAttach> attachlist = caseAttachService.findList(caseAttach);
		model.addAttribute("attachlist", attachlist);
		return "modules/tcase/tcaseAttachTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "processTab")
	public String processTab(Tcase tcase, Model model) {
		
		User user = UserUtils.getUser();
		
		String procInstId = tcase.getCaseProcess().getProcInstId();
		if(!StringUtils.isEmpty(procInstId)){
			TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(tcase.getCaseProcess().getProcInstId()).active();
			Task task = null;
			if(taskQuery.count()==1){
				task = taskQuery.singleResult();
			}else{
				List<Task> tasks = taskQuery.list();
				for(Task entity: tasks){
					if(entity.getAssignee().equals(user.getLoginName())){
						task = entity;
					}
				}
			}
			
			tcase.setTask(task);			
		}
		
		List<User> availableHandlers = Lists.newArrayList();
		if("0".equals(tcase.getCaseProcess().getCaseStageStatus())){
			availableHandlers = this.getCaseHandler4Start(tcase);
			tcase.getCaseProcess().setMultiple(true);			
		}else{
			availableHandlers = this.getCaseHandler4Handle(tcase);	
			tcase.getCaseProcess().setMultiple(false);	
		}
		tcase.getCaseProcess().setAvailableHandlers(availableHandlers);			
		
		model.addAttribute("tcase", tcase);
		
		ActTask actTask = new ActTask();
		model.addAttribute("actTask", actTask);
		
		//load process info
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseId(tcase.getId());
		List<CaseProcess> processlist = caseProcessService.findList(caseProcess);
		
		List<CaseProcess> punishProcesslist = Lists.newArrayList();
		List<CaseProcess> independentProcesslist = Lists.newArrayList();
		
		for(CaseProcess process : processlist){
			if(Global.CASE_STAGE_SERIOUS.equals(process.getCaseStage()) 
			|| Global.CASE_STAGE_CANCEL.equals(process.getCaseStage())){
				independentProcesslist.add(process);
			}else{
				punishProcesslist.add(process);
			}
		}
		
		model.addAttribute("processlist", punishProcesslist);
		model.addAttribute("independentProcesslist", independentProcesslist);
		
		return "modules/tcase/tcaseProcessTab";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(Tcase tcase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tcase)){
			return form(tcase, model);
		}
		tcaseService.save(tcase);
		addMessage(redirectAttributes, "保存案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?id="+tcase.getId();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveAndStart")
	public String saveAndStart(Tcase tcase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tcase)){
			return form(tcase, model);
		}
		tcaseService.saveAndStartFlow(tcase);
		addMessage(redirectAttributes, "保存案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?id="+tcase.getId();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "startWorkFlow")
	public String startWorkFlow(Tcase tcase, Model model, RedirectAttributes redirectAttributes) {
		tcaseService.startWorkflow(tcase);
		addMessage(redirectAttributes, "流程启动成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/list";
	}
	
	@Transactional(readOnly = false)
	public String handletask(ActTask actTask) {
		String userid = UserUtils.getUser().getLoginName();
		
		Map<String,Object> variables = actTask.getVariables();
		
		//设置处理结果
		ProcessUtils pu = new ProcessUtils();
		String group = pu.getTaskGroupText(actTask.getTaskid());
		variables.put(group+"Op", actTask.getApprove());
		
		logger.debug("actTask.getTaskid():{}", actTask.getTaskid());
		
		//设置下一关
		String nextHandlersStr = actTask.getNextHandlers();
		if(!StringUtils.isEmpty(nextHandlersStr)){
			String[] strs = nextHandlersStr.split(",");
			if(strs.length==1){
				group = pu.getNextTaskGroupText(actTask.getTaskid());
				variables.put(group+"Assignee", strs[0]);				
			}else if(strs.length>11){
				List<String> assigneeList = Lists.newArrayList();
				for(String str : strs){
					assigneeList.add(str);
				}
				variables.put("assigneeList", assigneeList);
			}
			
		}
		
		StringBuffer comment = new StringBuffer();
		if("pass".equals(actTask.getApprove())){
			comment.append("[通过]");
		}else if("return".equals(actTask.getApprove())){
			comment.append("[退回]");
		}else if("cancel".equals(actTask.getApprove())){
			comment.append("[不通过]");
		}
		comment.append(actTask.getApproveOpinion());
		
		taskService.addComment(actTask.getTaskid(), actTask.getProcessinstanceid(), comment.toString());
		
		taskService.claim(actTask.getTaskid(), userid);
		taskService.complete(actTask.getTaskid(), variables);
		
		return "redirect:"+Global.getAdminPath()+"/case/tcase/list";
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(Tcase tcase, RedirectAttributes redirectAttributes) {
		tcaseService.delete(tcase);
		addMessage(redirectAttributes, "删除案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?repage";
	}
	
	private String getCaseIdFromBusinessKey(String businesskey){
		String[] strs = businesskey.split(":");
		return strs.length>0?strs[0]:"";
	}

}