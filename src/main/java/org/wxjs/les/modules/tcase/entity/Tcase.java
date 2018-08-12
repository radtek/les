/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.sys.entity.User;

/**
 * 案件Entity
 * @author GLQ
 * @version 2018-07-09
 */
public class Tcase extends DataEntity<Tcase> {
	
	private static final long serialVersionUID = 1L;
	
	private static final int FieldShortLen = 12;
	
	private String caseSeq;		// 事项编号
	private String accepter;		// 受理人
	private Date acceptDate;		// 受理时间
	private String caseSource;		// 案件来源
	private String partyType;		// 当事人类型
	private String orgName;		// 名称
	private String orgAgent;		// 法定代表人
	private String orgCode;		// 统一社会信用代码
	private String orgResponsiblePerson;		// 负责人
	private String orgResponsiblePersonPost;    // 负责人职务
	private String orgAddress;		// 住址
	private String orgPhone;		// 联系电话
	private String psnName;		// 姓名
	private String psnOrganization;		// 工作单位
	private String psnCode;		// 身份证
	private Date psnBirthday;		// 出生年月
	private String psnPost;		// 职务
	private String psnAddress;		// 住址
	private String psnPhone;		// 联系电话
	private String psnSex;		// 性别
	private String caseHappenDate;		// 案发时间
	private String caseHappenAddress;		// 案发地点
	private String projectCode;		// 案件所涉项目代码
	private String projectName;		// 案件所涉项目名称
	private String caseCause;		// 案由
	
	private String caseTransfer;    //是否案源移交， 1：是案源移交
	private String transferCaseId;  //移交后的case id
	
	private String status;
	
	private CaseProcess caseProcess; //
	
	private CaseAttach caseAttach; //
	
	private List<CaseProcess> currentCaseProcesses = Lists.newArrayList(); //当前process
	
	private List<CaseProcess> caseProcesses = Lists.newArrayList(); //相关process
	
	
	//-- 临时属性 --//
	// 流程任务
	private Task task;
	private Map<String, Object> variables;
	// 运行中的流程实例
	private ProcessInstance processInstance;
	// 历史的流程实例
	private HistoricProcessInstance historicProcessInstance;
	// 流程定义
	private ProcessDefinition processDefinition;
	
	private Date acceptDateFrom;		// 受理时间起始
	private Date acceptDateTo;		// 受理时间截止
	
	private Date createDateFrom;		// 创建时间起始
	private Date createDateTo;		// 创建时间截止
	
	private String oldCaseId;
	
	public Tcase() {
		super();
	}

	public Tcase(String id){
		super(id);
	}
	
	public String getCaseSeq() {
		return caseSeq;
	}

	public void setCaseSeq(String caseSeq) {
		this.caseSeq = caseSeq;
	}

	@Length(min=1, max=32, message="受理人长度必须介于 1 和 32 之间")
	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	@Length(min=1, max=100, message="案件来源长度必须介于 1 和 100 之间")
	public String getCaseSource() {
		return caseSource;
	}

	public void setCaseSource(String caseSource) {
		this.caseSource = caseSource;
	}
	
	@Length(min=1, max=32, message="当事人类型长度必须介于 1 和 32 之间")
	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@Length(min=0, max=32, message="法定代表人长度必须介于 1 和 32 之间")
	public String getOrgAgent() {
		return orgAgent;
	}

	public void setOrgAgent(String orgAgent) {
		this.orgAgent = orgAgent;
	}
	
	@Length(min=0, max=32, message="统一社会信用代码长度必须介于 1 和 32 之间")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	@Length(min=0, max=100, message="负责人长度必须介于 1 和 100 之间")
	public String getOrgResponsiblePerson() {
		return orgResponsiblePerson;
	}

	public void setOrgResponsiblePerson(String orgResponsiblePerson) {
		this.orgResponsiblePerson = orgResponsiblePerson;
	}
	
	public String getOrgResponsiblePersonPost() {
		return orgResponsiblePersonPost;
	}

	public void setOrgResponsiblePersonPost(String orgResponsiblePersonPost) {
		this.orgResponsiblePersonPost = orgResponsiblePersonPost;
	}

	@Length(min=0, max=100, message="住址长度必须介于 1 和 100 之间")
	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	
	@Length(min=0, max=32, message="联系电话长度必须介于 1 和 32 之间")
	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}
	
	@Length(min=0, max=32, message="姓名长度必须介于 1 和 32 之间")
	public String getPsnName() {
		return psnName;
	}

	public void setPsnName(String psnName) {
		this.psnName = psnName;
	}
	
	@Length(min=0, max=100, message="工作单位长度必须介于 1 和 100 之间")
	public String getPsnOrganization() {
		return psnOrganization;
	}

	public void setPsnOrganization(String psnOrganization) {
		this.psnOrganization = psnOrganization;
	}
	
	@Length(min=0, max=32, message="身份证长度必须介于 1 和 32 之间")
	public String getPsnCode() {
		return psnCode;
	}

	public void setPsnCode(String psnCode) {
		this.psnCode = psnCode;
	}
	
	public Date getPsnBirthday() {
		return psnBirthday;
	}

	public void setPsnBirthday(Date psnBirthday) {
		this.psnBirthday = psnBirthday;
	}

	@Length(min=0, max=100, message="职务长度必须介于 1 和 100 之间")
	public String getPsnPost() {
		return psnPost;
	}

	public void setPsnPost(String psnPost) {
		this.psnPost = psnPost;
	}
	
	@Length(min=0, max=100, message="住址长度必须介于 1 和 100 之间")
	public String getPsnAddress() {
		return psnAddress;
	}

	public void setPsnAddress(String psnAddress) {
		this.psnAddress = psnAddress;
	}
	
	@Length(min=0, max=32, message="联系电话长度必须介于 1 和 32 之间")
	public String getPsnPhone() {
		return psnPhone;
	}

	public void setPsnPhone(String psnPhone) {
		this.psnPhone = psnPhone;
	}
	
	@Length(min=0, max=8, message="性别长度必须介于 1 和 8 之间")
	public String getPsnSex() {
		return psnSex;
	}

	public void setPsnSex(String psnSex) {
		this.psnSex = psnSex;
	}
	
	public String getCaseHappenDate() {
		return caseHappenDate;
	}

	public void setCaseHappenDate(String caseHappenDate) {
		this.caseHappenDate = caseHappenDate;
	}
	
	@Length(min=1, max=100, message="案发地点长度必须介于 1 和 100 之间")
	public String getCaseHappenAddress() {
		return caseHappenAddress;
	}

	public void setCaseHappenAddress(String caseHappenAddress) {
		this.caseHappenAddress = caseHappenAddress;
	}
	
	@Length(min=1, max=32, message="案件所涉项目代码长度必须介于 1 和 32 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=1, max=100, message="案件所涉项目名称长度必须介于 1 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=1, max=200, message="案由长度必须介于 1 和 200 之间")
	public String getCaseCause() {
		return caseCause;
	}

	public void setCaseCause(String caseCause) {
		this.caseCause = caseCause;
	}

	public String getCaseTransfer() {
		return caseTransfer;
	}

	public void setCaseTransfer(String caseTransfer) {
		this.caseTransfer = caseTransfer;
	}

	public String getTransferCaseId() {
		return transferCaseId;
	}

	public void setTransferCaseId(String transferCaseId) {
		this.transferCaseId = transferCaseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CaseProcess getCaseProcess() {
		return caseProcess;
	}

	public void setCaseProcess(CaseProcess caseProcess) {
		this.caseProcess = caseProcess;
	}

	public List<CaseProcess> getCurrentCaseProcesses() {
		return currentCaseProcesses;
	}

	public void setCurrentCaseProcesses(List<CaseProcess> currentCaseProcesses) {
		this.currentCaseProcesses = currentCaseProcesses;
	}
	
	public List<CaseProcess> getCaseProcesses() {
		return caseProcesses;
	}

	public void setCaseProcesses(List<CaseProcess> caseProcesses) {
		this.caseProcesses = caseProcesses;
	}

	public CaseAttach getCaseAttach() {
		return caseAttach;
	}

	public void setCaseAttach(CaseAttach caseAttach) {
		this.caseAttach = caseAttach;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Map<String, Object> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public HistoricProcessInstance getHistoricProcessInstance() {
		return historicProcessInstance;
	}

	public void setHistoricProcessInstance(
			HistoricProcessInstance historicProcessInstance) {
		this.historicProcessInstance = historicProcessInstance;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
	
	public String getCaseCauseShort(){
		return this.getFieldShort(this.caseCause);
	}
	
	public String getProjectNameShort(){
		return this.getFieldShort(this.projectName);
	}
	
	public Date getAcceptDateFrom() {
		return acceptDateFrom;
	}

	public void setAcceptDateFrom(Date acceptDateFrom) {
		this.acceptDateFrom = acceptDateFrom;
	}

	public Date getAcceptDateTo() {
		return acceptDateTo;
	}

	public void setAcceptDateTo(Date acceptDateTo) {
		this.acceptDateTo = acceptDateTo;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}

	private String getFieldShort(String field){
		String rst = "";
		if(field.length()>FieldShortLen){
			rst = field.substring(0, 12) + "...";
		}else{
			rst = field;
		}
		return rst;
	}
	
	public String getParty(){
		return "单位".equals(this.partyType)? this.orgName:this.psnName;
	}

	public String getOldCaseId() {
		return oldCaseId;
	}

	public void setOldCaseId(String oldCaseId) {
		this.oldCaseId = oldCaseId;
	}
	
	public boolean getAcceptanceTabVisible(){
		boolean flag = true;
		return flag;
	}
	
	public boolean getInitialTabVisible(){		
		return this.getTabVisibleByPreviousStage(Global.CASE_STAGE_ACCEPTANCE);
	}
	
	public boolean getHandleTabVisible(){
		return this.getTabVisibleByPreviousStage(Global.CASE_STAGE_INITIAL);
	}
	
	public boolean getNotifyTabVisible(){
		return this.getTabVisibleByPreviousStage(Global.CASE_STAGE_HANDLE);
	}
	
	public boolean getDecisionTabVisible(){
		return this.getTabVisibleByPreviousStage(Global.CASE_STAGE_NOTIFY);
	}
	
	public boolean getSettleTabVisible(){
		return this.getTabVisibleByPreviousStage(Global.CASE_STAGE_DECISION);
	}
	
	public boolean getFinishTabVisible(){
		return this.getTabVisibleByPreviousStage(Global.CASE_STAGE_SETTLE);
	}
	
	public String getCaseProcessStatus(String stage){
		String status = "";
		
		for(CaseProcess entity : this.caseProcesses){
			if(stage.equals(entity.getCaseStage())){
				status = entity.getCaseStageStatus();
			}
		}
		
		return status;
	}
	
	public CaseProcess getCaseProcess(String stage){
		CaseProcess rst = null;
		
		for(CaseProcess entity : this.caseProcesses){
			if(stage.equals(entity.getCaseStage())){
				rst = entity;
			}
		}
		
		return rst;
	}
	
	private boolean getTabVisibleByPreviousStage(String previousStage){
		boolean flag = false;
		String statusPre = this.getCaseProcessStatus(previousStage);
		if(Global.CASE_STAGE_STATUS_FINISHED.equals(statusPre)){
			flag = true;
		}
		return flag;		
	}
	
}