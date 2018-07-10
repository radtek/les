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
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.service.CaseDecisionService;

/**
 * 案件决定书Controller
 * @author GLQ
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/case/caseDecision")
public class CaseDecisionController extends BaseController {

	@Autowired
	private CaseDecisionService caseDecisionService;
	
	@ModelAttribute
	public CaseDecision get(@RequestParam(required=false) String id) {
		CaseDecision entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseDecisionService.get(id);
		}
		if (entity == null){
			entity = new CaseDecision();
		}
		return entity;
	}
	
	@RequiresPermissions("case:caseDecision:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseDecision caseDecision, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseDecision> page = caseDecisionService.findPage(new Page<CaseDecision>(request, response), caseDecision); 
		model.addAttribute("page", page);
		return "modules/tcase/caseDecisionList";
	}

	@RequiresPermissions("case:caseDecision:view")
	@RequestMapping(value = "form")
	public String form(CaseDecision caseDecision, Model model) {
		model.addAttribute("caseDecision", caseDecision);
		return "modules/tcase/caseDecisionForm";
	}

	@RequiresPermissions("case:caseDecision:edit")
	@RequestMapping(value = "save")
	public String save(CaseDecision caseDecision, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseDecision)){
			return form(caseDecision, model);
		}
		caseDecisionService.save(caseDecision);
		addMessage(redirectAttributes, "保存案件决定书成功");
		return "redirect:"+Global.getAdminPath()+"/case/caseDecision/?repage";
	}
	
	@RequiresPermissions("case:caseDecision:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseDecision caseDecision, RedirectAttributes redirectAttributes) {
		caseDecisionService.delete(caseDecision);
		addMessage(redirectAttributes, "删除案件决定书成功");
		return "redirect:"+Global.getAdminPath()+"/case/caseDecision/?repage";
	}

}