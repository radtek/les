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
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.StringUtils;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.entity.CaseSerious;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseDecisionExport;
import org.wxjs.les.modules.tcase.export.CaseDecisionLaunchExport;
import org.wxjs.les.modules.tcase.export.CaseDecisionReachExport;
import org.wxjs.les.modules.tcase.export.CaseSeriousExport;
import org.wxjs.les.modules.tcase.service.CaseDecisionService;
import org.wxjs.les.modules.tcase.service.TcaseService;

/**
 * 案件决定书Controller
 * @author GLQ
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseDecision")
public class CaseDecisionController extends BaseController {
	
	@Autowired
	private TcaseService tcaseService;

	@Autowired
	private CaseDecisionService caseDecisionService;
	
	@ModelAttribute
	public CaseDecision get(@RequestParam(required=false) String id) {
		CaseDecision entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseDecisionService.get(id);
		}
		if (entity == null){
			entity = new CaseDecision();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseDecision caseDecision, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseDecision> page = caseDecisionService.findPage(new Page<CaseDecision>(request, response), caseDecision); 
		model.addAttribute("page", page);
		return "modules/tcase/caseDecisionList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseDecision caseDecision, Model model) {
		model.addAttribute("caseDecision", caseDecision);
		return "modules/tcase/tcaseDecisionTab";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseDecision caseDecision, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseDecision)){
			return form(caseDecision, model);
		}
		caseDecisionService.save(caseDecision);
		addMessage(redirectAttributes, "保存案件决定书成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/decisionTab?"+caseDecision.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseDecision caseDecision, RedirectAttributes redirectAttributes) {
		caseDecisionService.delete(caseDecision);
		addMessage(redirectAttributes, "删除案件决定书成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseDecision/?repage";
	}
	
	@RequestMapping(value = "exportDecisionPDF")
	public String exportDecisionPDF(CaseDecision entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseDecision caseDecision = caseDecisionService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_DECISION);
		
		try {
            String fileName = "处罚决定书"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseDecisionExport export = new CaseDecisionExport(tcase, caseDecision);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/decisionTab?"+entity.getParamUri();
	}
	
	@RequestMapping(value = "exportDecisionLaunchPDF")
	public String exportDecisionLaunchPDF(CaseDecision entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseDecision caseDecision = caseDecisionService.get(entity.getCaseId());

		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_DECISION);
		
		try {
            String fileName = "处罚决定书发文稿"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseDecisionLaunchExport export = new CaseDecisionLaunchExport(tcase, caseDecision);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/decisionTab?"+entity.getParamUri();
	}
	
	@RequestMapping(value = "exportReachPDF")
	public String exportReachPDF(CaseDecision entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseDecision caseDecision = caseDecisionService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_DECISION);
		
		try {
            String fileName = "处罚决定书送达回证"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseDecisionReachExport export = new CaseDecisionReachExport(tcase, caseDecision);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/decisionTab?"+entity.getParamUri();
	}
	
	@RequestMapping(value = "recallNumber")
	public String recallNumber(CaseDecision caseDecision, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		//CaseDecision caseDecision = caseDecisionService.get(entity.getCaseId());
		
		caseDecisionService.recallNumber(caseDecision);
				
		return "redirect:"+Global.getAdminPath()+"/case/tcase/decisionTab?"+caseDecision.getParamUri();
	}

}