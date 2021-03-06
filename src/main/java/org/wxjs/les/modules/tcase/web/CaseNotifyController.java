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
import org.wxjs.les.modules.tcase.entity.CaseNotify;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseHandleApproveExport;
import org.wxjs.les.modules.tcase.export.CaseNotifyExport;
import org.wxjs.les.modules.tcase.service.CaseNotifyService;
import org.wxjs.les.modules.tcase.service.TcaseService;

/**
 * 案件告知书Controller
 * @author GLQ
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/case/caseNotify")
public class CaseNotifyController extends BaseController {
	
	@Autowired
	private TcaseService tcaseService;

	@Autowired
	private CaseNotifyService caseNotifyService;
	
	@ModelAttribute
	public CaseNotify get(@RequestParam(required=false) String id) {
		CaseNotify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseNotifyService.get(id);
		}
		if (entity == null){
			entity = new CaseNotify();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseNotify caseNotify, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseNotify> page = caseNotifyService.findPage(new Page<CaseNotify>(request, response), caseNotify); 
		model.addAttribute("page", page);
		return "modules/tcase/caseNotifyList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseNotify caseNotify, Model model) {
		model.addAttribute("caseNotify", caseNotify);
		return "modules/tcase/caseNotifyTab";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseNotify caseNotify, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseNotify)){
			return form(caseNotify, model);
		}
		caseNotifyService.save(caseNotify);
		addMessage(redirectAttributes, "保存案件告知书成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/notifyTab?"+caseNotify.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseNotify caseNotify, RedirectAttributes redirectAttributes) {
		caseNotifyService.delete(caseNotify);
		addMessage(redirectAttributes, "删除案件告知书成功");
		return "redirect:"+Global.getAdminPath()+"/case/caseNotify/?repage";
	}
	
	@RequestMapping(value = "exportPDF")
	public String exportPDF(CaseNotify entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseNotify caseNotify = caseNotifyService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_NOTIFY);	
		
		try {
            String fileName = "告知书"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseNotifyExport export = new CaseNotifyExport(tcase, caseNotify, false);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/notifyTab?"+entity.getParamUri();
	}
	
	@RequestMapping(value = "exportCopyPDF")
	public String exportCopyPDF(CaseNotify entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseNotify caseNotify = caseNotifyService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_NOTIFY);	
		
		try {
            String fileName = "告知书_存根"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseNotifyExport export = new CaseNotifyExport(tcase, caseNotify, true);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/notifyTab?"+entity.getParamUri();
	}
	
	@RequestMapping(value = "recallNumber")
	public String recallNumber(CaseNotify caseNotify, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		//CaseNotify caseNotify = caseNotifyService.get(entity.getCaseId());
		
		caseNotifyService.recallNumber(caseNotify);
				
		return "redirect:"+Global.getAdminPath()+"/case/tcase/notifyTab?"+caseNotify.getParamUri();
	}

}