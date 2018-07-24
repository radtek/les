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
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.service.CaseHandleService;

/**
 * 案件审理Controller
 * @author GLQ
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseHandle")
public class CaseHandleController extends BaseController {

	@Autowired
	private CaseHandleService caseHandleService;
	
	@ModelAttribute
	public CaseHandle get(@RequestParam(required=false) String id) {
		CaseHandle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseHandleService.get(id);
		}
		if (entity == null){
			entity = new CaseHandle();
		}
		return entity;
	}
	
	@RequiresPermissions("tcase:caseHandle:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseHandle caseHandle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseHandle> page = caseHandleService.findPage(new Page<CaseHandle>(request, response), caseHandle); 
		model.addAttribute("page", page);
		return "modules/tcase/caseHandleList";
	}

	@RequiresPermissions("tcase:caseHandle:view")
	@RequestMapping(value = "form")
	public String form(CaseHandle caseHandle, Model model) {
		model.addAttribute("caseHandle", caseHandle);
		return "modules/tcase/caseHandleForm";
	}

	@RequiresPermissions("tcase:caseHandle:edit")
	@RequestMapping(value = "save")
	public String save(CaseHandle caseHandle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseHandle)){
			return form(caseHandle, model);
		}
		caseHandleService.save(caseHandle);
		addMessage(redirectAttributes, "保存案件审理成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseHandle/?repage";
	}
	
	@RequiresPermissions("tcase:caseHandle:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseHandle caseHandle, RedirectAttributes redirectAttributes) {
		caseHandleService.delete(caseHandle);
		addMessage(redirectAttributes, "删除案件审理成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseHandle/?repage";
	}

}