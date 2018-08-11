/**
 * Copyright &copy; 2012-2016 千里目 All rights reserved.
 */
package org.wxjs.les.modules.tcase.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.modules.sys.entity.User;

/**
 * 重大行政处罚Entity
 * @author GLQ
 * @version 2018-07-27
 */
public class CaseSerious extends DataEntity<CaseSerious> {
	
	private static final long serialVersionUID = 1L;
	private String caseId;		// 案件编号
	private Date meetingDateFrom;		// 时间
	private Date meetingDateTo;		// 时间
	private String meetingAddress;		// 地点
	private User master;		// 主持人
	private User voter;		// 参会人员
	private String voterAdd;		// 参会人员补充
	private User attendee;		// 列席人员
	private String attendeeAdd;		// 列席人员补充
	private User recorder;		// 记录人员
	private String caseSummary;		// 执法机构汇报案情
	private String punishProposal;		// 执法机构处罚建议
	private String checkOpinion;		// 审查小组审查意见
	
	//临时
	private String paramUri;
	
	public CaseSerious() {
		super();
	}

	public CaseSerious(String id){
		super(id);
	}
	
	public static CaseSerious getInstance(Tcase tcase){
		CaseSerious entity = new CaseSerious();
		
		if(tcase!=null){
			entity.setCaseId(tcase.getId());
			entity.setMeetingDateFrom(Calendar.getInstance().getTime());
			entity.setMeetingDateTo(Calendar.getInstance().getTime());
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMeetingDateFrom() {
		return meetingDateFrom;
	}

	public void setMeetingDateFrom(Date meetingDateFrom) {
		this.meetingDateFrom = meetingDateFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMeetingDateTo() {
		return meetingDateTo;
	}

	public void setMeetingDateTo(Date meetingDateTo) {
		this.meetingDateTo = meetingDateTo;
	}
	
	@Length(min=0, max=64, message="地点长度必须介于 0 和 64 之间")
	public String getMeetingAddress() {
		return meetingAddress;
	}

	public void setMeetingAddress(String meetingAddress) {
		this.meetingAddress = meetingAddress;
	}

	public User getMaster() {
		return master;
	}

	public void setMaster(User master) {
		this.master = master;
	}

	public User getVoter() {
		return voter;
	}

	public void setVoter(User voter) {
		this.voter = voter;
	}

	public void setAttendee(User attendee) {
		this.attendee = attendee;
	}

	@Length(min=0, max=128, message="参会人员补充长度必须介于 0 和 128 之间")
	public String getVoterAdd() {
		return voterAdd;
	}

	public void setVoterAdd(String voterAdd) {
		this.voterAdd = voterAdd;
	}
	
	public User getAttendee() {
		return attendee;
	}

	@Length(min=0, max=128, message="列席人员补充长度必须介于 0 和 128 之间")
	public String getAttendeeAdd() {
		return attendeeAdd;
	}

	public void setAttendeeAdd(String attendeeAdd) {
		this.attendeeAdd = attendeeAdd;
	}
	
	public User getRecorder() {
		return recorder;
	}

	public void setRecorder(User recorder) {
		this.recorder = recorder;
	}

	public String getCaseSummary() {
		return caseSummary;
	}

	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}
	
	public String getPunishProposal() {
		return punishProposal;
	}

	public void setPunishProposal(String punishProposal) {
		this.punishProposal = punishProposal;
	}
	
	public String getCheckOpinion() {
		return checkOpinion;
	}

	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}

	public String getParamUri() {
		return this.paramUri==null?"":this.paramUri.replaceAll("&amp;", "&");
	}

	public void setParamUri(String paramUri) {
		this.paramUri = paramUri;
	}
	
}