/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import java.util.Calendar;

import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.DataEntity;

/**
 * 案件审理Entity
 * @author GLQ
 * @version 2018-07-24
 */
public class CaseHandle extends DataEntity<CaseHandle> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String punishCode;		// 行政处罚编码
	private String legalBasis;		// 法律依据
	private String legalBasisContent;		// 依据内容
	private String discretion;		// 自由裁量权
	private String punishMoney;		// 实际罚款金额（元）
	private String investReport;		// 案件调查报告内容
	private String fact;		// 案件事实经过及证据
	private String investigator;		// 调查人
	
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
	
	@Length(min=0, max=200, message="行政处罚编码长度必须介于 0 和 200 之间")
	public String getPunishCode() {
		return punishCode;
	}

	public void setPunishCode(String punishCode) {
		this.punishCode = punishCode;
	}
	
	@Length(min=0, max=200, message="法律依据长度必须介于 0 和 200 之间")
	public String getLegalBasis() {
		return legalBasis;
	}

	public void setLegalBasis(String legalBasis) {
		this.legalBasis = legalBasis;
	}
	
	@Length(min=0, max=200, message="依据内容长度必须介于 0 和 200 之间")
	public String getLegalBasisContent() {
		return legalBasisContent;
	}

	public void setLegalBasisContent(String legalBasisContent) {
		this.legalBasisContent = legalBasisContent;
	}
	
	@Length(min=0, max=200, message="自由裁量权长度必须介于 0 和 200 之间")
	public String getDiscretion() {
		return discretion;
	}

	public void setDiscretion(String discretion) {
		this.discretion = discretion;
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
	
	@Length(min=0, max=64, message="调查人长度必须介于 0 和 64 之间")
	public String getInvestigator() {
		return investigator;
	}

	public void setInvestigator(String investigator) {
		this.investigator = investigator;
	}
	
}