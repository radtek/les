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
import org.wxjs.les.modules.tcase.entity.CaseFinish;
import org.wxjs.les.modules.tcase.service.CaseFinishService;

/**
 * 案件结束Controller
 * @author GLQ
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseFinish")
public class CaseFinishController extends BaseController {

	@Autowired
	private CaseFinishService caseFinishService;
	
	@ModelAttribute
	public CaseFinish get(@RequestParam(required=false) String id) {
		CaseFinish entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseFinishService.get(id);
		}
		if (entity == null){
			entity = new CaseFinish();
		}
		return entity;
	}
	
	@RequiresPermissions("tcase:caseFinish:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseFinish caseFinish, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseFinish> page = caseFinishService.findPage(new Page<CaseFinish>(request, response), caseFinish); 
		model.addAttribute("page", page);
		return "modules/tcase/caseFinishList";
	}

	@RequiresPermissions("tcase:caseFinish:view")
	@RequestMapping(value = "form")
	public String form(CaseFinish caseFinish, Model model) {
		model.addAttribute("caseFinish", caseFinish);
		return "modules/tcase/caseFinishForm";
	}

	@RequiresPermissions("tcase:caseFinish:edit")
	@RequestMapping(value = "save")
	public String save(CaseFinish caseFinish, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseFinish)){
			return form(caseFinish, model);
		}
		caseFinishService.save(caseFinish);
		addMessage(redirectAttributes, "保存案件结束成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseFinish/?repage";
	}
	
	@RequiresPermissions("tcase:caseFinish:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseFinish caseFinish, RedirectAttributes redirectAttributes) {
		caseFinishService.delete(caseFinish);
		addMessage(redirectAttributes, "删除案件结束成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseFinish/?repage";
	}

}