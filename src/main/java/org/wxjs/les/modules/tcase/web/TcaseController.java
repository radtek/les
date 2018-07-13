/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.web;

import java.util.Calendar;
import java.util.List;

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
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.service.CaseAttachService;
import org.wxjs.les.modules.tcase.service.TcaseService;

import com.google.common.collect.Lists;

/**
 * 案件Controller
 * @author GLQ
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/case/tcase")
public class TcaseController extends BaseController {

	@Autowired
	private TcaseService tcaseService;
	
	@Autowired
	private CaseAttachService caseAttachService;
	
	@ModelAttribute
	public Tcase get(@RequestParam(required=false) String id) {
		Tcase entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tcaseService.get(id);
		}
		if (entity == null){
			entity = new Tcase();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tcase tcase, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Tcase> page = tcaseService.findPage(new Page<Tcase>(request, response), tcase); 
		model.addAttribute("page", page);
		return "modules/tcase/tcaseList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(Tcase tcase, Model model) {
		if(tcase.getIsNewRecord()){
			tcase.setPartyType("单位");
			tcase.setAcceptDate(Calendar.getInstance().getTime());
			tcase.setPsnSex("男");
			tcase.setCaseStage("10");
			tcase.setCaseStageStatus("0");
		}
		model.addAttribute("tcase", tcase);
		return "modules/tcase/tcaseForm";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "infoTab")
	public String infoTab(Tcase tcase, Model model) {
		if(tcase.getIsNewRecord()){
			tcase.setPartyType("单位");
			tcase.setAcceptDate(Calendar.getInstance().getTime());
			tcase.setPsnSex("男");
			tcase.setCaseStage("10");
			tcase.setCaseStageStatus("0");
		}
		model.addAttribute("tcase", tcase);
		return "modules/tcase/tcaseInfoTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "attachTab")
	public String attachTab(Tcase tcase, Model model) {
		model.addAttribute("tcase", tcase);
		
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setCaseId(tcase.getId());
		List<CaseAttach> attachlist = caseAttachService.findList(caseAttach);
		model.addAttribute("attachlist", attachlist);
		return "modules/tcase/tcaseAttachTab";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "flowTab")
	public String flowTab(Tcase tcase, Model model) {
		model.addAttribute("tcase", tcase);
		return "modules/tcase/tcaseFlowTab";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(Tcase tcase, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tcase)){
			return form(tcase, model);
		}
		tcaseService.save(tcase);
		addMessage(redirectAttributes, "保存案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/?id="+tcase.getId();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(Tcase tcase, RedirectAttributes redirectAttributes) {
		tcaseService.delete(tcase);
		addMessage(redirectAttributes, "删除案件成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/infoTab?repage";
	}

}