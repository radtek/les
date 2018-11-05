/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.web;

import java.io.File;

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
import org.wxjs.les.common.utils.Util;
import org.wxjs.les.modules.base.export.ExportBase;
import org.wxjs.les.modules.base.utils.PathUtils;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.tcase.dao.CaseAttachDao;
import org.wxjs.les.modules.tcase.entity.CaseAttach;
import org.wxjs.les.modules.tcase.entity.CaseDecision;
import org.wxjs.les.modules.tcase.entity.CaseFinish;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.CaseSettle;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseDecisionLaunchExport;
import org.wxjs.les.modules.tcase.export.CaseFinishExport;
import org.wxjs.les.modules.tcase.export.CaseHandleApproveExport;
import org.wxjs.les.modules.tcase.export.CaseHandleReportExport;
import org.wxjs.les.modules.tcase.export.CaseInitialExport;
import org.wxjs.les.modules.tcase.export.CaseSettleExport;
import org.wxjs.les.modules.tcase.service.CaseAttachService;
import org.wxjs.les.modules.tcase.service.CaseCancelService;
import org.wxjs.les.modules.tcase.service.CaseDecisionService;
import org.wxjs.les.modules.tcase.service.CaseFinishService;
import org.wxjs.les.modules.tcase.service.CaseHandleService;
import org.wxjs.les.modules.tcase.service.CaseSeriousService;
import org.wxjs.les.modules.tcase.service.CaseSettleService;
import org.wxjs.les.modules.tcase.service.TcaseService;

import com.ckfinder.connector.utils.FileUtils;
import com.lowagie.text.DocumentException;

/**
 * 案件资料Controller
 * @author GLQ
 * @version 2018-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/case/caseAttach")
public class CaseAttachController extends BaseController {
	
	@Autowired
	private CaseAttachDao caseAttachDao;

	@Autowired
	private CaseAttachService caseAttachService;
	
	@Autowired
	private TcaseService caseService;
	
	@Autowired
	private CaseDecisionService caseDecisionService;
	
	@Autowired
	private CaseSettleService caseSettleService;
	
	@Autowired
	private CaseFinishService caseFinishService;
	
	@Autowired
	private CaseHandleService caseHandleService;
	
	@ModelAttribute
	public CaseAttach get(@RequestParam(required=false) String id) {
		CaseAttach entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseAttachService.get(id);
		}
		if (entity == null){
			entity = new CaseAttach();
		}
		return entity;
	}
	
	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = {"list", ""})
	public String list(CaseAttach caseAttach, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseAttach> page = caseAttachService.findPage(new Page<CaseAttach>(request, response), caseAttach); 
		model.addAttribute("page", page);
		return "modules/tcase/caseAttachList";
	}

	@RequiresPermissions("case:tcase:view")
	@RequestMapping(value = "form")
	public String form(CaseAct caseAct, HttpServletRequest request, Model model) {
		String businesskey = caseAct.getBusinesskey();
		Tcase tcase = caseService.getCaseAndProcess(businesskey);
		caseAct.setTcase(tcase);
		
		String attachId = Util.getString(request.getParameter("attachId"));
		CaseAttach caseAttach = new CaseAttach();
		if(tcase.getCaseTransfer().equals("1")) {
			caseAttach.setFlowNode("案源移交流程");
		}
		if(StringUtils.isNotEmpty(attachId)){
			caseAttach = caseAttachService.get(attachId);
		}
		model.addAttribute("caseAttach", caseAttach);
		return "modules/tcase/caseAttachForm";
	}

	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "save")
	public String save(CaseAttach caseAttach, Model model, RedirectAttributes redirectAttributes) {

		String filename = "";
		String filepath = caseAttach.getFilepath();
		if(!StringUtils.isEmpty(filepath)){
			filename = filepath.substring(filepath.lastIndexOf("/")+1);
		}
		caseAttach.setFilename(PathUtils.decodeUri(filename));
		
		caseAttachService.save(caseAttach);
		addMessage(redirectAttributes, "保存案件资料成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/attachTab?"+caseAttach.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "delete")
	public String delete(CaseAct caseAct, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String attachId = Util.getString(request.getParameter("attachId"));
		CaseAttach caseAttach = new CaseAttach();
		caseAttach.setId(attachId);
		caseAttachService.delete(caseAttach);
		addMessage(redirectAttributes, "删除案件资料成功");
		return "redirect:"+Global.getAdminPath()+"/case/tcase/attachTab?"+caseAct.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "uploadWithOneClick")
	public String uploadWithOneClick(CaseAct caseAct, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String attachId = Util.getString(request.getParameter("attachId"));
		
		CaseAttach caseAttach = caseAttachDao.get(attachId);
		
		String filePath = "";
		String filename = "";
		
		User user = UserUtils.getUser();
		
		String userId = user.getId();
		
		String caseId = caseAttach.getCaseId();
		
		Tcase tcase = caseService.getCaseAndProcess(caseId, caseAttach.getFlowNode());
		
		ExportBase export = null;
		
		boolean dataPrepared = true;
		
		if(CaseAttach.reportTypes.发文稿.toString().equals(caseAttach.getAttachType())){
			
			CaseDecision caseDecision = caseDecisionService.get(caseId);
			if(caseDecision == null){
				dataPrepared = false;
			}
			export = new CaseDecisionLaunchExport(tcase, caseDecision);
			
			filename = "处罚决定发文稿.pdf";
			
			filePath = PathUtils.makeFilePath(userId, caseId, filename);
			
		}else if(CaseAttach.reportTypes.处理审批表.toString().equals(caseAttach.getAttachType())){
			
			CaseHandle caseHandle = caseHandleService.get(caseId);
			
			if(caseHandle == null){
				dataPrepared = false;
			}
			export = new CaseHandleApproveExport(tcase, caseHandle);
			
			filename = "案件处理审批表.pdf";
			
			filePath = PathUtils.makeFilePath(userId, caseId, filename);
			
		}else if(CaseAttach.reportTypes.备考表.toString().equals(caseAttach.getAttachType())){
			
			CaseFinish caseFinish = caseFinishService.get(caseId);
			
			if(caseFinish == null){
				dataPrepared = false;
			}
			
			export = new CaseFinishExport(tcase, caseFinish);
			
			filename = "备考表.pdf";
			
			filePath = PathUtils.makeFilePath(userId, caseId, filename);

		}else if(CaseAttach.reportTypes.立案审批表.toString().equals(caseAttach.getAttachType())){
			export = new CaseInitialExport(tcase);
			
			filename = "案件立案审批表.pdf";
			
			filePath = PathUtils.makeFilePath(userId, caseId, filename);

		}else if(CaseAttach.reportTypes.结案审批表.toString().equals(caseAttach.getAttachType())){
			
			CaseSettle caseSettle = caseSettleService.get(caseId);
			
			if(caseSettle == null){
				dataPrepared = false;
			}
			
			export = new CaseSettleExport(tcase, caseSettle);
			
			filename = "案件结案审批表.pdf";
			
			filePath = PathUtils.makeFilePath(userId, caseId, filename);
		
		}else if(CaseAttach.reportTypes.调查报告.toString().equals(caseAttach.getAttachType())){
			
			CaseHandle caseHandle = caseHandleService.get(caseId);
			
			if(caseHandle == null){
				dataPrepared = false;
			}
			
			export = new CaseHandleReportExport(tcase, caseHandle);
			
			filename = "案件调查报告.pdf";
			
			filePath = PathUtils.makeFilePath(userId, caseId, filename);
		}
		
		if(!dataPrepared){
			addMessage(redirectAttributes, "上传案件资料失败！未能获取数据。");
			return "redirect:"+Global.getAdminPath()+"/case/tcase/attachTab?"+caseAct.getParamUri();
		}
		
		if(export == null){
			addMessage(redirectAttributes, "上传案件资料失败");
			return "redirect:"+Global.getAdminPath()+"/case/tcase/attachTab?"+caseAct.getParamUri();
		}
		
		try {
			String realPath = PathUtils.getRealPath(filePath);
			File file = new File(realPath);
			file.getParentFile().mkdirs();
			export.generate(realPath);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//save file path
		caseAttach.setFilename(filename);
		caseAttach.setFilepath(filePath);

		caseAttachService.save(caseAttach);
		
		addMessage(redirectAttributes, "上传案件资料成功");
		
		return "redirect:"+Global.getAdminPath()+"/case/tcase/attachTab?"+caseAct.getParamUri();
	}

}