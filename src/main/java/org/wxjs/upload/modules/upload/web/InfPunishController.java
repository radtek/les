/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.wxjs.upload.modules.upload.entity.InfPunish;
import org.wxjs.upload.modules.upload.service.InfPunishService;

/**
 * inf_punishController
 * @author GLQ
 * @version 2018-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/upload/infPunish")
public class InfPunishController extends BaseController {

	@Autowired
	private InfPunishService infPunishService;
	
	@ModelAttribute
	public InfPunish get(@RequestParam(required=false) String id) {
		InfPunish entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = infPunishService.get(id);
		}
		if (entity == null){
			entity = new InfPunish();
		}
		return entity;
	}
	
	@RequiresPermissions("upload:infPunish:view")
	@RequestMapping(value = {"list", ""})
	public String list(InfPunish infPunish, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		logger.debug("list...");
		
		List<InfPunish> list = infPunishService.findList(infPunish);
		model.addAttribute("list", list);
		return "modules/upload/infPunishList";
	}

	@RequiresPermissions("upload:infPunish:view")
	@RequestMapping(value = "form")
	public String form(InfPunish infPunish, Model model) {
		model.addAttribute("infPunish", infPunish);
		return "modules/upload/infPunishForm";
	}

	@RequiresPermissions("upload:infPunish:edit")
	@RequestMapping(value = "save")
	public String save(InfPunish infPunish, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, infPunish)){
			return form(infPunish, model);
		}
		infPunishService.save(infPunish);
		addMessage(redirectAttributes, "保存inf_punish成功");
		return "redirect:"+Global.getAdminPath()+"/upload/infPunish/?repage";
	}
	
	@RequiresPermissions("upload:infPunish:edit")
	@RequestMapping(value = "delete")
	public String delete(InfPunish infPunish, RedirectAttributes redirectAttributes) {
		infPunishService.delete(infPunish);
		addMessage(redirectAttributes, "删除inf_punish成功");
		return "redirect:"+Global.getAdminPath()+"/upload/infPunish/?repage";
	}

}