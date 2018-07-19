package org.wxjs.les.modules.base.entity;

import java.util.HashMap;
import java.util.Map;

public class ActTask {
	
	private String businesskey;
	private String taskid;
	private String taskname;
	private String processinstanceid;
	private String processdefid;
	private String executionId;
	
	private String asignee; //操作者
	private String role; //操作者角色
	private String approve; //操作动作， true:false; pass, return, cancel
	private String approveOpinion; //意见
	
	private String nextHandlers; //后续处理人
	
	private Map<String,Object> variables=new HashMap<String,Object>();
	
	public String getBusinesskey() {
		return businesskey;
	}
	public void setBusinesskey(String businesskey) {
		this.businesskey = businesskey;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public String getProcessinstanceid() {
		return processinstanceid;
	}
	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}
	public String getProcessdefid() {
		return processdefid;
	}
	public void setProcessdefid(String processdefid) {
		this.processdefid = processdefid;
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

}
