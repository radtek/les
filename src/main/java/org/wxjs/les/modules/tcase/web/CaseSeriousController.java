/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.web;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.task.TaskDefinition;
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
import org.wxjs.les.modules.act.service.ProcessService;
import org.wxjs.les.modules.base.dao.SignatureLibDao;
import org.wxjs.les.modules.base.entity.ActTask;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.base.entity.SignatureLib;
import org.wxjs.les.modules.base.service.SignatureService;
import org.wxjs.les.modules.message.DbMessageSender;
import org.wxjs.les.modules.message.MessageSender;
import org.wxjs.les.modules.sys.utils.DictUtils;
import org.wxjs.les.modules.sys.utils.UserUtils;
import org.wxjs.les.modules.task.entity.CaseAct;
import org.wxjs.les.modules.task.service.CaseTaskService;
import org.wxjs.les.modules.tcase.entity.CaseHandle;
import org.wxjs.les.modules.tcase.entity.CaseProcess;
import org.wxjs.les.modules.tcase.entity.CaseSerious;
import org.wxjs.les.modules.tcase.entity.Tcase;
import org.wxjs.les.modules.tcase.export.CaseSeriousExport;
import org.wxjs.les.modules.tcase.export.CaseSeriousRecordExport;
import org.wxjs.les.modules.tcase.service.CaseHandleService;
import org.wxjs.les.modules.tcase.service.CaseProcessService;
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
	
	@Autowired
	private CaseHandleService caseHandleService;
	
	@Autowired
	private CaseProcessService caseProcessService;
	
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	HistoryService historyService;
	
	@Autowired
	private CaseTaskService actTaskService;
	
	@Autowired
	private SignatureService signatureService;
	
	@Autowired
	private SignatureLibDao signatureLibDao;
	
	@Autowired
	ProcessService processService;
	
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
		
		logger.debug("caseSerious.getIsNewRecord():", caseSerious.getIsNewRecord());
		
		if(caseSerious.getIsNewRecord()){
			//fact
			CaseHandle caseHandle = caseHandleService.get(caseSerious.getCaseId());

			caseSerious.setCaseSummary(caseHandle.getFact());
			
			//opinion
			CaseProcess caseProcess = new CaseProcess();
			caseProcess.setCaseId(caseHandle.getCaseId());
			caseProcess.setCaseStage(Global.CASE_STAGE_HANDLE);
			List<CaseProcess> caseProcessList = caseProcessService.findList(caseProcess);
			
			String procInstId = "";
			if(caseProcessList.size()>0){
				procInstId = caseProcessList.get(0).getProcDefId();
			}
			
			String opinion = "";
			if(StringUtils.isNotEmpty(procInstId)){
				Signature signatureParam = new Signature(false);
				signatureParam.setProcInstId(procInstId);
				signatureParam.setTaskName("办案人意见");
				List<Signature> signatureList = signatureService.findList4Export(signatureParam);
				for(Signature sig : signatureList){
					if(sig.getApproveOpinion().length()>opinion.length()){
						opinion = sig.getApproveOpinion();
					}
				}
				caseSerious.setPunishProposal(opinion);
			}

		}
		
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
	@RequestMapping(value = "saveMeetingRecord")
	public String saveMeetingRecord(CaseSerious caseSerious, Model model, RedirectAttributes redirectAttributes) {
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
		
		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_SERIOUS);	
		
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
	
	@RequestMapping(value = "exportRecordPDF")
	public String exportRecordPDF(CaseSerious entity, HttpServletResponse response,  Model model, RedirectAttributes redirectAttributes) {
		
		CaseSerious caseSerious = caseSeriousService.get(entity.getCaseId());
		
		Tcase tcase = tcaseService.getCaseAndProcess(entity.getCaseId(), Global.CASE_STAGE_SERIOUS);	
		
		try {
            String fileName = "重大行政处罚会议记录"+DateUtils.getDate("yyyyMMddHHmmss")+".pdf";
            CaseSeriousRecordExport export = new CaseSeriousRecordExport(tcase, caseSerious);
            export.write(response, fileName);
    		return null;
		} catch (Exception e) {
			logger.error("导出失败", e);
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}		

		return "redirect:"+Global.getAdminPath()+"/case/tcase/seriousTab?"+entity.getParamUri();
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "saveAndStart")
	public String saveAndStart(CaseAct caseAct, Model model, RedirectAttributes redirectAttributes) {
		//tcaseService.saveAndStartFlow(caseAct.getTcase());
		tcaseService.saveProcess(caseAct.getTcase());
		caseSeriousService.startWorkflow(caseAct.getTcase());
		addMessage(redirectAttributes, "事件启动成功");
		return "redirect:"+Global.getAdminPath()+"/task/todo";
	}
	
	@RequiresPermissions("case:tcase:edit")
	@RequestMapping(value = "handletask")	
	public String handletask(ActTask actTask, Model model, RedirectAttributes redirectAttributes) {
		
		String userid = UserUtils.getUser().getLoginName();
		
		Map<String,Object> variables = actTask.getVariables();
		
		//设置处理结果
		String variable = processService.getConditionVariable(actTask.getTaskId());
		
		logger.debug("handletask variable:{}", variable);
		
		variables.put(variable, actTask.getApprove());
		
		logger.debug("handletask actTask.getTaskid():{}", actTask.getTaskId());
		
		//设置下一关
		TaskDefinition taskDef = null;
		String taskDefKey = "";
		try {
			taskDef = processService.getTaskDefinition(actTask.getTaskId());
			taskDefKey = taskDef.getKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("taskDefKey:{}", taskDefKey);
		
		String caseId =  actTask.getCaseIdFromBusinesskey();
		CaseSerious caseSerious = caseSeriousService.get(caseId);
		
		if("writeTask".equals(taskDefKey)){
			//设置参会人员会签

			List<String> voters = caseSerious.getVoter().getLoginNames();
			variables.put("assigneeList", voters);			
			logger.debug("voters:{}", voters.toString());
		}
		
		logger.debug("actTask.getApprove():{}", actTask.getApprove());
		
		String signatureStr = "";
		
		StringBuffer comment = new StringBuffer();
		if("pass".equals(actTask.getApprove())){
			comment.append("[通过]");
			
			//check whether signature exists
			SignatureLib signatureLibParam = new SignatureLib();
			signatureLibParam.setUser(UserUtils.getUser());
			SignatureLib signatureLib = signatureLibDao.get(signatureLibParam);

			if(signatureLib!=null && StringUtils.isNotEmpty(signatureLib.getSignature())){
				signatureStr = signatureLib.getSignature();
			}else{
				addMessage(redirectAttributes, "请先设置您的签名！");
				return null;
			}
		}else if("return".equals(actTask.getApprove())){
			comment.append("[退回]");
		}else if("cancel".equals(actTask.getApprove())){
			comment.append("[不通过]");
		}
		comment.append(actTask.getApproveOpinion());
		//add signature
		//comment.append(Global.SignatureTag);
		//comment.append(actTask.getSignature().getId());
		
		taskService.addComment(actTask.getTaskId(), actTask.getProcInsId(), comment.toString());
		
		taskService.claim(actTask.getTaskId(), userid);
		taskService.complete(actTask.getTaskId(), variables);
		
		//update stage status if necessary
		CaseProcess caseProcess = new CaseProcess();
		caseProcess.setProcInsId(actTask.getProcInsId());
		if("pass".equals(actTask.getApprove())){
			
			//update opinion to signature
			
			Signature signature = new Signature(true);
			signature.setTitle(Signature.DefaultTitle);

			signature.setSignature(signatureStr);
			signatureService.save(signature);
			
			signature.setProcInstId(actTask.getProcInsId());
			signature.setTaskId(actTask.getTaskId());
			signature.setTaskName(actTask.getTaskName());
			signature.setApproveOpinion(actTask.getApproveOpinion());
			signatureService.updateOpinion(signature);
			
			logger.debug("nextTaskDef==null:{}",(taskDef==null));
			if("signTask".equals(taskDefKey)){ //if nextTaskDef is null, it is the last userTask 
				//判断所有人签过
				long activeCount = taskService.createTaskQuery().processInstanceId(actTask.getProcInsId()).active().count();
				logger.debug("activeCount:{}", activeCount);
				if(activeCount == 0){
					caseProcess.setCaseStageStatus(Global.CASE_STAGE_STATUS_FINISHED);
					this.caseProcessService.updateStageStatus(caseProcess);					
				}
			}else{
				//send message
				MessageSender sender = new DbMessageSender();
				Tcase tcase = tcaseService.getCaseAndProcess(actTask.getBusinesskey());
				try {
					sender.send(caseSerious.getVoter().getLoginName(), "综合行政执法系统中您有1个待办事项。项目名称："+tcase.getCaseCause()
					+", 事项："+DictUtils.getDictLabel(tcase.getCaseProcess().getCaseStage(), "case_stage", "")+"。");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if("cancel".equals(actTask.getApprove())){
			caseProcess.setCaseStageStatus(Global.CASE_STAGE_STATUS_CANCELED);
			this.caseProcessService.updateStageStatus(caseProcess);
		}		
		
		return "redirect:"+Global.getAdminPath()+"/task/todo";
	}

}