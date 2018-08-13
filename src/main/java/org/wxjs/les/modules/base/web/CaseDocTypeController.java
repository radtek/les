/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.base.web;

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
import org.wxjs.les.modules.base.entity.CaseDocType;
import org.wxjs.les.modules.base.service.CaseDocTypeService;

/**
 * 文档类型Controller
 * @author GLQ
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/base/caseDocType")
public class CaseDocTypeController extends BaseController {

	@Autowired
	private CaseDocTypeService caseDocTypeService;
	
	@ModelAttribute
	public CaseDocType get(@RequestParam(required=false) String id) {
		CaseDocType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseDocTypeService.get(id);
		}
		if (entity == null){
			entity = new CaseDocType();
		}
		return entity;
	}
	
	@RequiresPermissions("base:caseDocType:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseDocType caseDocType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseDocType> page = caseDocTypeService.findPage(new Page<CaseDocType>(request, response), caseDocType); 
		model.addAttribute("page", page);
		return "modules/base/caseDocTypeList";
	}

	@RequiresPermissions("base:caseDocType:view")
	@RequestMapping(value = "form")
	public String form(CaseDocType caseDocType, Model model) {
		model.addAttribute("caseDocType", caseDocType);
		return "modules/base/caseDocTypeForm";
	}

	@RequiresPermissions("base:caseDocType:edit")
	@RequestMapping(value = "save")
	public String save(CaseDocType caseDocType, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseDocType)){
			return form(caseDocType, model);
		}
		caseDocTypeService.save(caseDocType);
		addMessage(redirectAttributes, "保存文档类型成功");
		return "redirect:"+Global.getAdminPath()+"/base/caseDocType/?repage";
	}
	
	@RequiresPermissions("base:caseDocType:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseDocType caseDocType, RedirectAttributes redirectAttributes) {
		caseDocTypeService.delete(caseDocType);
		addMessage(redirectAttributes, "删除文档类型成功");
		return "redirect:"+Global.getAdminPath()+"/base/caseDocType/?repage";
	}

}