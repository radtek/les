/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.check.web;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
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
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.modules.act.service.ProcessService;
import org.wxjs.les.modules.base.dao.SignatureLibDao;
import org.wxjs.les.modules.base.entity.ActTask;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.entity.SignatureLib;
import org.wxjs.les.modules.base.service.SignatureService;
import org.wxjs.les.modules.check.entity.Tsitecheck;
import org.wxjs.les.modules.check.export.TsitecheckExport;
import org.wxjs.les.modules.check.service.TsitecheckService;
import org.wxjs.les.modules.message.DbMessageSender;
import org.wxjs.les.modules.message.MessageSender;
import org.wxjs.les.modules.sys.entity.Office;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.service.SystemService;
import org.wxjs.les.modules.sys.utils.UserUtils;

import com.google.common.collect.Lists;

/**
 * 现场踏勘Controller
 * @author 千里目
 * @version 2018-07-10
 */
@Controller
@RequestMapping(value = "${adminPath}/check/tsitecheck")
public class TsitecheckController extends BaseController {
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	ProcessService processService;
	
	@Autowired
	private SystemService systemService;

	@Autowired
	private TsitecheckService tsitecheckService;
	
	@Autowired
	private SignatureService signatureService;
	
	@Autowired
	private SignatureLibDao signatureLibDao;
	
	@ModelAttribute
	public Tsitecheck get(@RequestParam(required=false) String id) {
		Tsitecheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tsitecheckService.get(id);
		}
		if (entity == null){
			entity = new Tsitecheck();
		}
		return entity;
	}
	
	@RequiresPermissions("check:tsitecheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tsitecheck tsitecheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tsitecheck> page = tsitecheckService.findPage(new Page<Tsitecheck>(request, response), tsitecheck); 
		model.addAttribute("page", page);
		return "modules/check/tsitecheckList";
	}
	
	@RequiresPermissions("check:tsitecheck:view")
	@RequestMapping(value = {"listFinished"})
	public String listFinished(HttpServletRequest request, HttpServletResponse response, Model model) {
		Tsitecheck tsitecheck = new Tsitecheck();
		tsitecheck.setCaseStatus(Global.CASE_STATUS_FINISHED);
		Page<Tsitecheck> page = tsitecheckService.findPage(new Page<Tsitecheck>(request, response), tsitecheck); 
		model.addAttribute("page", page);
		return "modules/check/tsitecheckList";
	}

	@RequiresPermissions("check:tsitecheck:view")
	@RequestMapping(value = "form")
	public String form(Tsitecheck tsitecheck, Model model) {
		
		if(tsitecheck.getIsNewRecord()){
			String siteCheckResult = Global.getConfig("DEFAULT_SITE_SITUATION");
			tsitecheck.setSiteCheckResult(siteCheckResult);
		}
		
		model.addAttribute("tsitecheck", tsitecheck);
		return "modules/check/tsitecheckForm";
	}

	@RequiresPermissions("check:tsitecheck:edit")
	@RequestMapping(value = "save")
	public String save(Tsitecheck tsitecheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsitecheck)){
			return form(tsitecheck, model);
		}
		tsitecheckService.save(tsitecheck);
		addMessage(redirectAttributes, "保存现场踏勘信息成功");
		
		return "redirect:"+Global.getAdminPath()+"/check/tsitecheck/?repage";
	}
	
	
	@RequiresPermissions("check:tsitecheck:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(Tsitecheck tsitecheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsitecheck)){
		return form(tsitecheck, model);
		}
		tsitecheckService.save(tsitecheck);
		model.addAttribute("tsitecheck", tsitecheck);
		return "modules/check/tsitecheckForm";
	}
	
	@RequiresPermissions("check:tsitecheck:view")
	@RequestMapping(value = "toHandle")
	public String toHandle(Tsitecheck tsitecheck, Model model) {
		//tsitecheck
		model.addAttribute("tsitecheck", tsitecheck);
		logger.debug(tsitecheck.toString());
		//	
		User user = UserUtils.getUser();
		Office office = user.getOffice();
		
		if(!StringUtils.isEmpty(tsitecheck.getTaskId())){
			List<User> availableHandlers = Lists.newArrayList();
			if("0".equals(tsitecheck.getCaseStatus())){		
			}else{
				availableHandlers = processService.getHandler4Handle(tsitecheck.getTaskId(), office.getType(), office.getAreaId());	
			}
			tsitecheck.setAvailableHandlers(availableHandlers);	
			
			if(availableHandlers.size() == 1 ){
				//set default value
				tsitecheck.setHandler(availableHandlers.get(0).getLoginName());
				
				logger.debug("getCaseHandler():{}", tsitecheck.getHandler());
			}
			
			Task task = taskService.createTaskQuery().taskId(tsitecheck.getTaskId()).singleResult();
			
			logger.debug("task.getExecutionId():{}", task!=null?task.getExecutionId():"");
			
			//tsitecheck.setTask(task);	
		}
		
		if(Global.OperateType_Handle.equals(tsitecheck.getOperateType())){
			ActTask actTask = new ActTask();
			
			actTask.setBusinesskey(tsitecheck.getBusinesskey());
			
			actTask.initialSignature();
			
			actTask.setTaskId(tsitecheck.getTaskId());
			
			actTask.setTaskName(tsitecheck.getTaskName());
			
			actTask.setProcInsId(tsitecheck.getProcInsId());
			
			actTask.setNextHandlers(tsitecheck.getHandler());
			
			//set NextConditionTexts to control the button display
			actTask.setNextConditionTexts(processService.getConditionTexts(tsitecheck.getTaskId()));
			
			logger.debug("actTask.getNextHandlers():{}", actTask.getNextHandlers());
			model.addAttribute("actTask", actTask);			
		}		
		
		return "modules/check/tsitecheckForm";
	}
	
	@RequiresPermissions("check:tsitecheck:edit")
	@RequestMapping(value = "saveAndStart")
	public String saveAndStart(Tsitecheck tsitecheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tsitecheck)){
		return form(tsitecheck, model);
		}
		tsitecheckService.saveAndStartFlow(tsitecheck);
		
		return "redirect:"+Global.getAdminPath()+"/check/tsitecheck/?repage";
		
		//return "redirect:"+Global.getAdminPath()+"/task/todo";
	}
										
	@RequiresPermissions("check:tsitecheck:edit")
	@RequestMapping(value = "delete")
	public String delete(Tsitecheck tsitecheck, RedirectAttributes redirectAttributes) {
		tsitecheckService.delete(tsitecheck);
		addMessage(redirectAttributes, "删除现场踏勘信息成功");
		return "redirect:"+Global.getAdminPath()+"/check/tsitecheck/?repage";
	}
	
	@RequiresPermissions("check:tsitecheck:view")
	@RequestMapping(value = "exportPDF")
	public String exportPDF(Tsitecheck tsitecheck, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		Tsitecheck check = tsitecheckService.get(tsitecheck.getId());
		model.addAttribute("tsitecheck", check);
		try {
            String fileName = "现场踏勘"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            TsitecheckExport export = new TsitecheckExport(check);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		
		return "modules/check/tsitecheckForm";
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
				
				return "redirect:"+Global.getAdminPath()+"/task/todo4SiteCheck";
			}
			
		}else if("return".equals(actTask.getApprove())){
			comment.append("[退回]");
		}
		comment.append(actTask.getApproveOpinion());
		//add signature
		//comment.append(Global.SignatureTag);
		//comment.append(actTask.getSignature().getId());
		
		taskService.addComment(actTask.getTaskId(), actTask.getProcInsId(), comment.toString());
		
		taskService.claim(actTask.getTaskId(), userid);
		taskService.complete(actTask.getTaskId(), variables);
		
		//update status if necessary
		String businesskey = actTask.getBusinesskey();
		Tsitecheck tsitecheck = new Tsitecheck();
		tsitecheck.setId(businesskey.substring(businesskey.indexOf(":")+1));
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
				tsitecheck.setCaseStatus(Global.CASE_STAGE_STATUS_FINISHED);
				this.tsitecheckService.updateStatus(tsitecheck);
			}
			
			//send message
			MessageSender sender = new DbMessageSender();
			try {
				sender.send(nextHandlersStr, "综合行政执法系统中您有1个待办事项。"
				+" 事项："+"现场勘查（编号："+tsitecheck.getId()+"）"+"。");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("return".equals(actTask.getApprove())){
			tsitecheck.setCaseStatus(Global.CASE_STATUS_NOSTART);
			this.tsitecheckService.updateStatus(tsitecheck);			
		}
		
		return "redirect:"+Global.getAdminPath()+"/task/todo4SiteCheck";
	}
	
}