/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseHandleApproveExport;
import org.wxjs.les.modules.tcase.export.CaseHandleReportExport;
import org.wxjs.les.modules.tcase.service.CaseHandleService;
import org.wxjs.les.modules.tcase.service.CaseProcessService;
import org.wxjs.les.modules.tcase.service.TcaseService;

/**
 * 案件审理Controller
 * @author GLQ
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseHandle")
public class CaseHandleController extends BaseController {
	
	@Autowired
	private TcaseService tcaseService;

	@Autowired
	private CaseHandleService caseHandleService;
	
	@Autowired
	private CaseProcessService caseProcessService;
	
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
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseHandle caseHandle, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseHandle> page = caseHandleService.findPage(new Page<CaseHandle>(request, response), caseHandle); 
		model.addAttribute("page", page);
		return "modules/tcase/caseHandleList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseHandle caseHandle, Model model) {
		model.addAttribute("caseHandle", caseHandle);
		return "modules/tcase/caseHandleTab";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseHandle caseHandle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseHandle)){
			return form(caseHandle, model);
		}
		caseHandleService.save(caseHandle);
		addMessage(redirectAttributes, "保存案件审理成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/handleTab?"+caseHandle.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveUploadInfo")
	public String saveUploadInfo(CaseHandle caseHandle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseHandle)){
			return form(caseHandle, model);
		}
		caseHandleService.saveUploadInfo(caseHandle);
		
		addMessage(redirectAttributes, "保存案件审理成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/handleTab?"+caseHandle.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveReport")
	public String saveReport(CaseHandle caseHandle, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseHandle)){
			return form(caseHandle, model);
		}
		caseHandleService.saveReport(caseHandle);
		
		addMessage(redirectAttributes, "保存案件审理成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/handleTab?"+caseHandle.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseHandle caseHandle, RedirectAttributes redirectAttributes) {
		caseHandleService.delete(caseHandle);
		addMessage(redirectAttributes, "删除案件审理成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseHandle/?repage";
	}
	
	@RequestMapping(value = "exportReportPDF")
	public String exportReportPDF(CaseHandle entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseHandle caseHandle = caseHandleService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.get(entity.getCaseId());
		
		CaseProcess caseProcess = this.caseProcessService.get(tcase.getId(), Global.CASE_STAGE_HANDLE);
		
		tcase.setCaseProcess(caseProcess);
		
		try {
            String fileName = "案件调查报告"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseHandleReportExport export = new CaseHandleReportExport(tcase, caseHandle);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/handleTab?"+entity.getParamUri();
	}
	
	@RequestMapping(value = "exportApprovePDF")
	public String exportApprovePDF(CaseHandle entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseHandle caseHandle = caseHandleService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.get(entity.getCaseId());
		
		CaseProcess caseProcess = this.caseProcessService.get(tcase.getId(), Global.CASE_STAGE_HANDLE);
		
		tcase.setCaseProcess(caseProcess);
		
		try {
            String fileName = "案件处理审批表"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseHandleApproveExport export = new CaseHandleApproveExport(tcase, caseHandle);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/handleTab?"+entity.getParamUri();
	}

}