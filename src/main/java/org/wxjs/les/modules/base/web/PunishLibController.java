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
import org.wxjs.les.modules.base.entity.PunishLib;
import org.wxjs.les.modules.base.service.PunishLibService;

/**
 * 处罚基准库Controller
 * @author GLQ
 * @version 2018-07-29
 */
@Controller
@RequestMapping(value = "${adminPath}/base/punishLib")
public class PunishLibController extends BaseController {

	@Autowired
	private PunishLibService punishLibService;
	
	@ModelAttribute
	public PunishLib get(@RequestParam(required=false) String id) {
		PunishLib entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = punishLibService.get(id);
		}
		if (entity == null){
			entity = new PunishLib();
		}
		return entity;
	}
	
	@RequiresPermissions("base:punishLib:view")
	@RequestMapping(value = {"list", ""})
	public String list(PunishLib punishLib, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PunishLib> page = punishLibService.findPage(new Page<PunishLib>(request, response), punishLib); 
		model.addAttribute("page", page);
		return "modules/base/punishLibList";
	}

	@RequiresPermissions("base:punishLib:view")
	@RequestMapping(value = "form")
	public String form(PunishLib punishLib, Model model) {
		model.addAttribute("punishLib", punishLib);
		return "modules/base/punishLibForm";
	}
	
	@RequiresPermissions("base:punishLib:edit")
	@RequestMapping(value = "importTab")
	public String importTab(PunishLib punishLib, Model model) {
		model.addAttribute("punishLib", punishLib);
		return "modules/base/punishLibImport";
	}
	
	@RequiresPermissions("base:punishLib:edit")
	@RequestMapping(value = "importLib")
	public String importLib(PunishLib punishLib, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, punishLib)){
			return importTab(punishLib, model);
		}
		punishLibService.importLib(punishLib);
		addMessage(redirectAttributes, "导入处罚基准库成功");
		return "redirect:"+Global.getAdminPath()+"/base/punishLib/importTab?repage";
	}

	@RequiresPermissions("base:punishLib:edit")
	@RequestMapping(value = "save")
	public String save(PunishLib punishLib, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, punishLib)){
			return form(punishLib, model);
		}
		punishLibService.save(punishLib);
		addMessage(redirectAttributes, "保存处罚基准库成功");
		return "redirect:"+Global.getAdminPath()+"/base/punishLib/?repage";
	}
	
	@RequiresPermissions("base:punishLib:edit")
	@RequestMapping(value = "delete")
	public String delete(PunishLib punishLib, RedirectAttributes redirectAttributes) {
		punishLibService.delete(punishLib);
		addMessage(redirectAttributes, "删除处罚基准库成功");
		return "redirect:"+Global.getAdminPath()+"/base/punishLib/?repage";
	}

}