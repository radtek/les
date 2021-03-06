/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import java.util.List;

import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.base.entity.Signature;
import org.wxjs.les.modules.sys.entity.Role;
import org.wxjs.les.modules.sys.entity.User;
import org.wxjs.les.modules.sys.utils.UserUtils;

import com.google.common.collect.Lists;

/**
 * 案件流程Entity
 * @author GLQ
 * @version 2018-07-14
 */
public class CaseProcess extends DataEntity<CaseProcess> {
	
	private static final long serialVersionUID = 1L;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private String caseId;		// 案件编号
	private String caseSummary;		// 案情摘要
	private String caseHandler;		// 办案人
	private String caseStage;		// 事项类型
	private String caseStageStatus;		// 事项类型状态
	private String procInsId;		// 受理流程号

	private List<User> availableHandlers = Lists.newArrayList();
	private boolean multiple = false;
	
	private String procDefId;		// 流程定义号
	
	private String executionId;		// 执行号
	
	//签名信息
	private List<Signature> signatures = Lists.newArrayList();
	
	//临时属性
	private List<String> procInsIds;
	
	public CaseProcess() {
		super();
	}

	public CaseProcess(String id){
		super(id);
	}

	@Length(min=1, max=32, message="案件编号长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Length(min=1, max=1000, message="案情摘要长度必须介于 1 和 1000 之间")
	public String getCaseSummary() {
		return caseSummary;
	}

	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}
	
	@Length(min=1, max=100, message="办案人长度必须介于 1 和 100 之间")
	public String getCaseHandler() {
		return caseHandler;
	}

	public void setCaseHandler(String caseHandler) {
		this.caseHandler = caseHandler;
	}
	
	public String getCaseHandlerName() { 
		String rst = "";
		StringBuffer buffer = new StringBuffer();
		String handler = this.caseHandler;
		
		if(handler!=null && handler.length()>0){
			String[] strs = handler.split(",");
			for(String str : strs){
				User user = UserUtils.getByLoginName(str);
				buffer.append(",").append(user.getName());
			}
		}
		if(buffer.length()>0){
			rst = buffer.substring(1);
		}
		return rst;
	}
	
	public List<String> getCaseHandlerList() {
		List<String> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(this.caseHandler)){
			String[] strs = this.caseHandler.split(",");
			for(String str : strs){
				list.add(str);
			}
		}
		
		return list;
	}

	public void setCaseHandlerList(List<String> list) {
		StringBuffer buffer = new StringBuffer();
		for(String loginName : list){
			buffer.append(",").append(loginName);
		}
		this.caseHandler = buffer.substring(1);
		
		logger.debug("this.caseHandler:{}", this.caseHandler);
	}
	
	@Length(min=0, max=8, message="事项类型长度必须介于 0 和 8 之间")
	public String getCaseStage() {
		return caseStage;
	}

	public void setCaseStage(String caseStage) {
		this.caseStage = caseStage;
	}
	
	@Length(min=0, max=8, message="事项类型状态长度必须介于 0 和 8 之间")
	public String getCaseStageStatus() {
		return caseStageStatus;
	}

	public void setCaseStageStatus(String caseStageStatus) {
		this.caseStageStatus = caseStageStatus;
	}
	
	@Length(min=0, max=64, message="受理流程号长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	public boolean isIndependentFlow(){
		return (Global.CASE_STAGE_SERIOUS.equals(this.caseStage)
			 || Global.CASE_STAGE_CANCEL.equals(this.caseStage));
	}

	public List<User> getAvailableHandlers() {
		return availableHandlers;
	}

	public void setAvailableHandlers(List<User> availableHandlers) {
		this.availableHandlers = availableHandlers;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public List<Signature> getSignatures() {
		return signatures;
	}

	public void setSignatures(List<Signature> signatures) {
		this.signatures = signatures;
	}
	
	public boolean getEditable(){
		User user = UserUtils.getUser();
		return user.isManager() || StringUtils.isEmpty(this.caseStageStatus) || "0".equals(this.caseStageStatus) || "1".equals(this.caseStageStatus);
	}
	
	/**
	 * 重大行政处罚中文本框是否可编辑
	 * @return
	 */
	public boolean getSeriousTextsEditable(){
		boolean specialRole = false;
		User user = UserUtils.getUser();
		List<Role> roles = user.getRoleList();
		//List<Role> roles = UserUtils.getRoleList();
		if(roles==null){
			return specialRole;
		}
		for(Role role : roles){
			if("#slkky#slkfzr#fgcblr#fgcfzr#".contains("#"+role.getEnname()+"#")){
				specialRole = true;
				break;
			}
		}
		return specialRole || this.getEditable();
	}
	
	public boolean getStartable(){
		return StringUtils.isEmpty(this.caseStageStatus) || "0".equals(this.caseStageStatus);
	}

	public List<String> getProcInsIds() {
		return procInsIds;
	}

	public void setProcInsIds(List<String> procInsIds) {
		this.procInsIds = procInsIds;
	}
	
}