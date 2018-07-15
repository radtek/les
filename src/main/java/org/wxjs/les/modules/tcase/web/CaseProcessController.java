/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.service.CaseProcessService;

/**
 * 案件流程Controller
 * @author GLQ
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseProcess")
public class CaseProcessController extends BaseController {

	@Autowired
	private CaseProcessService caseProcessService;
	
	@ModelAttribute
	public CaseProcess get(@RequestParam(required=false) String id) {
		CaseProcess entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseProcessService.get(id);
		}
		if (entity == null){
			entity = new CaseProcess();
		}
		return entity;
	}
	
	@RequiresPermissions("tcase:caseProcess:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseProcess caseProcess, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseProcess> page = caseProcessService.findPage(new Page<CaseProcess>(request, response), caseProcess); 
		model.addAttribute("page", page);
		return "modules/tcase/caseProcessList";
	}

	@RequiresPermissions("tcase:caseProcess:view")
	@RequestMapping(value = "form")
	public String form(CaseProcess caseProcess, Model model) {
		model.addAttribute("caseProcess", caseProcess);
		return "modules/tcase/caseProcessForm";
	}

	@RequiresPermissions("tcase:caseProcess:edit")
	@RequestMapping(value = "save")
	public String save(CaseProcess caseProcess, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseProcess)){
			return form(caseProcess, model);
		}
		caseProcessService.save(caseProcess);
		addMessage(redirectAttributes, "保存案件流程成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseProcess/?repage";
	}
	
	@RequiresPermissions("tcase:caseProcess:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseProcess caseProcess, RedirectAttributes redirectAttributes) {
		caseProcessService.delete(caseProcess);
		addMessage(redirectAttributes, "删除案件流程成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseProcess/?repage";
	}

}