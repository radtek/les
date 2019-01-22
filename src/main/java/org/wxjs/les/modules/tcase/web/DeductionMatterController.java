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
import org.wxjs.les.modules.tcase.entity.DeductionMatter;
import org.wxjs.les.modules.tcase.service.DeductionMatterService;

/**
 * 处罚扣分事项Controller
 * @author GLQ
 * @version 2019-01-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/deductionMatter")
public class DeductionMatterController extends BaseController {

	@Autowired
	private DeductionMatterService deductionMatterService;
	
	@ModelAttribute
	public DeductionMatter get(@RequestParam(required=false) String id) {
		DeductionMatter entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = deductionMatterService.get(id);
		}
		if (entity == null){
			entity = new DeductionMatter();
		}
		return entity;
	}
	
	@RequiresPermissions("tcase:deductionMatter:view")
	@RequestMapping(value = {"list", ""})
	public String list(DeductionMatter deductionMatter, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DeductionMatter> page = deductionMatterService.findPage(new Page<DeductionMatter>(request, response), deductionMatter); 
		model.addAttribute("page", page);
		return "modules/tcase/deductionMatterList";
	}

	@RequiresPermissions("tcase:deductionMatter:view")
	@RequestMapping(value = "form")
	public String form(DeductionMatter deductionMatter, Model model) {
		model.addAttribute("deductionMatter", deductionMatter);
		return "modules/tcase/deductionMatterForm";
	}

	@RequiresPermissions("tcase:deductionMatter:edit")
	@RequestMapping(value = "save")
	public String save(DeductionMatter deductionMatter, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, deductionMatter)){
			return form(deductionMatter, model);
		}
		deductionMatterService.save(deductionMatter);
		addMessage(redirectAttributes, "保存处罚扣分事项成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/deductionMatter/?repage";
	}
	
	@RequiresPermissions("tcase:deductionMatter:edit")
	@RequestMapping(value = "delete")
	public String delete(DeductionMatter deductionMatter, RedirectAttributes redirectAttributes) {
		deductionMatterService.delete(deductionMatter);
		addMessage(redirectAttributes, "删除处罚扣分事项成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/deductionMatter/?repage";
	}

}