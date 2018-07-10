/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.wxjs.les.common.persistence.DataEntity;

/**
 * 案件告知书Entity
 * @author GLQ
 * @version 2018-07-09
 */
public class CaseNotify extends DataEntity<CaseNotify> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String notifyType;		// 告知书类型
	private String year;		// 年份
	private String seq;		// 流水号
	private String partyName;		// 名称
	private String content;		// 正文
	private String launchDept;		// 发证部门
	private Date launchDate;		// 发证时间
	
	public CaseNotify() {
		super();
	}

	public CaseNotify(String id){
		super(id);
	}

	@Length(min=1, max=32, message="案件编号长度必须介于 1 和 32 之间")
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	@Length(min=1, max=8, message="告知书类型长度必须介于 1 和 8 之间")
	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	
	@Length(min=1, max=8, message="年份长度必须介于 1 和 8 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=1, max=8, message="流水号长度必须介于 1 和 8 之间")
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	
	@Length(min=1, max=1000, message="正文长度必须介于 1 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=1, max=100, message="发证部门长度必须介于 1 和 100 之间")
	public String getLaunchDept() {
		return launchDept;
	}

	public void setLaunchDept(String launchDept) {
		this.launchDept = launchDept;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}
	
}