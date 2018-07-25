package org.wxjs.les.modules.task.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.modules.act.entity.Act;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.task.service.CaseTaskService;
import org.wxjs.les.modules.task.utils.CaseActUtils;


/**
 * TaskController
 * @author GLQ
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/task")
public class CaseTaskController extends BaseController {
	

	@Autowired
	private CaseTaskService actTaskService;
	
	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = {"todo", ""})
	public String todoList(CaseAct caseAct, HttpServletResponse response, Model model) throws Exception {
		List<CaseAct> list = actTaskService.todoList(caseAct);
		model.addAttribute("list", list);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, list);
		}
		return "modules/task/actTaskTodoList";
	}
	
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = "historic")
	public String historicList(CaseAct caseAct, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Page<CaseAct> page = new Page<CaseAct>(request, response);
		page = actTaskService.historicList(page, caseAct);
		model.addAttribute("page", page);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, page);
		}
		return "modules/task/actTaskHistoricList";
	}
	
	/**
	 * 获取流程表单
	 * @param taskId	任务ID
	 * @param taskName	任务名称
	 * @param taskDefKey 任务环节标识
	 * @param procInsId 流程实例ID
	 * @param procDefId 流程定义ID
	 */
	@RequestMapping(value = "form")
	public String form(CaseAct caseAct, HttpServletRequest request, Model model){
		
		// 获取流程XML上的表单KEY
		//String formKey = actTaskService.getFormKey(caseAct.getProcDefId(), caseAct.getTaskDefKey());
		
		String formKey = "/case/tcase/infoTab";
		
		if("1".equals(caseAct.getCaseTransfer())){
			formKey = "/case/tcase/infoTabTransfer";
		}

		// 获取流程实例对象
		/*
		if (caseAct.getProcInsId() != null){

			String stageStatus = caseAct.getTcase().getCaseProcess().getCaseStageStatus();
			if(Global.CASE_STAGE_STATUS_STARTED.equals(stageStatus)){
				ProcessInstance pi = actTaskService.getProcIns(caseAct.getProcInsId());
				caseAct.setBusinesskey(pi.getBusinessKey());
			}else if(Global.CASE_STAGE_STATUS_FINISHED.equals(stageStatus)
				  || Global.CASE_STAGE_STATUS_CANCELED.equals(stageStatus)){
				HistoricProcessInstance hpi = actTaskService.getHisProcIns(caseAct.getProcInsId());
				caseAct.setBusinesskey(hpi.getBusinessKey());
			}
		}
		*/
		
		String formUrl = CaseActUtils.getFormUrl(formKey, caseAct);
		
		logger.debug("formUrl:{}", formUrl);
		
		return "redirect:" + formUrl;
		
//		// 传递参数到视图
//		model.addAttribute("act", act);
//		model.addAttribute("formUrl", formUrl);
//		return "modules/act/actTaskForm";
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 */
	@RequestMapping(value = "start")
	@ResponseBody
	public String start(Act act, String table, String id, Model model) throws Exception {
		actTaskService.startProcess(act.getProcDefKey(), act.getBusinessId(), act.getBusinessTable(), act.getTitle());
		return "true";//adminPath + "/act/task";
	}

	/**
	 * 签收任务
	 * @param taskId 任务ID
	 */
	@RequestMapping(value = "claim")
	@ResponseBody
	public String claim(Act act) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		actTaskService.claim(act.getTaskId(), userId);
		return "true";//adminPath + "/act/task";
	}
	
	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务流程变量，如下
	 * 		vars.keys=flag,pass
	 * 		vars.values=1,true
	 * 		vars.types=S,B  @see org.wxjs.les.modules.act.utils.PropertyType
	 */
	@RequestMapping(value = "complete")
	@ResponseBody
	public String complete(Act act) {
		actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), act.getVars().getVariableMap());
		return "true";//adminPath + "/act/task";
	}
	

}
