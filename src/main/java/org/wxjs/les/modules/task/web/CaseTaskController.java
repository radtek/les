package org.wxjs.les.modules.task.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.web.BaseController;

import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.task.service.CaseTaskService;


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
	

}
