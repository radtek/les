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
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.tcase.entity.CaseCancel;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseCancelExport;
import org.wxjs.les.modules.tcase.service.CaseCancelService;
import org.wxjs.les.modules.tcase.service.TcaseService;

/**
 * 案件撤销Controller
 * @author GLQ
 * @version 2018-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseCancel")
public class CaseCancelController extends BaseController {

	@Autowired
	private CaseCancelService caseCancelService;
	
	@Autowired
	private TcaseService tcaseService;
	
	@ModelAttribute
	public CaseCancel get(@RequestParam(required=false) String id) {
		CaseCancel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseCancelService.get(id);
		}
		if (entity == null){
			entity = new CaseCancel();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseCancel caseCancel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseCancel> page = caseCancelService.findPage(new Page<CaseCancel>(request, response), caseCancel); 
		model.addAttribute("page", page);
		return "modules/tcase/caseCancelList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseCancel caseCancel, Model model) {
		model.addAttribute("caseCancel", caseCancel);
		return "modules/tcase/caseCancelForm";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseCancel caseCancel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseCancel)){
			return form(caseCancel, model);
		}
		caseCancelService.save(caseCancel);
		addMessage(redirectAttributes, "保存案件撤销成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/cancelTab?"+caseCancel.getParamUri();		
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveAndStart")
	public String saveAndStart(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		//tcaseService.saveAndStartFlow(caseAct.getTcase());
		tcaseService.saveProcess(caseAct.getTcase());
		caseCancelService.startWorkflow(caseAct.getTcase());
		addMessage(redirectAttributes, "事件启动成功");
		return "redirect:"+Global.getAdminPath()+"/task/todo";
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseCancel caseCancel, RedirectAttributes redirectAttributes) {
		caseCancelService.delete(caseCancel);
		addMessage(redirectAttributes, "删除案件撤销成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseCancel/?repage";
	}
	
	@RequestMapping(value = "exportPDF")
	public String exportPDF(CaseCancel entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseCancel caseCancel = caseCancelService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_CANCEL);	
		
		try {
            String fileName = "案件撤销"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseCancelExport export = new CaseCancelExport(tcase, caseCancel);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/cancelTab?"+entity.getParamUri();
	}

}