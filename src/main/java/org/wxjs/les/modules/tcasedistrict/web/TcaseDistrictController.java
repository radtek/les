/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcasedistrict.web;

import java.util.Calendar;

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
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.SequenceUtils;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.tcasedistrict.entity.TcaseDistrict;
import org.wxjs.les.modules.tcasedistrict.service.TcaseDistrictService;

/**
 * 案件Controller
 * @author GLQ
 * @version 2019-01-31
 */
@Controller
@RequestMapping(value = "${adminPath}/tcasedistrict/tcaseDistrict")
public class TcaseDistrictController extends BaseController {

	@Autowired
	private TcaseDistrictService tcaseDistrictService;
	
	@ModelAttribute
	public TcaseDistrict get(@RequestParam(required=false) String id) {
		TcaseDistrict entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tcaseDistrictService.get(id);
		}
		if (entity == null){
			entity = new TcaseDistrict();
		}
		return entity;
	}
	
	@RequiresPermissions("tcasedistrict:tcaseDistrict:view")
	@RequestMapping(value = {"list", ""})
	public String list(TcaseDistrict tcaseDistrict, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(tcaseDistrict.getDateFrom() == null){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, -30);
			tcaseDistrict.setDateFrom(cal.getTime());
		}
		
		if(tcaseDistrict.getDateTo() == null){
			Calendar cal=Calendar.getInstance();
			tcaseDistrict.setDateTo(cal.getTime());			
		}
		
		User user = UserUtils.getUser();
		String handleOrg = user.getOffice().getId();
		if(handleOrg.startsWith("320")){
			tcaseDistrict.setHandleOrg(handleOrg);
		}
		
		Page<TcaseDistrict> page = tcaseDistrictService.findPage(new Page<TcaseDistrict>(request, response), tcaseDistrict); 
		model.addAttribute("page", page);
		return "modules/tcasedistrict/tcaseDistrictList";
	}
	
	@RequiresPermissions("tcasedistrict:tcaseDistrict:view")
	@RequestMapping(value = "form")
	public String form(TcaseDistrict tcaseDistrict, Model model) {
		
		logger.debug("tcaseDistrict.getReadonly():{}", tcaseDistrict.getReadonly());
		
		if(tcaseDistrict.getIsNewRecord()){
			tcaseDistrict.setPartyType(Global.PartyTypeOrg);
			tcaseDistrict.setAcceptDate(Calendar.getInstance().getTime());
			tcaseDistrict.setPsnSex("男");
			//tcase.setCaseStage("10");
			//tcase.setCaseStageStatus("0");
			
			tcaseDistrict.setStatus("0");
			
			long seq = SequenceUtils.fetchSeq("tcase_district");
			tcaseDistrict.setId(seq + "");
			tcaseDistrict.setIsNewRecord(true);
			
			//set handleOrg
			User user = UserUtils.getUser();
			String handleOrg = user.getOffice().getId();
			tcaseDistrict.setHandleOrg(handleOrg);
		}
		
		model.addAttribute("tcaseDistrict", tcaseDistrict);
		String readonly = tcaseDistrict.getReadonly();
		if("1".equals(readonly)){
			model.addAttribute("readonly", readonly);
		}
		
		return "modules/tcasedistrict/tcaseDistrictForm";
	}
	
	@RequiresPermissions("tcasedistrict:tcaseDistrict:view")
	@RequestMapping(value = {"query"})
	public String query(TcaseDistrict tcaseDistrict, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(tcaseDistrict.getDateFrom() == null){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, -90);
			tcaseDistrict.setDateFrom(cal.getTime());
		}
		
		if(tcaseDistrict.getDateTo() == null){
			Calendar cal=Calendar.getInstance();
			tcaseDistrict.setDateTo(cal.getTime());			
		}
		
		User user = UserUtils.getUser();
		String handleOrg = user.getOffice().getId();
		if(handleOrg.startsWith("320")){
			tcaseDistrict.setHandleOrg(handleOrg);
		}
		
		Page<TcaseDistrict> page = tcaseDistrictService.findPage(new Page<TcaseDistrict>(request, response), tcaseDistrict); 
		model.addAttribute("page", page);
		logger.debug("handleOrg:{}", handleOrg);
		model.addAttribute("userOffice", handleOrg);
		
		return "modules/tcasedistrict/tcaseDistrictQuery";
	}

	@RequiresPermissions("tcasedistrict:tcaseDistrict:edit")
	@RequestMapping(value = "save")
	public String save(TcaseDistrict tcaseDistrict, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tcaseDistrict)){
			return form(tcaseDistrict, model);
		}
		tcaseDistrictService.save(tcaseDistrict);
		addMessage(redirectAttributes, "保存案件成功");
		return "redirect:"+Global.getAdminPath()+"/tcasedistrict/tcaseDistrict/?repage";
	}
	
	@RequiresPermissions("tcasedistrict:tcaseDistrict:edit")
	@RequestMapping(value = "commit")
	public String commit(TcaseDistrict tcaseDistrict, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tcaseDistrict)){
			return form(tcaseDistrict, model);
		}
		tcaseDistrictService.commit(tcaseDistrict);
		
		addMessage(redirectAttributes, "提交案件成功");
		return "redirect:"+Global.getAdminPath()+"/tcasedistrict/tcaseDistrict/?repage";
	}	
	
	@RequiresPermissions("tcasedistrict:tcaseDistrict:edit")
	@RequestMapping(value = "delete")
	public String delete(TcaseDistrict tcaseDistrict, RedirectAttributes redirectAttributes) {
		tcaseDistrictService.delete(tcaseDistrict);
		addMessage(redirectAttributes, "删除案件成功");
		return "redirect:"+Global.getAdminPath()+"/tcasedistrict/tcaseDistrict/?repage";
	}

}