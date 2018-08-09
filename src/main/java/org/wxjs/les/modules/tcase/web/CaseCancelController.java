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
import org.wxjs.les.modules.tcase.entity.CaseCancel;
import org.wxjs.les.modules.tcase.service.CaseCancelService;

/**
 * 案件撤销Controller
 * @author GLQ
 * @version 2018-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseCancel")
public class CaseCancelController extends BaseController {

	@Autowired
	private CaseCancelService caseCancelService;
	
	@ModelAttribute
	public CaseCancel get(@RequestParam(required=false) String id) {
		CaseCancel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseCancelService.get(id);
		}
		if (entity == null){
			entity = new CaseCancel();
		}
		return entity;
	}
	
	@RequiresPermissions("tcase:caseCancel:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseCancel caseCancel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseCancel> page = caseCancelService.findPage(new Page<CaseCancel>(request, response), caseCancel); 
		model.addAttribute("page", page);
		return "modules/tcase/caseCancelList";
	}

	@RequiresPermissions("tcase:caseCancel:view")
	@RequestMapping(value = "form")
	public String form(CaseCancel caseCancel, Model model) {
		model.addAttribute("caseCancel", caseCancel);
		return "modules/tcase/caseCancelForm";
	}

	@RequiresPermissions("tcase:caseCancel:edit")
	@RequestMapping(value = "save")
	public String save(CaseCancel caseCancel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseCancel)){
			return form(caseCancel, model);
		}
		caseCancelService.save(caseCancel);
		addMessage(redirectAttributes, "保存案件撤销成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/cancelTab?"+caseCancel.getParamUri();		
	}
	
	@RequiresPermissions("tcase:caseCancel:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseCancel caseCancel, RedirectAttributes redirectAttributes) {
		caseCancelService.delete(caseCancel);
		addMessage(redirectAttributes, "删除案件撤销成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseCancel/?repage";
	}

}