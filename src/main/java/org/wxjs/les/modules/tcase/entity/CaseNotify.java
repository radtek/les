/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.wxjs.les.common.config.Global;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.Util;

/**
 * 案件告知书Entity
 * @author GLQ
 * @version 2018-07-09
 */
public class CaseNotify extends DataEntity<CaseNotify> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private String notifyType; 
	private String year;		// 年份
	private String seq;		// 流水号
	private String partyName;		// 名称
	private String content;		// 正文
	private String launchDept;		// 发证部门
	private Date launchDate;		// 发证时间
	
	//临时属性
	private String paramUri;		// uri
	
	public CaseNotify() {
		super();
	}
	
	public static CaseNotify getInstance(Tcase tcase){
		CaseNotify entity = new CaseNotify();
		
		if(tcase!=null){
			entity.setCaseId(tcase.getId());
			entity.setPartyName(tcase.getParty());
			entity.setLaunchDept(Global.getConfig("defaultLaunchDept"));
			entity.setLaunchDate(Calendar.getInstance().getTime());
			entity.setYear(DateUtils.getYear());
		}
		
		return entity;
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
	
	@Length(min=0, max=8, message="流水号长度必须介于 0 和 8 之间")
	public String getSeq() {
		return Util.fillZeroPre(seq, 3);
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

	public String getParamUri() {
		return this.paramUri==null?"":this.paramUri.replaceAll("&amp;", "&");
	}

	public void setParamUri(String paramUri) {
		this.paramUri = paramUri;
	}
	
	
	
}