/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.cr.web;

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
import org.wxjs.les.modules.check.entity.Tsitecheck;
import org.wxjs.les.modules.check.export.TsitecheckExport;
import org.wxjs.les.modules.cr.entity.SiteCheckRecord;
import org.wxjs.les.modules.cr.export.SiteCheckRecordExport;
import org.wxjs.les.modules.cr.service.SiteCheckRecordService;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.StringUtils;

/**
 * 现场检查笔录Controller
 * @author 千里目
 * @version 2018-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/cr/siteCheckRecord")
public class SiteCheckRecordController extends BaseController {

	@Autowired
	private SiteCheckRecordService siteCheckRecordService;
	
	@ModelAttribute
	public SiteCheckRecord get(@RequestParam(required=false) String id) {
		SiteCheckRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = siteCheckRecordService.get(id);
		}
		if (entity == null){
			entity = new SiteCheckRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("cr:siteCheckRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SiteCheckRecord siteCheckRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SiteCheckRecord> page = siteCheckRecordService.findPage(new Page<SiteCheckRecord>(request, response), siteCheckRecord); 
		model.addAttribute("page", page);
		return "modules/cr/siteCheckRecordList";
	}

	@RequiresPermissions("cr:siteCheckRecord:view")
	@RequestMapping(value = "form")
	public String form(SiteCheckRecord siteCheckRecord, Model model) {
		if(siteCheckRecord.getIsNewRecord()){
			//tcase.setCaseStage("10");
			//tcase.setCaseStageStatus("0");
			siteCheckRecord.setSitePictureMemo("时间："+"2018年7月25日"+"\n"
			+"方位："+"\n"+"绘制人姓名："+"\n"+"身份：");
		}
		model.addAttribute("siteCheckRecord", siteCheckRecord);
		return "modules/cr/siteCheckRecordForm";
	}

	@RequiresPermissions("cr:siteCheckRecord:edit")
	@RequestMapping(value = "save")
	public String save(SiteCheckRecord siteCheckRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, siteCheckRecord)){
			return form(siteCheckRecord, model);
		}
		siteCheckRecordService.save(siteCheckRecord);
		addMessage(redirectAttributes, "保存现场检查笔录成功");
		return "redirect:"+Global.getAdminPath()+"/cr/siteCheckRecord/?repage";
	}
	
	@RequiresPermissions("cr:siteCheckRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(SiteCheckRecord siteCheckRecord, RedirectAttributes redirectAttributes) {
		siteCheckRecordService.delete(siteCheckRecord);
		addMessage(redirectAttributes, "删除现场检查笔录成功");
		return "redirect:"+Global.getAdminPath()+"/cr/siteCheckRecord/?repage";
	}
	
	
	@RequiresPermissions("cr:siteCheckRecord:view")
	@RequestMapping(value = "exportPDF")
	public String exportPDF(SiteCheckRecord siteCheckRecord, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		SiteCheckRecord record = siteCheckRecordService.get(siteCheckRecord.getId());
		model.addAttribute("siteCheckRecord", record);
		try {
            String fileName = "现场检查笔录"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            SiteCheckRecordExport export = new SiteCheckRecordExport(record);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		
		return "modules/cr/siteCheckRecordForm";
	}
}