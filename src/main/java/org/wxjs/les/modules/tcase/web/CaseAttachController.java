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
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.service.CaseAttachService;
import org.wxjs.les.modules.tcase.service.TcaseService;

/**
 * 案件资料Controller
 * @author GLQ
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/case/caseAttach")
public class CaseAttachController extends BaseController {

	@Autowired
	private CaseAttachService caseAttachService;
	
	@Autowired
	private TcaseService caseService;
	
	@ModelAttribute
	public CaseAttach get(@RequestParam(required=false) String id) {
		CaseAttach entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseAttachService.get(id);
		}
		if (entity == null){
			entity = new CaseAttach();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseAttach caseAttach, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseAttach> page = caseAttachService.findPage(new Page<CaseAttach>(request, response), caseAttach); 
		model.addAttribute("page", page);
		return "modules/tcase/caseAttachList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseAct caseAct, Model model) {
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = caseService.getCaseAndProcess(businesskey);
		
		caseAct.setTcase(tcase);
		
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setCaseId(tcase.getId());
		model.addAttribute("caseAttach", caseAttach);
		
		return "modules/tcase/caseAttachForm";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseAttach caseAttach, Model model, RedirectAttributes redirectAttributes) {

		String filename = "";
		String filepath = caseAttach.getFilepath();
		if(!StringUtils.isEmpty(filepath)){
			filename = filepath.substring(filepath.lastIndexOf("/")+1);
		}
		caseAttach.setFilename(filename);
		
		caseAttachService.save(caseAttach);
		addMessage(redirectAttributes, "保存案件资料成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/attachTab?"+caseAttach.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseAttach caseAttach, RedirectAttributes redirectAttributes) {
		caseAttachService.delete(caseAttach);
		addMessage(redirectAttributes, "删除案件资料成功");
		return "redirect:"+Global.getAdminPath()+"/case/caseAttach/?id="+caseAttach.getCaseId();
	}

}