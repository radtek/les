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

import org.wxjs.les.modules.tcase.entity.CaseSerious;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseSeriousExport;
import org.wxjs.les.modules.tcase.service.CaseSeriousService;
import org.wxjs.les.modules.tcase.service.TcaseService;

/**
 * 重大行政处罚Controller
 * @author GLQ
 * @version 2018-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseSerious")
public class CaseSeriousController extends BaseController {

	@Autowired
	private CaseSeriousService caseSeriousService;
	
	@Autowired
	private TcaseService tcaseService;
	
	@ModelAttribute
	public CaseSerious get(@RequestParam(required=false) String id) {
		CaseSerious entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseSeriousService.get(id);
		}
		if (entity == null){
			entity = new CaseSerious();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseSerious caseSerious, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseSerious> page = caseSeriousService.findPage(new Page<CaseSerious>(request, response), caseSerious); 
		model.addAttribute("page", page);
		return "modules/tcase/caseSeriousList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseSerious caseSerious, Model model) {
		model.addAttribute("caseSerious", caseSerious);
		return "modules/tcase/caseSeriousForm";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseSerious caseSerious, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseSerious)){
			return form(caseSerious, model);
		}
		caseSeriousService.save(caseSerious);
		addMessage(redirectAttributes, "保存重大行政处罚成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/seriousTab?"+caseSerious.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseSerious caseSerious, RedirectAttributes redirectAttributes) {
		caseSeriousService.delete(caseSerious);
		addMessage(redirectAttributes, "删除重大行政处罚成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseSerious/?repage";
	}
	

	@RequestMapping(value = "exportPDF")
	public String exportPDF(CaseSerious entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseSerious caseSerious = caseSeriousService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.get(entity.getCaseId());
		
		try {
            String fileName = "重大行政处罚审查"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseSeriousExport export = new CaseSeriousExport(tcase, caseSerious);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/seriousTab?"+entity.getParamUri();
	}

}