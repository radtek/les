/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.sys.entity.User;

/**
 * 案件审理Entity
 * @author GLQ
 * @version 2018-07-24
 */
public class CaseHandle extends DataEntity<CaseHandle> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String punishMoney;		// 实际罚款金额（元）
	private String investReport;		// 案件调查报告内容
	private String fact;		// 案件事实经过及证据
	private User investigator;		// 调查人
	
	//临时属性
	private String paramUri;		// uri
	
	private String rangeIds;
	
	public CaseHandle() {
		super();
	}

	public CaseHandle(String id){
		super(id);
	}
	
	public static CaseHandle getInstance(Tcase tcase){
		CaseHandle entity = new CaseHandle();
		
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
	
	public String getPunishMoney() {
		return punishMoney;
	}

	public void setPunishMoney(String punishMoney) {
		this.punishMoney = punishMoney;
	}
	
	public String getInvestReport() {
		return investReport;
	}

	public void setInvestReport(String investReport) {
		this.investReport = investReport;
	}
	
	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}

	public User getInvestigator() {
		return investigator;
	}

	public void setInvestigator(User investigator) {
		this.investigator = investigator;
	}

	public String getParamUri() {
		return this.paramUri==null?"":this.paramUri.replaceAll("&amp;", "&");
	}

	public void setParamUri(String paramUri) {
		this.paramUri = paramUri;
	}

	public String getRangeIds() {
		return rangeIds;
	}

	public void setRangeIds(String rangeIds) {
		this.rangeIds = rangeIds;
	}
	
}