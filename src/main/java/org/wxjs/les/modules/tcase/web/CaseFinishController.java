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
import org.wxjs.les.common.utils.Util;

import org.wxjs.les.modules.tcase.entity.CaseFinish;
import org.wxjs.les.modules.tcase.entity.Tcase;

import org.wxjs.les.modules.tcase.export.CaseFinishExport;
import org.wxjs.les.modules.tcase.service.CaseFinishService;
import org.wxjs.les.modules.tcase.service.TcaseService;

/**
 * 案件结束Controller
 * @author GLQ
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseFinish")
public class CaseFinishController extends BaseController {
	
	@Autowired
	private TcaseService tcaseService;

	@Autowired
	private CaseFinishService caseFinishService;
	
	@ModelAttribute
	public CaseFinish get(@RequestParam(required=false) String id) {
		CaseFinish entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseFinishService.get(id);
		}
		if (entity == null){
			entity = new CaseFinish();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseFinish caseFinish, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseFinish> page = caseFinishService.findPage(new Page<CaseFinish>(request, response), caseFinish); 
		model.addAttribute("page", page);
		return "modules/tcase/caseFinishList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseFinish caseFinish, Model model) {
		model.addAttribute("caseFinish", caseFinish);
		return "modules/tcase/caseFinishTab";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseFinish caseFinish, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseFinish)){
			return form(caseFinish, model);
		}
		
		if(Util.getInteger(caseFinish.getTotalPage(),0) != caseFinish.getPageSum()){
			addMessage(redirectAttributes, "保存案件结束失败！页数合计与总数不一致。");
		}else{
			caseFinishService.save(caseFinish);
			addMessage(redirectAttributes, "保存案件结束成功");
			
		}

		return "redirect:"+Global.getAdminPath()+"/case/tcase/finishTab?"+caseFinish.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseFinish caseFinish, RedirectAttributes redirectAttributes) {
		caseFinishService.delete(caseFinish);
		addMessage(redirectAttributes, "删除案件结束成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseFinish/?repage";
	}
	
	@RequestMapping(value = "exportPDF")
	public String exportPDF(CaseFinish entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseFinish caseFinish = caseFinishService.get(entity.getCaseId());

		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_FINISH);
		
		try {
            String fileName = "备考表"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseFinishExport export = new CaseFinishExport(tcase, caseFinish);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/finishTab?"+entity.getParamUri();
	}

}