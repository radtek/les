/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.persistence.DataEntity;

import com.google.common.collect.Lists;

/**
 * 案件流程Entity
 * @author GLQ
 * @version 2018-07-14
 */
public class CaseProcess extends DataEntity<CaseProcess> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String caseSummary;		// 案情摘要
	private String caseHandler;		// 办案人
	private String caseStage;		// 事项类型
	private String caseStageStatus;		// 事项类型状态
	private String procInstId;		// 受理流程号
	
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
	
	@Length(min=1, max=500, message="案情摘要长度必须介于 1 和 500 之间")
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
		for(String name : list){
			buffer.append(",").append(name);
		}
		this.caseHandler = buffer.substring(1);
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
	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
}