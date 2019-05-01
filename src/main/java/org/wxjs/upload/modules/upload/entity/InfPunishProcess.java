/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package org.wxjs.upload.modules.upload.entity;

import org.hibernate.validator.constraints.Length;
import org.wxjs.les.common.persistence.DataEntity;
import org.wxjs.les.common.utils.DateUtils;
import org.wxjs.les.common.utils.Util;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;


/**
 * inf_punish_processEntity
 * @author GLQ
 * @version 2018-08-21
 */
public class InfPunishProcess extends DataEntity<InfPunishProcess> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// no
	private String noOrd;		// no_ord
	private String orgId;		// org_id
	private String internalNo;		// internal_no
	private String itemId;		// item_id
	private String tacheCode;		// tache_code
	private String tacheName;		// tache_name
	private String department;		// department
	private String userStaffCode;		// user_staff_code
	private String userName;		// user_name
	private String status;		// status
	private String promise;		// promise
	private String promiseType;		// promise_type
	private String promiseStartSign;		// promise_start_sign
	private String isrisk;		// isrisk
	private String risktype;		// risktype
	private String riskdescription;		// riskdescription
	private String riskresult;		// riskresult
	private String note;		// note
	private String attachment;		// attachment
	private String evidence;		// evidence
	private Date readDate;		// read_date
	private String syncSign;		// sync_sign
	private String syncErrorDesc;		// sync_error_desc
	private Date processStartTime;		// process_start_time
	private Date processEndTime;		// process_end_time
	
	public InfPunishProcess() {
		super();
	}

	public InfPunishProcess(String id){
		super(id);
	}

	@Length(min=1, max=50, message="no长度必须介于 1 和 50 之间")
	public String getNo() {
		return Util.getString(no);
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	public String getNoOrd() {
		return Util.getString(noOrd);
	}

	public void setNoOrd(String noOrd) {
		this.noOrd = noOrd;
	}
	
	@Length(min=1, max=10, message="org_id长度必须介于 1 和 10 之间")
	public String getOrgId() {
		return Util.getString(orgId);
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=1, max=100, message="internal_no长度必须介于 1 和 100 之间")
	public String getInternalNo() {
		return Util.getString(internalNo);
	}

	public void setInternalNo(String internalNo) {
		this.internalNo = internalNo;
	}
	
	@Length(min=1, max=100, message="item_id长度必须介于 1 和 100 之间")
	public String getItemId() {
		return Util.getString(itemId);
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=30, message="tache_code长度必须介于 0 和 30 之间")
	public String getTacheCode() {
		return Util.getString(tacheCode);
	}

	public void setTacheCode(String tacheCode) {
		this.tacheCode = tacheCode;
	}
	
	@Length(min=0, max=100, message="tache_name长度必须介于 0 和 100 之间")
	public String getTacheName() {
		return Util.getString(tacheName);
	}

	public void setTacheName(String tacheName) {
		this.tacheName = tacheName;
	}
	
	@Length(min=1, max=100, message="department长度必须介于 1 和 100 之间")
	public String getDepartment() {
		return Util.getString(department);
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=1, max=30, message="user_staff_code长度必须介于 1 和 30 之间")
	public String getUserStaffCode() {
		return Util.getString(userStaffCode);
	}

	public void setUserStaffCode(String userStaffCode) {
		this.userStaffCode = userStaffCode;
	}
	
	@Length(min=1, max=100, message="user_name长度必须介于 1 和 100 之间")
	public String getUserName() {
		return Util.getString(userName);
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=1, max=2, message="status长度必须介于 1 和 2 之间")
	public String getStatus() {
		return Util.getString(status);
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPromise() {
		return promise;
	}

	public void setPromise(String promise) {
		this.promise = promise;
	}
	
	@Length(min=0, max=2, message="promise_type长度必须介于 0 和 2 之间")
	public String getPromiseType() {
		return Util.getString(promiseType);
	}

	public void setPromiseType(String promiseType) {
		this.promiseType = promiseType;
	}
	
	@Length(min=0, max=1, message="promise_start_sign长度必须介于 0 和 1 之间")
	public String getPromiseStartSign() {
		return Util.getString(promiseStartSign);
	}

	public void setPromiseStartSign(String promiseStartSign) {
		this.promiseStartSign = promiseStartSign;
	}
	
	public String getIsrisk() {
		return isrisk;
	}

	public void setIsrisk(String isrisk) {
		this.isrisk = isrisk;
	}
	
	@Length(min=0, max=50, message="risktype长度必须介于 0 和 50 之间")
	public String getRisktype() {
		return Util.getString(risktype);
	}

	public void setRisktype(String risktype) {
		this.risktype = risktype;
	}
	
	@Length(min=0, max=200, message="riskdescription长度必须介于 0 和 200 之间")
	public String getRiskdescription() {
		return Util.getString(riskdescription);
	}

	public void setRiskdescription(String riskdescription) {
		this.riskdescription = riskdescription;
	}
	
	@Length(min=0, max=200, message="riskresult长度必须介于 0 和 200 之间")
	public String getRiskresult() {
		return Util.getString(riskresult);
	}

	public void setRiskresult(String riskresult) {
		this.riskresult = riskresult;
	}
	
	@Length(min=1, max=4000, message="note长度必须介于 1 和 4000 之间")
	public String getNote() {
		return Util.getString(note);
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getAttachment() {
		return Util.getString(attachment);
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public String getEvidence() {
		return Util.getString(evidence);
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReadDate() {
		return readDate;
	}
	
	public String getReadDateStr() {
		return DateUtils.formatDate(readDate, "yyyy-MM-dd");
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
	@Length(min=0, max=1, message="sync_sign长度必须介于 0 和 1 之间")
	public String getSyncSign() {
		return Util.getString(syncSign);
	}

	public void setSyncSign(String syncSign) {
		this.syncSign = syncSign;
	}
	
	@Length(min=0, max=1000, message="sync_error_desc长度必须介于 0 和 1000 之间")
	public String getSyncErrorDesc() {
		return Util.getString(syncErrorDesc);
	}

	public void setSyncErrorDesc(String syncErrorDesc) {
		this.syncErrorDesc = syncErrorDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="process_start_time不能为空")
	public Date getProcessStartTime() {
		return processStartTime;
	}
	
	public String getProcessStartTimeStr() {
		return DateUtils.formatDate(processStartTime, "yyyy-MM-dd");
	}

	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getProcessEndTime() {
		return processEndTime;
	}
	
	public String getProcessEndTimeStr() {
		return DateUtils.formatDate(processEndTime, "yyyy-MM-dd");
	}

	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}
	
	public String getUpdateDateStr() {
		return DateUtils.formatDate(this.updateDate, "yyyy-MM-dd");
	}
	
	public String getCreateDateStr() {
		return DateUtils.formatDate(this.createDate, "yyyy-MM-dd");
	}
	
}