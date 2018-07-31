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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.web.BaseController;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.base.entity.MSG;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.tcase.entity.CaseHandlePunishLib;
import org.wxjs.les.modules.tcase.service.CaseHandlePunishLibService;

/**
 * 案件裁量权Controller
 * @author GLQ
 * @version 2018-07-30
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseHandlePunishLib")
public class CaseHandlePunishLibController extends BaseController {

	@Autowired
	private CaseHandlePunishLibService caseHandlePunishLibService;
	
	@ModelAttribute
	public CaseHandlePunishLib get(@RequestParam(required=false) String id) {
		CaseHandlePunishLib entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseHandlePunishLibService.get(id);
		}
		if (entity == null){
			entity = new CaseHandlePunishLib();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseHandlePunishLib caseHandlePunishLib, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseHandlePunishLib> page = caseHandlePunishLibService.findPage(new Page<CaseHandlePunishLib>(request, response), caseHandlePunishLib); 
		model.addAttribute("page", page);
		return "modules/tcase/caseHandlePunishLibList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseHandlePunishLib caseHandlePunishLib, Model model) {
		model.addAttribute("caseHandlePunishLib", caseHandlePunishLib);
		return "modules/tcase/caseHandlePunishLibForm";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseHandlePunishLib caseHandlePunishLib, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseHandlePunishLib)){
			return form(caseHandlePunishLib, model);
		}
		caseHandlePunishLibService.save(caseHandlePunishLib);
		addMessage(redirectAttributes, "保存案件裁量权成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/handleTab?"+caseHandlePunishLib.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseAct caseAct, RedirectAttributes redirectAttributes) {
		
		CaseHandlePunishLib caseHandlePunishLib = new CaseHandlePunishLib();
		
		String id = caseAct.getId(); //借用这个id来传
		
		caseHandlePunishLib.setId(id);
		
		caseHandlePunishLibService.delete(caseHandlePunishLib);
		addMessage(redirectAttributes, "删除案件裁量权成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/handleTab?"+caseAct.getParamUri();
	}
	
	@RequestMapping(value = "updateRange")
	@ResponseBody
	public MSG updateRange(CaseHandlePunishLib caseHandlePunishLib, HttpServletRequest req) {
		caseHandlePunishLibService.updateRange(caseHandlePunishLib);
		return new MSG("ok");  
	}

}