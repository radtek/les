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
import org.wxjs.les.modules.base.entity.DocumentHead;
import org.wxjs.les.modules.base.service.DocumentHeadService;

/**
 * 发文字号Controller
 * @author GLQ
 * @version 2019-03-14
 */
@Controller
@RequestMapping(value = "${adminPath}/base/documentHead")
public class DocumentHeadController extends BaseController {

	@Autowired
	private DocumentHeadService documentHeadService;
	
	@ModelAttribute
	public DocumentHead get(@RequestParam(required=false) String id) {
		DocumentHead entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = documentHeadService.get(id);
		}
		if (entity == null){
			entity = new DocumentHead();
		}
		return entity;
	}
	
	@RequiresPermissions("base:documentHead:view")
	@RequestMapping(value = {"list", ""})
	public String list(DocumentHead documentHead, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DocumentHead> page = documentHeadService.findPage(new Page<DocumentHead>(request, response), documentHead); 
		model.addAttribute("page", page);
		return "modules/base/documentHeadList";
	}

	@RequiresPermissions("base:documentHead:view")
	@RequestMapping(value = "form")
	public String form(DocumentHead documentHead, Model model) {
		model.addAttribute("documentHead", documentHead);
		return "modules/base/documentHeadForm";
	}

	@RequiresPermissions("base:documentHead:edit")
	@RequestMapping(value = "save")
	public String save(DocumentHead documentHead, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, documentHead)){
			return form(documentHead, model);
		}
		documentHeadService.save(documentHead);
		addMessage(redirectAttributes, "保存发文字号成功");
		return "redirect:"+Global.getAdminPath()+"/base/documentHead/?repage";
	}
	
	@RequiresPermissions("base:documentHead:edit")
	@RequestMapping(value = "delete")
	public String delete(DocumentHead documentHead, RedirectAttributes redirectAttributes) {
		documentHeadService.delete(documentHead);
		addMessage(redirectAttributes, "删除发文字号成功");
		return "redirect:"+Global.getAdminPath()+"/base/documentHead/?repage";
	}

}