/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.qa.web;

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
import org.wxjs.les.modules.qa.entity.Questionanswer;
import org.wxjs.les.modules.qa.export.QaExport;
import org.wxjs.les.modules.qa.service.QuestionanswerService;

/**
 * 询问笔录Controller
 * @author GLQ
 * @version 2018-07-02
 */
@Controller
@RequestMapping(value = "${adminPath}/qa/questionanswer")
public class QuestionanswerController extends BaseController {

	@Autowired
	private QuestionanswerService questionanswerService;
	
	@ModelAttribute
	public Questionanswer get(@RequestParam(required=false) String id) {
		Questionanswer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = questionanswerService.get(id);
		}
		if (entity == null){
			entity = new Questionanswer();
		}
		return entity;
	}
	
	@RequiresPermissions("qa:questionanswer:view")
	@RequestMapping(value = {"list", ""})
	public String list(Questionanswer questionanswer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Questionanswer> page = questionanswerService.findPage(new Page<Questionanswer>(request, response), questionanswer); 
		model.addAttribute("page", page);
		return "modules/qa/questionanswerList";
	}

	@RequiresPermissions("qa:questionanswer:view")
	@RequestMapping(value = "form")
	public String form(Questionanswer questionanswer, Model model) {
		model.addAttribute("questionanswer", questionanswer);
		return "modules/qa/questionanswerForm";
	}
	
	@RequiresPermissions("qa:questionanswer:view")
	@RequestMapping(value = "infoTab")
	public String infoTab(Questionanswer questionanswer, Model model) {
		model.addAttribute("questionanswer", questionanswer);
		return "modules/qa/questionanswerInfoTab";
	}
	
	@RequiresPermissions("qa:questionanswer:view")
	@RequestMapping(value = "qaTab")
	public String qaTab(Questionanswer questionanswer, Model model) {
		
		//logger.debug("questionanswer.getAsig().toString():{}", questionanswer.getAsig().toString());
		
		model.addAttribute("questionanswer", questionanswer);
		return "modules/qa/questionanswerQaTab";
	}

	@RequiresPermissions("qa:questionanswer:edit")
	@RequestMapping(value = "save")
	public String save(Questionanswer questionanswer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, questionanswer)){
			return form(questionanswer, model);
		}
		questionanswerService.save(questionanswer);
		addMessage(redirectAttributes, "保存询问笔录成功");
		return "redirect:"+Global.getAdminPath()+"/qa/questionanswer/?repage";
	}
	
	@RequiresPermissions("qa:questionanswer:edit")
	@RequestMapping(value = "saveInfo")
	public String saveInfo(Questionanswer questionanswer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, questionanswer)){
			return form(questionanswer, model);
		}
		questionanswerService.saveInfo(questionanswer);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/qa/questionanswer/infoTab?id="+questionanswer.getId();
	}
	
	@RequiresPermissions("qa:questionanswer:edit")
	@RequestMapping(value = "saveQa")
	public String saveQa(Questionanswer questionanswer, Model model, RedirectAttributes redirectAttributes) {

		questionanswerService.saveQa(questionanswer);
		
		logger.debug("saveQa...");
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/qa/questionanswer/qaTab?id="+questionanswer.getId();
	}
	
	@RequiresPermissions("qa:questionanswer:edit")
	@RequestMapping(value = "delete")
	public String delete(Questionanswer questionanswer, RedirectAttributes redirectAttributes) {
		questionanswerService.delete(questionanswer);
		addMessage(redirectAttributes, "删除询问笔录成功");
		return "redirect:"+Global.getAdminPath()+"/qa/questionanswer/?repage";
	}
	
	@RequiresPermissions("qa:questionanswer:view")
	@RequestMapping(value = "exportPDF")
	public String exportPDF(Questionanswer questionanswer, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		Questionanswer qa = questionanswerService.get(questionanswer.getId());
		
		model.addAttribute("questionanswer", qa);
		
		try {
            String fileName = "询问笔录"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            QaExport export = new QaExport(qa);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "modules/qa/questionanswerInfoTab";
	}

	@RequestMapping(value = "test")
	public String test(RedirectAttributes redirectAttributes) {
		return "/modules/test/signature";
	}

}