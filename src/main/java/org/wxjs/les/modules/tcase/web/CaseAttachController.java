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
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.service.CaseAttachService;

/**
 * 案件资料Controller
 * @author GLQ
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/case/caseAttach")
public class CaseAttachController extends BaseController {

	@Autowired
	private CaseAttachService caseAttachService;
	
	@ModelAttribute
	public CaseAttach get(@RequestParam(required=false) String id) {
		CaseAttach entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseAttachService.get(id);
		}
		if (entity == null){
			entity = new CaseAttach();
		}
		return entity;
	}
	
	@RequiresPermissions("case:caseAttach:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseAttach caseAttach, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseAttach> page = caseAttachService.findPage(new Page<CaseAttach>(request, response), caseAttach); 
		model.addAttribute("page", page);
		return "modules/tcase/caseAttachList";
	}

	@RequiresPermissions("case:caseAttach:view")
	@RequestMapping(value = "form")
	public String form(CaseAttach caseAttach, Model model) {
		model.addAttribute("caseAttach", caseAttach);
		return "modules/tcase/caseAttachForm";
	}

	@RequiresPermissions("case:caseAttach:edit")
	@RequestMapping(value = "save")
	public String save(CaseAttach caseAttach, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAttach)){
			return form(caseAttach, model);
		}
		caseAttachService.save(caseAttach);
		addMessage(redirectAttributes, "保存案件资料成功");
		return "redirect:"+Global.getAdminPath()+"/case/caseAttach/?repage";
	}
	
	@RequiresPermissions("case:caseAttach:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseAttach caseAttach, RedirectAttributes redirectAttributes) {
		caseAttachService.delete(caseAttach);
		addMessage(redirectAttributes, "删除案件资料成功");
		return "redirect:"+Global.getAdminPath()+"/case/caseAttach/?repage";
	}

}