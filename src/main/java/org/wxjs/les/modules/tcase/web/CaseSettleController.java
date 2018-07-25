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
import org.wxjs.les.modules.tcase.entity.CaseSettle;
import org.wxjs.les.modules.tcase.service.CaseSettleService;

/**
 * 案件结案Controller
 * @author GLQ
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseSettle")
public class CaseSettleController extends BaseController {

	@Autowired
	private CaseSettleService caseSettleService;
	
	@ModelAttribute
	public CaseSettle get(@RequestParam(required=false) String id) {
		CaseSettle entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseSettleService.get(id);
		}
		if (entity == null){
			entity = new CaseSettle();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseSettle caseSettle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseSettle> page = caseSettleService.findPage(new Page<CaseSettle>(request, response), caseSettle); 
		model.addAttribute("page", page);
		return "modules/tcase/caseSettleList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseSettle caseSettle, Model model) {
		model.addAttribute("caseSettle", caseSettle);
		return "modules/tcase/caseSettleTab";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseSettle caseSettle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseSettle)){
			return form(caseSettle, model);
		}
		caseSettleService.save(caseSettle);
		addMessage(redirectAttributes, "保存案件结案成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/settleTab?"+caseSettle.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseSettle caseSettle, RedirectAttributes redirectAttributes) {
		caseSettleService.delete(caseSettle);
		addMessage(redirectAttributes, "删除案件结案成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseSettle/?repage";
	}

}