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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.Page;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseTransferExport;
import org.wxjs.les.modules.tcase.service.CaseProcessService;
import org.wxjs.les.modules.tcase.service.CaseTransferService;
import org.wxjs.les.modules.tcase.service.TcaseService;

/**
 * 案件结案Controller
 * @author GLQ
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/tcase/caseTransfer")
public class CaseTransferController extends TcaseController {
	
	@Autowired
	private TcaseService tcaseService;
	
	@Autowired
	private CaseTransferService caseTransferService;
	
	@Autowired
	private CaseProcessService caseProcessService;
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(Tcase tcase, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		tcase.setCaseTransfer("1"); //案源移交标记
		
		if(tcase.getAcceptDateFrom() == null){
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, -180);
			tcase.setCreateDateFrom(cal.getTime()); 
		}
		
		if(tcase.getAcceptDateTo() == null){
			Calendar cal=Calendar.getInstance();
			tcase.setCreateDateTo(cal.getTime());			
		}
		
		Page<Tcase> page = caseTransferService.findPage(new Page<Tcase>(request, response), tcase); 
		model.addAttribute("page", page);
		return "modules/tcase/tcaseListTransfer";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "toStart")
	public String toStart(CaseAct caseAct, Model model) {
		
		caseAct.setCaseTransfer("1");
		
		caseAct.setOperateType("start");
		
		Tcase tcase = new Tcase();
		
		tcase.setIsNewRecord(true);
		tcase.setPartyType(Global.PartyTypeOrg);
		tcase.setAcceptDate(Calendar.getInstance().getTime());
		tcase.setPsnSex("男");
		
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setCaseStage(Global.CASE_STAGE_TRANSFER);
		tcase.setCaseProcess(caseProcess);
		
		if("start".equals(caseAct.getOperateType())){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);			
		}
		
		caseAct.setTcase(tcase);
		
		return "modules/tcase/tcaseInfoTabTransfer";
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "infoTab")
	public String infoTab(CaseAct caseAct, Model model) {
		
		caseAct.setCaseTransfer("1");
		
		String businesskey = caseAct.getBusinesskey();
		
		Tcase tcase = tcaseService.getCaseAndProcess(businesskey);
		
		logger.debug("businesskey:{}", businesskey);
		
		if(tcase.getCaseProcess() != null){
			List<User> availableHandlers = this.getCaseHandler4Start(tcase.getCaseProcess());
			tcase.getCaseProcess().setAvailableHandlers(availableHandlers);			
		}
	
		caseAct.setTcase(tcase);
		
		this.prepare4Approve(caseAct, model);
		
		return "modules/tcase/tcaseInfoTabTransfer";
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAct)){
			return infoTab(caseAct, model);
		}
		Tcase tcase = caseAct.getTcase();
		caseTransferService.save(tcase);
		
		model.addAttribute("operateType", caseAct.getOperateType());
		
		String businesskey = tcase.getId()+":"+tcase.getCaseProcess().getId();
		
		logger.debug("caseAct.getOperateType():{}, businesskey:{}", caseAct.getOperateType(), businesskey);
		
		
		addMessage(redirectAttributes, "保存案件成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseTransfer/infoTab?businesskey="+businesskey+"&operateType="+caseAct.getOperateType();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveAndStart")
	public String saveAndStart(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, caseAct)){
			return infoTab(caseAct, model);
		}
		caseTransferService.saveAndStartFlow(caseAct.getTcase());
		addMessage(redirectAttributes, "事件启动成功");
		return "redirect:"+Global.getAdminPath()+"/task/todo";
	}
	
	@RequestMapping(value = "exportPDF")
	public String exportPDF(CaseAct caseAct, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		Tcase tcase = caseTransferService.get(caseAct.getTcase().getId());
		
		CaseProcess caseProcess = this.caseProcessService.get(caseAct.getTcase().getId(), Global.CASE_STAGE_TRANSFER);
		
		tcase.setCaseProcess(caseProcess);
		
		try {
            String fileName = "案件移送单"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseTransferExport export = new CaseTransferExport(tcase);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/tcase/caseTransfer/infoTab?"+caseAct.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(Tcase tcase, RedirectAttributes redirectAttributes) {
		caseTransferService.delete(tcase);
		addMessage(redirectAttributes, "删除案件成功");
		return "redirect:"+Global.getAdminPath()+"/tcase/caseTransfer/infoTab?repage";
	}

}