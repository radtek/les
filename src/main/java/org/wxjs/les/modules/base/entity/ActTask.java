package org.wxjs.les.modules.base.entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.wxjs.les.common.utils.IdGen;

public class ActTask {
	
	private String businesskey;
	private String taskId;
	private String taskName;
	private String procInsId;
	private String procDefid;
	private String executionId;
	
	private String asignee; //操作者
	private String role; //操作者角色
	private String approve; //操作动作， true:false; pass, return, cancel
	private String approveOpinion; //意见
	
	private String nextHandlers; //后续处理人
	
	private Map<String,Object> variables=new HashMap<String,Object>();
	
	
	private String nextConditionTexts;
	
	private Signature signature;
	
	public void initialSignature(){
		this.signature = new Signature();
		this.signature.setId(IdGen.uuid());
	}
	
	public String getBusinesskey() {
		return businesskey;
	}
	public void setBusinesskey(String businesskey) {
		this.businesskey = businesskey;
	}
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getProcDefid() {
		return procDefid;
	}
	public void setProcDefid(String procDefid) {
		this.procDefid = procDefid;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getAsignee() {
		return asignee;
	}
	public void setAsignee(String asignee) {
		this.asignee = asignee;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
	}
	public String getApproveOpinion() {
		return approveOpinion;
	}
	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}
	public Map<String, Object> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
	public String getNextHandlers() {
		return nextHandlers;
	}
	public void setNextHandlers(String nextHandlers) {
		this.nextHandlers = nextHandlers;
	}

	public String getNextConditionTexts() {
		return nextConditionTexts;
	}

	public Signature getSignature() {
		return signature;
	}
	public void setSignature(Signature signature) {
		this.signature = signature;
	}
	public void setNextConditionTexts(String nextConditionTexts) {
		this.nextConditionTexts = nextConditionTexts;
	}
	
	public boolean getNeedPassButton(){
		return StringUtils.isEmpty(this.nextConditionTexts) || this.nextConditionTexts.contains("'pass'");
	}
	
	public boolean getNeedCancelButton(){
		return this.nextConditionTexts.contains("'cancel'");
	}
	
	public boolean getNeedReturnButton(){
		return this.nextConditionTexts.contains("'return'");
	}

}
