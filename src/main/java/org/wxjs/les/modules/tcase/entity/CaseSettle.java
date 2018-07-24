/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import java.util.Calendar;

import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.DataEntity;

/**
 * 案件结案Entity
 * @author GLQ
 * @version 2018-07-24
 */
public class CaseSettle extends DataEntity<CaseSettle> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String handleSummary;		// 案件处理情况
	private String executeSummary;		// 案件执行情况
	private String reviewSummary;		// 案件复议情况
	
	public CaseSettle() {
		super();
	}

	public CaseSettle(String id){
		super(id);
	}
	
	public static CaseSettle getInstance(Tcase tcase){
		CaseSettle entity = new CaseSettle();
		
		if(tcase!=null){
			entity.setCaseId(tcase.getId());
		}
		
		return entity;
	}

	@Length(min=1, max=32, message="案件编号长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	public String getHandleSummary() {
		return handleSummary;
	}

	public void setHandleSummary(String handleSummary) {
		this.handleSummary = handleSummary;
	}
	
	public String getExecuteSummary() {
		return executeSummary;
	}

	public void setExecuteSummary(String executeSummary) {
		this.executeSummary = executeSummary;
	}
	
	public String getReviewSummary() {
		return reviewSummary;
	}

	public void setReviewSummary(String reviewSummary) {
		this.reviewSummary = reviewSummary;
	}
	
}