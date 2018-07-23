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
import org.wxjs.les.modules.base.entity.OpinionTemplate;
import org.wxjs.les.modules.base.service.OpinionTemplateService;

/**
 * 常用批语Controller
 * @author GLQ
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/base/opinionTemplate")
public class OpinionTemplateController extends BaseController {

	@Autowired
	private OpinionTemplateService opinionTemplateService;
	
	@ModelAttribute
	public OpinionTemplate get(@RequestParam(required=false) String id) {
		OpinionTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = opinionTemplateService.get(id);
		}
		if (entity == null){
			entity = new OpinionTemplate();
		}
		return entity;
	}
	
	@RequiresPermissions("base:opinionTemplate:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpinionTemplate opinionTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpinionTemplate> page = opinionTemplateService.findPage(new Page<OpinionTemplate>(request, response), opinionTemplate); 
		model.addAttribute("page", page);
		return "modules/base/opinionTemplateList";
	}

	@RequiresPermissions("base:opinionTemplate:view")
	@RequestMapping(value = "form")
	public String form(OpinionTemplate opinionTemplate, Model model) {
		model.addAttribute("opinionTemplate", opinionTemplate);
		return "modules/base/opinionTemplateForm";
	}

	@RequiresPermissions("base:opinionTemplate:edit")
	@RequestMapping(value = "save")
	public String save(OpinionTemplate opinionTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, opinionTemplate)){
			return form(opinionTemplate, model);
		}
		opinionTemplateService.save(opinionTemplate);
		addMessage(redirectAttributes, "保存常用批语成功");
		return "redirect:"+Global.getAdminPath()+"/base/opinionTemplate/?repage";
	}
	
	@RequiresPermissions("base:opinionTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(OpinionTemplate opinionTemplate, RedirectAttributes redirectAttributes) {
		opinionTemplateService.delete(opinionTemplate);
		addMessage(redirectAttributes, "删除常用批语成功");
		return "redirect:"+Global.getAdminPath()+"/base/opinionTemplate/?repage";
	}

}